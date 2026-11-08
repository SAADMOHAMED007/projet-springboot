package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import tn.esprit.tpfoyer.domain.CategorieVehicule;
import tn.esprit.tpfoyer.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.Set;

public record VehiculeRequest(

        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
        String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
        String modele,

        @NotNull(message = "La catégorie est obligatoire")
        CategorieVehicule categorie,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @DecimalMin(value = "0.00", message = "Le tarif journalier ne peut pas être négatif")
        @Digits(integer = 8, fraction = 2, message = "Le tarif journalier accepte au maximum 8 chiffres et 2 décimales")
        BigDecimal tarifJournalier,

        @NotNull(message = "Le statut du véhicule est obligatoire")
        StatutVehicule statut,

        @Positive(message = "L'identifiant de l'agence doit être positif")
        Long idAgence,

        Set<@NotNull(message = "Un identifiant d'équipement ne peut pas être nul")
            @Positive(message = "Un identifiant d'équipement doit être positif") Long> equipementIds
) {
}
