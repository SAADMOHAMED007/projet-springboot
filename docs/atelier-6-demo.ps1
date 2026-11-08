# Atelier 6 - DTO, validation et gestion centralisee des erreurs.
# Prerequis : l'API doit tourner avec le profil demo :
#   .\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=demo"
# Usage :
#   powershell -ExecutionPolicy Bypass -File .\docs\atelier-6-demo.ps1

$ErrorActionPreference = 'Stop'
$Base = 'http://localhost:8081/api'

function Invoke-Api {
    param([string]$Method, [string]$Path, [string]$Body)

    $params = @{ Uri = "$Base$Path"; Method = $Method; UseBasicParsing = $true }
    if ($Body) {
        $params.ContentType = 'application/json; charset=utf-8'
        $params.Body = [System.Text.Encoding]::UTF8.GetBytes($Body)
    }

    try {
        $response = Invoke-WebRequest @params
        return [pscustomobject]@{ Status = [int]$response.StatusCode; Content = $response.Content }
    } catch {
        $response = $_.Exception.Response
        if (-not $response) { throw }
        $reader = New-Object System.IO.StreamReader($response.GetResponseStream())
        return [pscustomobject]@{ Status = [int]$response.StatusCode; Content = $reader.ReadToEnd() }
    }
}

function Show-Call {
    param([string]$Title, [string]$Method, [string]$Path, [string]$Body)

    Write-Host ''
    Write-Host ('=' * 78)
    Write-Host $Title
    Write-Host "$Method $Base$Path"
    if ($Body) { Write-Host "Payload : $Body" }

    $result = Invoke-Api -Method $Method -Path $Path -Body $Body
    Write-Host "HTTP $($result.Status)"

    if ([string]::IsNullOrWhiteSpace($result.Content)) {
        Write-Host '(corps vide)'
    } else {
        try {
            $pretty = $result.Content | ConvertFrom-Json | ConvertTo-Json -Depth 10
            Write-Host $pretty
        } catch {
            Write-Host $result.Content
        }
    }
    return $result
}

Write-Host 'ATELIER 6 - DTO + VALIDATION + GESTION CENTRALISEE DES ERREURS'
Write-Host "Base URL : $Base"

$suffix = Get-Date -Format 'yyyyMMddHHmmss'
$immatriculation = "TN-$suffix"
$email = "samir.benali.$suffix@example.com"

# --- 1. Creations nominales : les reponses sont des DTO ---------------------

$agence = Show-Call '1. Creer une agence (DTO de reponse)' 'POST' '/agences' @'
{"nom":"Agence Tunis Centre","ville":"Tunis","adresse":"12 avenue Habib Bourguiba","telephone":"71234567"}
'@
$agenceId = ($agence.Content | ConvertFrom-Json).idAgence

$client = Show-Call '2. Creer un client (DTO de requete valide)' 'POST' '/clients' (@"
{"nom":"Ben Ali","prenom":"Samir","email":"$email","telephone":"98123456","numPermis":"PERMIS-$suffix","dateInscription":"2026-10-01"}
"@)
$clientId = ($client.Content | ConvertFrom-Json).idClient

$equipement = Show-Call '3. Creer un equipement' 'POST' '/equipements' @'
{"libelle":"GPS","quantite":2}
'@
$equipementId = ($equipement.Content | ConvertFrom-Json).idEquipement

$employe = Show-Call '4. Creer un employe rattache a l agence par idAgence' 'POST' '/employes' (@"
{"nom":"Trabelsi","prenom":"Nadia","role":"MANAGER","idAgence":$agenceId}
"@)

$vehicule = Show-Call '5. Creer un vehicule (agence + equipements par identifiants)' 'POST' '/vehicules' (@"
{"immatriculation":"$immatriculation","marque":"Renault","modele":"Clio","categorie":"CITADINE","tarifJournalier":120.00,"statut":"DISPONIBLE","idAgence":$agenceId,"equipementIds":[$equipementId]}
"@)
$vehiculeId = ($vehicule.Content | ConvertFrom-Json).idVehicule

$reservation = Show-Call '6. Creer une reservation (vehicule + client par identifiants)' 'POST' '/reservations' (@"
{"dateDebut":"2026-11-10","dateFin":"2026-11-15","statut":"CONFIRMEE","idVehicule":$vehiculeId,"idClient":$clientId}
"@)
$reservationId = ($reservation.Content | ConvertFrom-Json).idReservation

$contrat = Show-Call '7. Creer un contrat avec deux paiements imbriques' 'POST' '/contrats' (@"
{"dateSignature":"2026-11-02","montantTotal":480.00,"valide":true,"idReservation":$reservationId,"paiements":[{"montant":150.00,"datePaiement":"2026-11-02","modePaiement":"CARTE"},{"montant":330.00,"datePaiement":"2026-11-06","modePaiement":"VIREMENT"}]}
"@)
$contratId = ($contrat.Content | ConvertFrom-Json).idContrat

$null = Show-Call '8. Creer une maintenance' 'POST' '/maintenances' (@"
{"dateDebut":"2026-11-20","dateFin":"2026-11-21","description":"Vidange","cout":250.00,"idVehicule":$vehiculeId}
"@)

# --- 2. Lecture : DTO plats, sans graphe d entites --------------------------

$null = Show-Call '9. Lister les agences' 'GET' '/agences'
$null = Show-Call '10. Consulter le vehicule cree (agence et equipements resumes)' 'GET' "/vehicules/$vehiculeId"
$null = Show-Call '11. Consulter la reservation (identifiants et libelles utiles)' 'GET' "/reservations/$reservationId"
$null = Show-Call '12. Consulter le contrat et ses paiements' 'GET' "/contrats/$contratId"
$null = Show-Call '13. Lister les paiements' 'GET' '/paiements'
$null = Show-Call '14. Lister les maintenances' 'GET' '/maintenances'

# --- 3. Mise a jour sans perte des paiements -------------------------------

$null = Show-Call '15. Modifier le contrat sans envoyer paiements' 'PUT' "/contrats/$contratId" @'
{"dateSignature":"2026-11-02","montantTotal":520.00,"valide":true}
'@
$null = Show-Call '16. Verifier que les paiements sont conserves' 'GET' "/contrats/$contratId"

# --- 4. Validation et erreurs centralisees ---------------------------------

$null = Show-Call '17. Validation : nom et ville d agence manquants' 'POST' '/agences' @'
{"nom":" ","ville":""}
'@
$null = Show-Call '18. Validation : email client invalide' 'POST' '/clients' @'
{"nom":"Ben Ali","prenom":"Samir","email":"pas-un-email","telephone":"98123456"}
'@
$null = Show-Call '19. Validation : tarif negatif et enums absentes' 'POST' '/vehicules' @'
{"immatriculation":"TN-9999","marque":"Renault","modele":"Clio","tarifJournalier":-1}
'@
$null = Show-Call '20. Validation : champs obligatoires absents dans une reservation' 'POST' '/reservations' @'
{}
'@
$null = Show-Call '21. JSON illisible : enumeration inconnue' 'POST' '/vehicules' @'
{"immatriculation":"TN-8888","marque":"Renault","modele":"Clio","categorie":"CATAMARAN","tarifJournalier":100.00,"statut":"DISPONIBLE"}
'@
$null = Show-Call '22. Reference inexistante : vehicule 9999 dans une reservation' 'POST' '/reservations' (@"
{"dateDebut":"2026-11-10","dateFin":"2026-11-15","statut":"EN_ATTENTE","idVehicule":9999,"idClient":$clientId}
"@)
$null = Show-Call '23. Ressource inexistante' 'GET' '/agences/9999'
$null = Show-Call '24. Conflit d unicite : immatriculation deja utilisee' 'POST' '/vehicules' (@"
{"immatriculation":"$immatriculation","marque":"Peugeot","modele":"208","categorie":"CITADINE","tarifJournalier":110.00,"statut":"DISPONIBLE","idAgence":$agenceId}
"@)
$null = Show-Call '25. Methode HTTP non supportee sur les paiements' 'POST' '/paiements' @'
{"montant":10.00}
'@

Write-Host ''
Write-Host ('=' * 78)
Write-Host 'Fin du scenario Atelier 6.'
