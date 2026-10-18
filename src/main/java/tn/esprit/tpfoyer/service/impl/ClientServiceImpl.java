package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.repository.IClientRepository;
import tn.esprit.tpfoyer.service.IClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends AbstractCrudService<Client> implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    protected JpaRepository<Client, Long> repository() {
        return clientRepository;
    }

    @Override
    protected Long getId(Client client) {
        return client.getIdClient();
    }

    @Override
    protected String entityName() {
        return "Client";
    }

    @Override
    protected void validate(Client client) {
        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
        if (client.getEmail() == null || client.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'email du client est obligatoire");
        }
    }

    @Override
    protected void copyMutableFields(Client existing, Client updates) {
        existing.setNom(updates.getNom());
        existing.setPrenom(updates.getPrenom());
        existing.setEmail(updates.getEmail());
        existing.setTelephone(updates.getTelephone());
        existing.setNumPermis(updates.getNumPermis());
        existing.setDateInscription(updates.getDateInscription());
    }
}
