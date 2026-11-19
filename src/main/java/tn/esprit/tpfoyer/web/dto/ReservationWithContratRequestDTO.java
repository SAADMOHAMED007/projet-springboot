package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tn.esprit.tpfoyer.domain.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Requête composite : crée une réservation et le contrat qui lui est associé
 * en une seule opération.
 */
public record ReservationWithContratRequestDTO(

        @NotNull(message = "La date de début de la réservation est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin de la réservation est obligatoire")
        LocalDate dateFin,

        @NotNull(message = "Le statut de la réservation est obligatoire")
        StatutReservation statut,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long idVehicule,

        @NotNull(message = "L'identifiant du client est obligatoire")
        @Positive(message = "L'identifiant du client doit être positif")
        Long idClient,

        @NotNull(message = "La date de signature du contrat est obligatoire")
        LocalDate dateSignature,

        @NotNull(message = "Le montant total du contrat est obligatoire")
        @DecimalMin(value = "0.00", message = "Le montant total du contrat ne peut pas être négatif")
        @Digits(integer = 10, fraction = 2, message = "Le montant total accepte au maximum 10 chiffres et 2 décimales")
        BigDecimal montantTotal,

        Boolean valide,

        List<@Valid PaiementRequest> paiements
) {
}
