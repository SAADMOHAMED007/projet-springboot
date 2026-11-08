package tn.esprit.tpfoyer.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.service.IClientService;
import tn.esprit.tpfoyer.web.dto.ClientRequest;
import tn.esprit.tpfoyer.web.dto.ClientResponse;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {
    private final IClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un client")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Client créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public ClientResponse create(@Valid @RequestBody ClientRequest request) {
        return clientService.create(request);
    }

    @GetMapping
    @Operation(summary = "Lister les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients")
    public List<ClientResponse> findAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un client")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Client trouvé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
    public ClientResponse findById(@Parameter(description = "Identifiant du client") @PathVariable Long id) {
        return clientService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un client")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Client modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
    public ClientResponse update(@Parameter(description = "Identifiant du client") @PathVariable Long id,
                                 @Valid @RequestBody ClientRequest request) {
        return clientService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un client")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Client supprimé"), @ApiResponse(responseCode = "404", description = "Client introuvable")})
    public void deleteById(@Parameter(description = "Identifiant du client") @PathVariable Long id) {
        clientService.deleteById(id);
    }
}
