package tn.esprit.tpfoyer.web.dto;

public record AgenceResponse(
        Long idAgence,
        String nom,
        String ville,
        String adresse,
        String telephone
) {
}
