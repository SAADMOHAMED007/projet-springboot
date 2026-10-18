package tn.esprit.tpfoyer.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.service.IClientService;
import tn.esprit.tpfoyer.web.dto.ClientRequest;
import tn.esprit.tpfoyer.web.dto.ClientResponse;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse create(@RequestBody ClientRequest request) {
        return toResponse(clientService.create(toEntity(request)));
    }

    @GetMapping
    public List<ClientResponse> findAll() {
        return clientService.findAll().stream().map(ClientController::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ClientResponse findById(@PathVariable Long id) {
        return toResponse(clientService.findById(id));
    }

    @PutMapping("/{id}")
    public ClientResponse update(@PathVariable Long id, @RequestBody ClientRequest request) {
        return toResponse(clientService.update(id, toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.deleteById(id);
    }

    private static Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setNom(request.nom());
        client.setPrenom(request.prenom());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNumPermis(request.numPermis());
        client.setDateInscription(request.dateInscription());
        return client;
    }

    private static ClientResponse toResponse(Client client) {
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
