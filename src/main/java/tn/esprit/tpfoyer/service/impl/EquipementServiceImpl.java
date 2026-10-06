package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Equipement;
import tn.esprit.tpfoyer.repository.EquipementRepository;
import tn.esprit.tpfoyer.service.EquipementService;

@Service
public class EquipementServiceImpl extends AbstractCrudService<Equipement> implements EquipementService {

    public EquipementServiceImpl(EquipementRepository repository) {
        super(repository);
    }
}
