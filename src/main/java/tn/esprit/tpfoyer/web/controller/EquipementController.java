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
import tn.esprit.tpfoyer.service.IEquipementService;
import tn.esprit.tpfoyer.web.dto.EquipementRequest;
import tn.esprit.tpfoyer.web.dto.EquipementResponse;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements")
public class EquipementController {
    private final IEquipementService equipementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un équipement")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Équipement créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public EquipementResponse create(@Valid @RequestBody EquipementRequest request) {
        return equipementService.create(request);
    }

    @GetMapping
    @Operation(summary = "Lister les équipements")
    @ApiResponse(responseCode = "200", description = "Liste des équipements")
    public List<EquipementResponse> findAll() {
        return equipementService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un équipement")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement trouvé"), @ApiResponse(responseCode = "404", description = "Équipement introuvable")})
    public EquipementResponse findById(@Parameter(description = "Identifiant de l'équipement") @PathVariable Long id) {
        return equipementService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un équipement")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Équipement modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Équipement introuvable")})
    public EquipementResponse update(@Parameter(description = "Identifiant de l'équipement") @PathVariable Long id,
                                     @Valid @RequestBody EquipementRequest request) {
        return equipementService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un équipement")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Équipement supprimé"), @ApiResponse(responseCode = "404", description = "Équipement introuvable")})
    public void deleteById(@Parameter(description = "Identifiant de l'équipement") @PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
