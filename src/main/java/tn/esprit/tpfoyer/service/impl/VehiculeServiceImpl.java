package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.repository.VehiculeRepository;
import tn.esprit.tpfoyer.service.VehiculeService;

@Service
public class VehiculeServiceImpl extends AbstractCrudService<Vehicule> implements VehiculeService {

    public VehiculeServiceImpl(VehiculeRepository repository) {
        super(repository);
    }
}
