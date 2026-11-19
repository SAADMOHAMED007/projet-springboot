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
import tn.esprit.tpfoyer.service.IReservationService;
import tn.esprit.tpfoyer.web.dto.ReservationRequest;
import tn.esprit.tpfoyer.web.dto.ReservationResponseDTO;
import tn.esprit.tpfoyer.web.dto.ReservationWithContratRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationController {
    private final IReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une réservation")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Réservation créée"), @ApiResponse(responseCode = "400", description = "Données invalides")})
    public ReservationResponseDTO create(@Valid @RequestBody ReservationRequest request) {
        return reservationService.create(request);
    }

    @GetMapping
    @Operation(summary = "Lister les réservations")
    @ApiResponse(responseCode = "200", description = "Liste des réservations")
    public List<ReservationResponseDTO> findAll() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une réservation")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Réservation trouvée"), @ApiResponse(responseCode = "404", description = "Réservation introuvable")})
    public ReservationResponseDTO findById(@Parameter(description = "Identifiant de la réservation") @PathVariable Long id) {
        return reservationService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une réservation")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Réservation modifiée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Réservation introuvable")})
    public ReservationResponseDTO update(@Parameter(description = "Identifiant de la réservation") @PathVariable Long id,
                                      @Valid @RequestBody ReservationRequest request) {
        return reservationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une réservation")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Réservation supprimée"), @ApiResponse(responseCode = "404", description = "Réservation introuvable")})
    public void deleteById(@Parameter(description = "Identifiant de la réservation") @PathVariable Long id) {
        reservationService.deleteById(id);
    }

    @PutMapping("/{id}/client")
    @Operation(summary = "Affecter une réservation à un client",
            description = "Recherche le client par son nom et son prénom, puis le rattache à la réservation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réservation affectée au client"),
            @ApiResponse(responseCode = "404", description = "Réservation ou client introuvable")
    })
    public ReservationResponseDTO affecterReservationAClient(
            @Parameter(description = "Identifiant de la réservation") @PathVariable Long id,
            @Parameter(description = "Nom du client") @RequestParam String nom,
            @Parameter(description = "Prénom du client") @RequestParam String prenom) {
        return reservationService.affecterReservationAClient(id, nom, prenom);
    }

    @PostMapping("/avec-contrat")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une réservation et son contrat associé",
            description = "Crée la réservation puis le contrat qui lui est rattaché, en une seule transaction.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Réservation et contrat créés"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Véhicule ou client introuvable")
    })
    public ReservationResponseDTO creerReservationEtContratAssocie(
            @Valid @RequestBody ReservationWithContratRequestDTO requestDTO) {
        return reservationService.creerReservationEtContratAssocie(requestDTO);
    }
}
