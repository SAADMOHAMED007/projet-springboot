package tn.esprit.tpfoyer.web.dto;

import tn.esprit.tpfoyer.domain.CategorieVehicule;
import tn.esprit.tpfoyer.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.List;

public record VehiculeResponse(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long idAgence,
        String agenceNom,
        List<EquipementResponse> equipements
) {
}
