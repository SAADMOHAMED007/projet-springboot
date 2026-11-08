package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ContratRequest(

        @NotNull(message = "La date de signature est obligatoire")
        LocalDate dateSignature,

        @NotNull(message = "Le montant total du contrat est obligatoire")
        @DecimalMin(value = "0.00", message = "Le montant total du contrat ne peut pas être négatif")
        @Digits(integer = 10, fraction = 2, message = "Le montant total accepte au maximum 10 chiffres et 2 décimales")
        BigDecimal montantTotal,

        boolean valide,

        @Positive(message = "L'identifiant de la réservation doit être positif")
        Long idReservation,

        @Valid
        List<@Valid PaiementRequest> paiements
) {
}
