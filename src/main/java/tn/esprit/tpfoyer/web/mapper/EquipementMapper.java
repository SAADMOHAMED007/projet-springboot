package tn.esprit.tpfoyer.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Equipement;
import tn.esprit.tpfoyer.web.dto.EquipementRequest;
import tn.esprit.tpfoyer.web.dto.EquipementResponse;

@Component
public class EquipementMapper implements EntityMapper<Equipement, EquipementRequest, EquipementResponse> {

    @Override
    public Equipement toEntity(EquipementRequest request) {
        Equipement equipement = new Equipement();
        equipement.setLibelle(request.libelle());
        equipement.setQuantite(request.quantite());
        return equipement;
    }

    @Override
    public EquipementResponse toResponse(Equipement equipement) {
        return new EquipementResponse(
                equipement.getIdEquipement(),
                equipement.getLibelle(),
                equipement.getQuantite()
        );
    }
}
