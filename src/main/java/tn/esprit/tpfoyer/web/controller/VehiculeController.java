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
import tn.esprit.tpfoyer.service.IVehiculeService;
import tn.esprit.tpfoyer.web.dto.VehiculeRequest;
import tn.esprit.tpfoyer.web.dto.VehiculeResponse;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "Gestion des véhicules")
public class VehiculeController {
    private final IVehiculeService vehiculeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Véhicule créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public VehiculeResponse create(@Valid @RequestBody VehiculeRequest request) {
        return vehiculeService.create(request);
    }

    @GetMapping
    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules")
    public List<VehiculeResponse> findAll() {
        return vehiculeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule trouvé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public VehiculeResponse findById(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Véhicule modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public VehiculeResponse update(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id,
                                   @Valid @RequestBody VehiculeRequest request) {
        return vehiculeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un véhicule")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Véhicule supprimé"), @ApiResponse(responseCode = "404", description = "Véhicule introuvable")})
    public void deleteById(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
