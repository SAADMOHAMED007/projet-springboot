package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Equipement;
import tn.esprit.tpfoyer.repository.IEquipementRepository;
import tn.esprit.tpfoyer.service.IEquipementService;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl extends AbstractCrudService<Equipement> implements IEquipementService {

    private final IEquipementRepository equipementRepository;

    @Override
    protected JpaRepository<Equipement, Long> repository() {
        return equipementRepository;
    }

    @Override
    protected Long getId(Equipement equipement) {
        return equipement.getIdEquipement();
    }

    @Override
    protected String entityName() {
        return "Équipement";
    }

    @Override
    protected void validate(Equipement equipement) {
        if (equipement.getLibelle() == null || equipement.getLibelle().isBlank()) {
            throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
        }
    }

    @Override
    protected void copyMutableFields(Equipement existing, Equipement updates) {
        existing.setLibelle(updates.getLibelle());
    }
}
