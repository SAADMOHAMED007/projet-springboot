# Atelier 5 — API REST AutoLoc

Les routes ci-dessous sont précédées de `/api`, configuré comme context-path Spring. L'application écoute actuellement sur le port `8081` : la base URL locale est `http://localhost:8081/api`.

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| AgenceController | POST `/api/agences` | `IAgenceService.create` | 201 | 400 |
| AgenceController | GET `/api/agences` | `IAgenceService.findAll` | 200 | — |
| AgenceController | GET `/api/agences/{id}` | `IAgenceService.findById` | 200 | 404 |
| AgenceController | PUT `/api/agences/{id}` | `IAgenceService.update` | 200 | 400, 404 |
| AgenceController | DELETE `/api/agences/{id}` | `IAgenceService.deleteById` | 204 | 404 |
| ClientController | POST `/api/clients` | `IClientService.create` | 201 | 400 |
| ClientController | GET `/api/clients` | `IClientService.findAll` | 200 | — |
| ClientController | GET `/api/clients/{id}` | `IClientService.findById` | 200 | 404 |
| ClientController | PUT `/api/clients/{id}` | `IClientService.update` | 200 | 400, 404 |
| ClientController | DELETE `/api/clients/{id}` | `IClientService.deleteById` | 204 | 404 |
| EmployeController | POST `/api/employes` | `IEmployeService.create` | 201 | 400 |
| EmployeController | GET `/api/employes` | `IEmployeService.findAll` | 200 | — |
| EmployeController | GET `/api/employes/{id}` | `IEmployeService.findById` | 200 | 404 |
| EmployeController | PUT `/api/employes/{id}` | `IEmployeService.update` | 200 | 400, 404 |
| EmployeController | DELETE `/api/employes/{id}` | `IEmployeService.deleteById` | 204 | 404 |
| VehiculeController | POST `/api/vehicules` | `IVehiculeService.create` | 201 | 400 |
| VehiculeController | GET `/api/vehicules` | `IVehiculeService.findAll` | 200 | — |
| VehiculeController | GET `/api/vehicules/{id}` | `IVehiculeService.findById` | 200 | 404 |
| VehiculeController | PUT `/api/vehicules/{id}` | `IVehiculeService.update` | 200 | 400, 404 |
| VehiculeController | DELETE `/api/vehicules/{id}` | `IVehiculeService.deleteById` | 204 | 404 |
| EquipementController | POST `/api/equipements` | `IEquipementService.create` | 201 | 400 |
| EquipementController | GET `/api/equipements` | `IEquipementService.findAll` | 200 | — |
| EquipementController | GET `/api/equipements/{id}` | `IEquipementService.findById` | 200 | 404 |
| EquipementController | PUT `/api/equipements/{id}` | `IEquipementService.update` | 200 | 400, 404 |
| EquipementController | DELETE `/api/equipements/{id}` | `IEquipementService.deleteById` | 204 | 404 |
| ReservationController | POST `/api/reservations` | `IReservationService.create` | 201 | 400 |
| ReservationController | GET `/api/reservations` | `IReservationService.findAll` | 200 | — |
| ReservationController | GET `/api/reservations/{id}` | `IReservationService.findById` | 200 | 404 |
| ReservationController | PUT `/api/reservations/{id}` | `IReservationService.update` | 200 | 400, 404 |
| ReservationController | DELETE `/api/reservations/{id}` | `IReservationService.deleteById` | 204 | 404 |
| ContratController | POST `/api/contrats` | `IContratService.create` | 201 | 400 |
| ContratController | GET `/api/contrats` | `IContratService.findAll` | 200 | — |
| ContratController | GET `/api/contrats/{id}` | `IContratService.findById` | 200 | 404 |
| ContratController | PUT `/api/contrats/{id}` | `IContratService.update` | 200 | 400, 404 |
| ContratController | DELETE `/api/contrats/{id}` | `IContratService.deleteById` | 204 | 404 |
| PaiementController | GET `/api/paiements` | `IPaiementService.findAll` | 200 | — |
| PaiementController | GET `/api/paiements/{id}` | `IPaiementService.findById` | 200 | 404 |
| MaintenanceController | POST `/api/maintenances` | `IMaintenanceService.create` | 201 | 400 |
| MaintenanceController | GET `/api/maintenances` | `IMaintenanceService.findAll` | 200 | — |
| MaintenanceController | GET `/api/maintenances/{id}` | `IMaintenanceService.findById` | 200 | 404 |
| MaintenanceController | PUT `/api/maintenances/{id}` | `IMaintenanceService.update` | 200 | 400, 404 |
| MaintenanceController | DELETE `/api/maintenances/{id}` | `IMaintenanceService.deleteById` | 204 | 404 |

Un POST `/api/paiements` n'est pas mappé : Spring répond 405. Les montants, tarifs, quantités et dates incohérents sont rejetés par les services et convertis en 400 par le gestionnaire d'exceptions.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| Contrat → Paiements | `@JsonManagedReference` sur `Contrat.paiements` et `@JsonBackReference` sur `Paiement.contrat` | Le contrat sérialise la liste, le paiement ne ré-embarque pas son contrat. Cette paire évite la boucle et permet à Jackson de renseigner le lien inverse à la désérialisation. |
| Agence → Véhicules | `@JsonIgnore` sur `Agence.vehicules` | Évite de traverser cette collection dans les réponses d'agence. |
| Autres relations bidirectionnelles | `@JsonIdentityInfo` sur les entités concernées | La première occurrence est développée, les références suivantes sont représentées par leur identifiant. |

`@JsonBackReference` désigne le côté inverse d'une paire nommée `@JsonManagedReference` et participe au rattachement lors de la lecture JSON. `@JsonIgnore` exclut simplement la propriété du traitement JSON ; il ne représente pas un lien inverse et ne le restaure pas à la désérialisation.

La création d'un contrat avec `paiements` utilise `CascadeType.ALL`. Les paiements sont persistés avec leur contrat et supprimés lors de la suppression de celui-ci. La mise à jour d'un contrat copie uniquement ses attributs modifiables : omettre `paiements` du corps ne supprime donc pas les paiements existants.

## C. Postman et Swagger

Postman rejoue des requêtes concrètes et vérifie leurs réponses. Dans le scénario de cascade, le POST d'un contrat avec deux paiements doit répondre 201, puis GET `/api/paiements` doit montrer ces deux paiements. Swagger UI présente le contrat OpenAPI synchronisé avec les contrôleurs ; par exemple, `GET /api/contrats/{id}` expose son paramètre d'identifiant et les réponses 200/404. Les deux outils sont complémentaires : exécution reproductible dans Postman, exploration et description de l'API dans Swagger.

Swagger UI : `/api/swagger-ui.html`. Document JSON : `/api/v3/api-docs`.

## D. Anomalies et corrections

| Anomalie | Correction |
|---|---|
| Couplage de l'URL `/api` dans les mappings des contrôleurs alors qu'il s'agit du context-path de l'application ; cela pouvait produire `/api/api/...`. | Le préfixe a été déplacé dans `server.servlet.context-path` et les mappings contrôleur sont relatifs (`/contrats`, `/paiements`, etc.). |
| Sérialisation cyclique et lien enfant-parent non fiable pour Contrat/Paiement. | Remplacement de l'identité Jackson sur cette association par la paire `@JsonManagedReference` / `@JsonBackReference`, et cascade JPA `ALL`. |
| Dépendances de contrôleurs : injection directement par constructeur plutôt que champ `@Autowired` (prévention du problème Sonar S6813). | Tous les contrôleurs utilisent `@RequiredArgsConstructor` et un champ `final` typé par l'interface `I...Service`. |

L'analyse SonarQube for IDE n'est pas intégrée au dépôt ; ces corrections sont vérifiées dans le code et ne sont pas présentées comme un rapport IDE. L'exposition directe d'entités JPA reste adaptée à cet atelier, mais une API de production devrait séparer les DTO du modèle de persistance pour maîtriser les champs et associations exposés.

## E. Questions de compréhension

1. **Quel composant reçoit les requêtes et choisit le contrôleur ?** Le `DispatcherServlet` reçoit les requêtes HTTP. Les mappings enregistrés par Spring relient la méthode, le chemin et les conditions HTTP à la méthode du contrôleur appropriée.
2. **Pourquoi ContratController dépend-il de `IContratService` ?** Il dépend du contrat de service, non du repository ni d'une implémentation concrète. Cela respecte les couches, réduit le couplage et facilite les tests et remplacements d'implémentation.
3. **Pourquoi POST renvoie 201 et DELETE 204 ? Pourquoi 404 au lieu de 500 ?** 201 signifie que la ressource a été créée ; 204 signifie que la suppression a réussi sans corps de réponse. La ressource absente est une erreur client connue, traduite par `ResourceNotFoundException` en 404, tandis que 500 est réservé aux erreurs serveur inattendues.
4. **Pourquoi PaiementController n'expose que GET ?** Les paiements sont gérés dans le cycle de vie de leur contrat par cascade ; le service de paiement est en lecture seule. Un POST sur `/api/paiements` reçoit 405 car aucune méthode POST n'est déclarée.
5. **Quels risques comporte l'exposition directe des entités ? Pourquoi ces annotations Jackson ?** Le schéma interne, des champs non prévus et des associations peuvent être exposés, les graphes peuvent devenir volumineux et les cycles peuvent provoquer une récursion. La paire Managed/Back Reference conserve les paiements dans la représentation du contrat sans boucle ; `@JsonIgnore` exclut entièrement la collection des véhicules d'une agence. Les DTO sont la solution de production pour séparer le modèle API du modèle JPA.
