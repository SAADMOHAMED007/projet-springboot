package tn.esprit.tpfoyer.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.web.dto.ClientRequest;
import tn.esprit.tpfoyer.web.dto.ClientResponse;

@Component
public class ClientMapper implements EntityMapper<Client, ClientRequest, ClientResponse> {

    @Override
    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setNom(request.nom());
        client.setPrenom(request.prenom());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNumPermis(request.numPermis());
        client.setDateInscription(request.dateInscription());
        return client;
    }

    @Override
    public ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription()
        );
    }
}
