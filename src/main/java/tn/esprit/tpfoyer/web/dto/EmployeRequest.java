package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import tn.esprit.tpfoyer.domain.RoleEmploye;

public record EmployeRequest(

        @NotBlank(message = "Le nom de l'employé est obligatoire")
        @Size(max = 100, message = "Le nom de l'employé ne doit pas dépasser 100 caractères")
        String nom,

        @NotBlank(message = "Le prénom de l'employé est obligatoire")
        @Size(max = 100, message = "Le prénom de l'employé ne doit pas dépasser 100 caractères")
        String prenom,

        @NotNull(message = "Le rôle de l'employé est obligatoire")
        RoleEmploye role,

        @Positive(message = "L'identifiant de l'agence doit être positif")
        Long idAgence
) {
}
