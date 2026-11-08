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
import tn.esprit.tpfoyer.service.IEmployeService;
import tn.esprit.tpfoyer.web.dto.EmployeRequest;
import tn.esprit.tpfoyer.web.dto.EmployeResponse;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {
    private final IEmployeService employeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un employé")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Employé créé"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public EmployeResponse create(@Valid @RequestBody EmployeRequest request) {
        return employeService.create(request);
    }

    @GetMapping
    @Operation(summary = "Lister les employés")
    @ApiResponse(responseCode = "200", description = "Liste des employés")
    public List<EmployeResponse> findAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un employé")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé trouvé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public EmployeResponse findById(@Parameter(description = "Identifiant de l'employé") @PathVariable Long id) {
        return employeService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un employé")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Employé modifié"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public EmployeResponse update(@Parameter(description = "Identifiant de l'employé") @PathVariable Long id,
                                  @Valid @RequestBody EmployeRequest request) {
        return employeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un employé")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Employé supprimé"), @ApiResponse(responseCode = "404", description = "Employé introuvable")})
    public void deleteById(@Parameter(description = "Identifiant de l'employé") @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
