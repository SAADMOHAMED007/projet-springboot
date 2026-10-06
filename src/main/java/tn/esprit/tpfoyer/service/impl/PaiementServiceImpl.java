package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.repository.PaiementRepository;
import tn.esprit.tpfoyer.service.PaiementService;

@Service
public class PaiementServiceImpl extends AbstractCrudService<Paiement> implements PaiementService {

    public PaiementServiceImpl(PaiementRepository repository) {
        super(repository);
    }
}
