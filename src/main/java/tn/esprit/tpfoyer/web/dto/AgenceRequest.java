package tn.esprit.tpfoyer.web.dto;

public record AgenceRequest(
        String nom,
        String ville,
        String adresse,
        String telephone
) {
}
