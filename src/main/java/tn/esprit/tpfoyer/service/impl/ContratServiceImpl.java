package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.repository.ContratRepository;
import tn.esprit.tpfoyer.service.ContratService;

@Service
public class ContratServiceImpl extends AbstractCrudService<Contrat> implements ContratService {

    public ContratServiceImpl(ContratRepository repository) {
        super(repository);
    }
}
