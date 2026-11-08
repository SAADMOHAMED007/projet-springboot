package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.repository.IClientRepository;
import tn.esprit.tpfoyer.service.IClientService;
import tn.esprit.tpfoyer.web.dto.ClientRequest;
import tn.esprit.tpfoyer.web.dto.ClientResponse;
import tn.esprit.tpfoyer.web.mapper.ClientMapper;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends AbstractCrudService<Client, ClientRequest, ClientResponse>
        implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    protected JpaRepository<Client, Long> repository() {
        return clientRepository;
    }

    @Override
    protected ClientMapper mapper() {
        return clientMapper;
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
