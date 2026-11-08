package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record EquipementRequest(

        @NotBlank(message = "Le libellé de l'équipement est obligatoire")
        @Size(max = 100, message = "Le libellé ne doit pas dépasser 100 caractères")
        String libelle,

        @NotNull(message = "La quantité de l'équipement est obligatoire")
        @PositiveOrZero(message = "La quantité de l'équipement ne peut pas être négative")
        Integer quantite
) {
}
