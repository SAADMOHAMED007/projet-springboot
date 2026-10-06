package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Employe;
import tn.esprit.tpfoyer.repository.EmployeRepository;
import tn.esprit.tpfoyer.service.EmployeService;

@Service
public class EmployeServiceImpl extends AbstractCrudService<Employe> implements EmployeService {

    public EmployeServiceImpl(EmployeRepository repository) {
        super(repository);
    }
}
