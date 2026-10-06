package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.repository.ClientRepository;
import tn.esprit.tpfoyer.service.ClientService;

@Service
public class ClientServiceImpl extends AbstractCrudService<Client> implements ClientService {

    public ClientServiceImpl(ClientRepository repository) {
        super(repository);
    }
}
