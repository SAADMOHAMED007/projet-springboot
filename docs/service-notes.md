# Atelier 4 — Couche Service, IoC et injection

Le projet utilise les packages `tn.esprit.tpfoyer` existants (domain, repository,
service et web). Ils ne sont pas renommés en `tn.esprit.autoloc` afin de garder
les couches déjà présentes cohérentes avec la classe principale du projet.

## A. Modes d'injection comparés

| Critère | Injection par constructeur | Injection par attribut |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dépendance visible dans la signature | Oui, dans le constructeur | Non, seulement sur le champ |
| Utilisable avec `new` hors conteneur sans dépendance nulle | Non : il faut fournir la dépendance | Oui techniquement, mais le champ reste nul hors Spring |
| Signalement SonarQube (S6813) | Non attendu | Généralement signalé, car l'injection par constructeur est préférable |

Les services de ce projet utilisent `@RequiredArgsConstructor` et des
dépendances `private final`, donc l'injection par constructeur générée par
Lombok. Aucun `@Autowired` n'est nécessaire.

## B. Services et dépendances

| Service | Dépendance injectée | Mode | Justification |
|---|---|---|---|
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Lecture seule, aucune opération de mutation exposée |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | Dépendance obligatoire, finale et typée par interface |

Les services CRUD héritent de `AbstractCrudService` sans état, qui centralise
les gardes communes : refus d'un identifiant à la création, recherche avec
exception métier, vérification à la suppression et mise à jour de l'entité
chargée plutôt que remplacement par l'objet reçu.

Les règles de gestion suivent les champs présents dans le modèle : nom pour
Agence et Employé, nom et e-mail pour Client, libellé pour Équipement, tarif
journalier non négatif pour Véhicule, montant non négatif pour Contrat et dates
cohérentes pour Réservation et Maintenance. Les modèles ne possèdent pas de
quantité pour Équipement, de kilométrage pour Véhicule, ni de coût pour
Maintenance ; aucune règle portant sur ces champs absents n'est ajoutée.

## C. Diagnostic des erreurs de démarrage

1. **Bean de service introuvable** : l'implémentation peut ne pas être annotée
   `@Service`, ou se trouver hors du package couvert par le component scan.
   Vérifier l'annotation et placer l'implémentation sous le package de la classe
   `@SpringBootApplication` (ou configurer explicitement le scan).
2. **Deux beans pour une dépendance** : annoter une implémentation avec
   `@Primary` pour la rendre prioritaire, ou ajouter `@Qualifier("nomDuBean")`
   au paramètre du constructeur consommateur pour sélectionner explicitement
   l'implémentation voulue.
3. **Cycle client/réservation** : l'injection par constructeur exige que chaque
   dépendance soit disponible avant la création du bean ; le cycle ne peut donc
   pas être résolu par une instance partiellement construite. Supprimer la
   dépendance circulaire en déplaçant l'orchestration dans un service dédié,
   ou en faisant dépendre les services d'une abstraction à sens unique.

## D. Anomalies relevées et corrections

| Anomalie / règle | Correction |
|---|---|
| Injection par attribut, signalée par S6813 dans l'exercice | Retour à l'injection par constructeur avec champs `final` et `@RequiredArgsConstructor` |
| Exception générique `RuntimeException` de l'expérience S112 | Utilisation de `ResourceNotFoundException` pour une ressource absente et d'`IllegalArgumentException` pour les entrées invalides |
| La sauvegarde directe lors d'une mise à jour pouvait écraser les associations et des champs absents du corps reçu | `update` charge l'existant puis copie uniquement les champs modifiables |
| Le service et le contrôleur Paiement exposaient création, modification et suppression | `IPaiementService` et `PaiementController` n'exposent désormais que la lecture |

Les règles S6813 et S112 ci-dessus sont celles indiquées par l'exercice.
SonarQube for IDE n'étant pas configuré dans ce dépôt, elles sont documentées
d'après les consignes de l'atelier et ne sont pas présentées comme une analyse
IDE effectivement exécutée.

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?** Le code
   applicatif n'en écrit pas. Le conteneur Spring détecte la classe `@Service`,
   construit le bean et fournit le proxy repository à son constructeur.
2. **Pourquoi un contrôleur dépend-il de `IContratService` plutôt que de
   `ContratServiceImpl` ?** Le contrôleur dépend du contrat métier, pas de son
   implémentation. Cela réduit le couplage et facilite le remplacement ou le
   mock du service.
3. **Pourquoi un singleton doit-il rester sans état ?** Un singleton est partagé
   entre les requêtes. Un champ mutable contenant, par exemple, l'identifiant
   du client courant pourrait être remplacé par une autre requête et provoquer
   une mise à jour sur le mauvais client.
4. **Pourquoi charger l'entité avant `save` dans `update` ?** Cela conserve les
   valeurs et associations non fournies dans la requête et ne copie que les
   champs modifiables. L'identifiant reste celui de la ligne chargée.
5. **Quelle différence entre `@Component` et `@Bean` ?** `@Component` marque
   une classe détectée par component scan ; `@Bean` est une méthode de
   configuration qui construit explicitement un bean. `@Primary` choisit le
   bean par défaut parmi plusieurs candidats, tandis que `@Qualifier` sélectionne
   un bean précis au point d'injection. `IPaiementService` est en lecture seule
   car les paiements sont créés, modifiés et supprimés dans le contexte du
   Contrat.
