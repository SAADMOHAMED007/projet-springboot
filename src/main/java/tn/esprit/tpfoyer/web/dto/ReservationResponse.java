package tn.esprit.tpfoyer.web.dto;

import tn.esprit.tpfoyer.domain.StatutReservation;

import java.time.LocalDate;

public record ReservationResponse(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        Long idVehicule,
        String vehiculeImmatriculation,
        Long idClient,
        String clientNom,
        String clientPrenom,
        Long idContrat
) {
}
