package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientRequest(

        @NotBlank(message = "Le nom du client est obligatoire")
        @Size(max = 100, message = "Le nom du client ne doit pas dépasser 100 caractères")
        String nom,

        @NotBlank(message = "Le prénom du client est obligatoire")
        @Size(max = 100, message = "Le prénom du client ne doit pas dépasser 100 caractères")
        String prenom,

        @NotBlank(message = "L'email du client est obligatoire")
        @Email(message = "L'email du client doit être valide")
        @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
        String email,

        @Pattern(regexp = "^$|^[0-9+\\- ]{8,20}$",
                message = "Le téléphone doit contenir entre 8 et 20 chiffres, espaces, '+' ou '-'")
        String telephone,

        @Size(max = 50, message = "Le numéro de permis ne doit pas dépasser 50 caractères")
        String numPermis,

        LocalDate dateInscription
) {
}
