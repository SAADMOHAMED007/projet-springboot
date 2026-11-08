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
import tn.esprit.tpfoyer.web.dto.ReservationResponse;

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
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return reservationService.create(request);
    }

    @GetMapping
    @Operation(summary = "Lister les réservations")
    @ApiResponse(responseCode = "200", description = "Liste des réservations")
    public List<ReservationResponse> findAll() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une réservation")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Réservation trouvée"), @ApiResponse(responseCode = "404", description = "Réservation introuvable")})
    public ReservationResponse findById(@Parameter(description = "Identifiant de la réservation") @PathVariable Long id) {
        return reservationService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une réservation")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Réservation modifiée"), @ApiResponse(responseCode = "400", description = "Données invalides"), @ApiResponse(responseCode = "404", description = "Réservation introuvable")})
    public ReservationResponse update(@Parameter(description = "Identifiant de la réservation") @PathVariable Long id,
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
}
