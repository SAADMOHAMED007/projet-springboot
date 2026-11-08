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
import tn.esprit.tpfoyer.service.IMaintenanceService;
import tn.esprit.tpfoyer.web.dto.MaintenanceRequest;
import tn.esprit.tpfoyer.web.dto.MaintenanceResponse;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {
    private final IMaintenanceService maintenanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une maintenance")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Maintenance créée"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public MaintenanceResponse create(@Valid @RequestBody MaintenanceRequest request) {
        return maintenanceService.create(request);
    }

    @GetMapping
    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances")
    public List<MaintenanceResponse> findAll() {
        return maintenanceService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Maintenance trouvée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable")})
    public MaintenanceResponse findById(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une maintenance")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Maintenance modifiée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable")})
    public MaintenanceResponse update(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id,
                                      @Valid @RequestBody MaintenanceRequest request) {
        return maintenanceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une maintenance")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Maintenance supprimée"), @ApiResponse(responseCode = "404", description = "Maintenance introuvable")})
    public void deleteById(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
