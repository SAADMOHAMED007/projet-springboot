# Atelier 6 — DTO, validation et gestion centralisée des erreurs

Ce document décrit l'implémentation des deux parties du lab :

1. **Utilisation des DTO** : tous les endpoints reçoivent et renvoient des DTO, plus jamais les entités JPA.
2. **Validation et erreurs centralisées** : contraintes Bean Validation sur les DTO et un `@RestControllerAdvice` unique pour toute l'API.

## A. DTO et mappers

### DTO de requête et de réponse

Chaque entité possède un `XxxRequest` (entrée, validée) et un `XxxResponse` (sortie, plate).
Les paquets sont `tn.esprit.tpfoyer.web.dto` et `tn.esprit.tpfoyer.web.mapper`.

| Entité | Request | Response | Relations exposées en réponse |
|---|---|---|---|
| Agence | `AgenceRequest` | `AgenceResponse` | aucune collection |
| Client | `ClientRequest` | `ClientResponse` | aucune collection |
| Employé | `EmployeRequest` | `EmployeResponse` | `idAgence`, `agenceNom` |
| Équipement | `EquipementRequest` | `EquipementResponse` | aucune collection |
| Véhicule | `VehiculeRequest` | `VehiculeResponse` | `idAgence`, `agenceNom`, liste d'`EquipementResponse` |
| Réservation | `ReservationRequest` | `ReservationResponse` | `idVehicule`, `vehiculeImmatriculation`, `idClient`, `clientNom`, `clientPrenom`, `idContrat` |
| Contrat | `ContratRequest` | `ContratResponse` | `idReservation`, liste de `PaiementResponse` |
| Paiement | `PaiementRequest` (imbriqué dans le contrat) | `PaiementResponse` | `idContrat` |
| Maintenance | `MaintenanceRequest` | `MaintenanceResponse` | `idVehicule`, `vehiculeImmatriculation` |

Règles appliquées :

- les relations sont **envoyées sous forme d'identifiants** (`idAgence`, `idVehicule`, `idClient`, `idReservation`, `equipementIds`) et non sous forme d'objets imbriqués ;
- les réponses sont **plates** : quelques identifiants et libellés utiles, jamais de graphe récursif ;
- `ContratResponse` conserve ses paiements, car ils font partie du cas d'usage, mais chaque `PaiementResponse` ne ré-embarque pas le contrat ;
- aucun champ interne ou collection inutile n'est exposé (`employes`, `vehicules`, `reservations`, `maintenances`).

### Mappers

| Mapper | Rôle | Dépendances |
|---|---|---|
| `AgenceMapper` | `AgenceRequest` ↔ `Agence` ↔ `AgenceResponse` | — |
| `ClientMapper` | idem pour `Client` | — |
| `EmployeMapper` | résout `idAgence` | `IAgenceRepository` |
| `EquipementMapper` | idem pour `Equipement` | — |
| `VehiculeMapper` | résout `idAgence` et `equipementIds` | `IAgenceRepository`, `IEquipementRepository`, `EquipementMapper` |
| `ReservationMapper` | résout `idVehicule` et `idClient` | `IVehiculeRepository`, `IClientRepository` |
| `PaiementMapper` | `PaiementRequest` ↔ `Paiement` ↔ `PaiementResponse` | — |
| `ContratMapper` | résout `idReservation` et rattache les paiements au contrat | `IReservationRepository`, `PaiementMapper` |
| `MaintenanceMapper` | résout `idVehicule` | `IVehiculeRepository` |

Une référence absente (`idVehicule: 9999`) lève `ResourceNotFoundException` et produit un **404** avant toute écriture.

## B. Services

Le contrat générique est désormais exprimé en DTO :

```java
public interface CrudService<REQ, RES> {
    RES create(REQ request);
    List<RES> findAll();
    RES findById(Long id);
    RES update(Long id, REQ request);
    void deleteById(Long id);
}
```

`AbstractCrudService<T, REQ, RES>` :

1. convertit la requête avec `mapper().toEntity(request)` ;
2. applique les règles métier (`validate`) ;
3. persiste via le repository ;
4. convertit le résultat avec `mapper().toResponse(entity)`.

Les implémentations (`AgenceServiceImpl`, `ClientServiceImpl`, etc.) déclarent
`extends AbstractCrudService<Entité, XxxRequest, XxxResponse>` et injectent leur mapper.
`IPaiementService` reste en lecture seule mais renvoie `PaiementResponse`.

Points de conception :

- `update` convertit la requête en entité « updates », valide, puis copie uniquement les champs autorisés sur l'entité existante ;
- les paiements d'un contrat ne sont pas copiés : un `PUT` sans `paiements` les conserve ;
- si `idReservation` est omis lors de la modification d'un contrat, la réservation existante est conservée ;
- la validation de cohérence métier (dates inversées, montants négatifs) reste dans les services, en complément de la validation de forme des DTO.

## C. Validation des données

La dépendance `spring-boot-starter-validation` était déjà présente dans `pom.xml`. Les DTO portent maintenant les contraintes :

| DTO | Contrôles principaux |
|---|---|
| `AgenceRequest` | `nom` et `ville` non vides, tailles maximales, téléphone au format autorisé |
| `ClientRequest` | `nom`, `prenom`, `email` obligatoires, email valide, téléphone, taille du permis |
| `EmployeRequest` | `nom`, `prenom`, `role` obligatoires, `idAgence` positif |
| `EquipementRequest` | `libelle` obligatoire, `quantite` obligatoire et ≥ 0 |
| `VehiculeRequest` | immatriculation, marque, modèle, catégorie, statut obligatoires, tarif ≥ 0, `equipementIds` positifs |
| `ReservationRequest` | dates et statut obligatoires, `idVehicule` et `idClient` obligatoires et positifs |
| `ContratRequest` | date de signature et montant obligatoires, montant ≥ 0, `@Valid` sur les paiements imbriqués |
| `PaiementRequest` | montant > 0, date et mode de paiement obligatoires |
| `MaintenanceRequest` | dates, coût et véhicule obligatoires, coût ≥ 0 |

Chaque contrôleur utilise `@Valid @RequestBody XxxRequest`, ce qui déclenche la validation avant l'appel du service.

## D. Gestion centralisée des erreurs

`tn.esprit.tpfoyer.web.controller.ApiExceptionHandler` (`@RestControllerAdvice`) traduit les exceptions en `ProblemDetail` (RFC 9457) :

| Exception | Statut | Détail |
|---|---|---|
| `ResourceNotFoundException` | 404 | ressource ou référence absente |
| `MethodArgumentNotValidException` | 400 | `errors` : champ → message |
| `HandlerMethodValidationException`, `ConstraintViolationException` | 400 | paramètres/contraintes de méthode |
| `HttpMessageNotReadableException` | 400 | JSON mal formé, date ou énumération invalide |
| `MethodArgumentTypeMismatchException` | 400 | identifiant de chemin non convertible |
| `IllegalArgumentException` | 400 | règle métier non respectée |
| `DataIntegrityViolationException` | 409 | unicité (immatriculation) ou intégrité |
| `HttpRequestMethodNotSupportedException` | 405 | verbe non déclaré |
| `HttpMediaTypeNotSupportedException` | 415 | type de contenu non supporté |
| `NoResourceFoundException` | 404 | route inconnue |
| `Exception` | 500 | erreur inattendue journalisée |

Exemple de réponse 400 :

```json
{
  "type": "about:blank",
  "title": "Données invalides",
  "status": 400,
  "detail": "Un ou plusieurs champs de la requête sont invalides.",
  "instance": "/api/agences",
  "timestamp": "2026-11-08T18:51:23.761376900Z",
  "errors": {
    "nom": "Le nom de l'agence est obligatoire",
    "ville": "La ville de l'agence est obligatoire"
  }
}
```

## E. Exécution et preuves

### Base de démonstration H2

Aucun serveur MySQL n'étant disponible dans l'environnement d'exécution, un profil `demo` a été ajouté
(`src/main/resources/application-demo.properties` + dépendance H2 `runtime`). La configuration MySQL par défaut
(`application.properties`) reste inchangée.

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=demo"
```

### Scénario d'exécution

Le scénario (créations, lectures, mise à jour, erreurs) a été exécuté sur l'API démarrée avec le profil `demo`. Les captures d'écran de `docs/screenshots/` constituent la preuve demandée par le lab. Il couvre :

- créations d'agence, client, équipement, employé, véhicule, réservation, contrat avec deux paiements, maintenance ;
- lectures avec DTO plats (relations résumées par identifiants/libellés) ;
- modification d'un contrat sans perte des paiements ni de la réservation ;
- validation : champs manquants, email invalide, tarif négatif, énumération inconnue ;
- erreurs : 404 (ressource et référence), 409 (immatriculation dupliquée), 405 (POST paiement).

Exemples de réponses réelles :

```text
17. Validation : nom et ville d agence manquants
POST http://localhost:8081/api/agences
HTTP 400
{
  "detail": "Un ou plusieurs champs de la requête sont invalides.",
  "instance": "/api/agences",
  "status": 400,
  "title": "Données invalides",
  "errors": {
    "nom": "Le nom de l'agence est obligatoire",
    "ville": "La ville de l'agence est obligatoire"
  }
}

22. Reference inexistante : vehicule 9999 dans une reservation
HTTP 404
{
  "detail": "Véhicule not found with id: 9999",
  "title": "Ressource introuvable"
}

24. Conflit d unicite : immatriculation deja utilisee
HTTP 409
{
  "detail": "Une contrainte d'unicité ou d'intégrité est violée par la requête.",
  "title": "Conflit de données"
}
```

### Postman et Swagger

- Collection mise à jour : `postman/AutoLoc.postman_collection.json` (scénario « Atelier 6 »). Elle crée les ressources dans l'ordre, mémorise les identifiants dans des variables et vérifie les statuts 201/200/400/404/405/409.
- Swagger UI : `http://localhost:8081/api/swagger-ui.html` — les schémas exposés sont désormais ceux des DTO.
- Document OpenAPI : `http://localhost:8081/api/v3/api-docs`.

Captures d'écran (dossier `docs/screenshots/`) :

| Fichier | Contenu |
|---|---|
| `swagger-dto-overview.png` | Vue d'ensemble Swagger : tous les endpoints et la section `Schemas` avec les DTO |
| `swagger-post-agence.png` | Opération `POST /agences` : corps `AgenceRequest` et réponses 201/400 |
| `swagger-vehicule-request-schema.png` | Schéma `VehiculeRequest` : champs obligatoires, tailles, `tarifJournalier >= 0`, énumérations |
| `swagger-execution-201.png` | Exécution réelle de `POST /agences` : statut 201 et corps de réponse DTO |
| `swagger-execution-400-validation.png` | Exécution réelle d'un `POST /agences` invalide : statut 400 et erreurs par champ |
| `swagger-execution-200-list.png` | Exécution réelle de `GET /agences` : statut 200 et liste de `AgenceResponse` |
| `swagger-execution-404.png` | Exécution réelle de `GET /agences/9999` : statut 404 et `ProblemDetail` |
| `swagger-execution-409.png` | Exécution réelle d'un `POST /vehicules` avec immatriculation dupliquée : statut 409 et `ProblemDetail` |

Les captures d'écran du dossier `docs/screenshots/` constituent la preuve d'exécution demandée par le lab.

### Vérification

```powershell
.\mvnw.cmd -DskipTests package
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=demo"
```

La compilation et le packaging passent, puis le scénario HTTP confirme les statuts attendus (voir captures). Aucun test automatisé n'est conservé dans le dépôt : les tests ne font pas partie des livrables demandés par le lab.

## F. Fichiers ajoutés ou modifiés

- `web/dto/` : 18 DTO (`*Request`, `*Response`) ;
- `web/mapper/` : `EntityMapper` + 9 mappers ;
- `service/` : `CrudService<REQ, RES>`, interfaces et implémentations DTO ;
- `web/controller/` : 9 contrôleurs avec `@Valid` et retours DTO ;
- `web/controller/ApiExceptionHandler.java` : gestion centralisée étendue ;
- `pom.xml` : dépendance H2 `runtime` ;
- `application-demo.properties` : profil de démonstration ;
- `docs/atelier-6-demo.ps1` : script de démonstration optionnel (non requis par le lab) ;
- `postman/AutoLoc.postman_collection.json` : collection alignée sur les DTO.
