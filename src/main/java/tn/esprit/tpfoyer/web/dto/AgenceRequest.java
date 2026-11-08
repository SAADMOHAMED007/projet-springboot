package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AgenceRequest(

        @NotBlank(message = "Le nom de l'agence est obligatoire")
        @Size(max = 100, message = "Le nom de l'agence ne doit pas dépasser 100 caractères")
        String nom,

        @NotBlank(message = "La ville de l'agence est obligatoire")
        @Size(max = 100, message = "La ville ne doit pas dépasser 100 caractères")
        String ville,

        @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
        String adresse,

        @Pattern(regexp = "^$|^[0-9+\\- ]{8,20}$",
                message = "Le téléphone doit contenir entre 8 et 20 chiffres, espaces, '+' ou '-'")
        String telephone
) {
}
