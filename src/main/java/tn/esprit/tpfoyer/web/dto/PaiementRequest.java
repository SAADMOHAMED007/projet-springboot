package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tn.esprit.tpfoyer.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementRequest(

        @NotNull(message = "Le montant du paiement est obligatoire")
        @DecimalMin(value = "0.01", message = "Le montant du paiement doit être strictement positif")
        @Digits(integer = 10, fraction = 2, message = "Le montant accepte au maximum 10 chiffres et 2 décimales")
        BigDecimal montant,

        @NotNull(message = "La date du paiement est obligatoire")
        LocalDate datePaiement,

        @NotNull(message = "Le mode de paiement est obligatoire")
        ModePaiement modePaiement
) {
}
