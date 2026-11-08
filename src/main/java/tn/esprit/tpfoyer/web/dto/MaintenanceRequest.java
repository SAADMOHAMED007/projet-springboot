package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceRequest(

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
        String description,

        @NotNull(message = "Le coût de la maintenance est obligatoire")
        @DecimalMin(value = "0.00", message = "Le coût de la maintenance ne peut pas être négatif")
        @Digits(integer = 10, fraction = 2, message = "Le coût accepte au maximum 10 chiffres et 2 décimales")
        BigDecimal cout,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long idVehicule
) {
}
