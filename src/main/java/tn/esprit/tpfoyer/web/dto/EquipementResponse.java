package tn.esprit.tpfoyer.web.dto;

public record EquipementResponse(
        Long idEquipement,
        String libelle,
        Integer quantite
) {
}
