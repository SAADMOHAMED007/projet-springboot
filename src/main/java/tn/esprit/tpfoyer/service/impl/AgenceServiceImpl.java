package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.repository.AgenceRepository;
import tn.esprit.tpfoyer.service.AgenceService;

@Service
public class AgenceServiceImpl extends AbstractCrudService<Agence> implements AgenceService {

    public AgenceServiceImpl(AgenceRepository repository) {
        super(repository);
    }
}
