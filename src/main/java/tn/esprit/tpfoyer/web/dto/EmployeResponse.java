package tn.esprit.tpfoyer.web.dto;

import tn.esprit.tpfoyer.domain.RoleEmploye;

public record EmployeResponse(
        Long idEmploye,
        String nom,
        String prenom,
        RoleEmploye role,
        Long idAgence,
        String agenceNom
) {
}
