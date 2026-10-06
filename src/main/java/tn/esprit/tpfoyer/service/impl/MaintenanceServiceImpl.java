package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.repository.MaintenanceRepository;
import tn.esprit.tpfoyer.service.MaintenanceService;

@Service
public class MaintenanceServiceImpl extends AbstractCrudService<Maintenance> implements MaintenanceService {

    public MaintenanceServiceImpl(MaintenanceRepository repository) {
        super(repository);
    }
}
