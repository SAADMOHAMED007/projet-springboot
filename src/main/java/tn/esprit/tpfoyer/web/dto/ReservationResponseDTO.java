package tn.esprit.tpfoyer.web.dto;

import tn.esprit.tpfoyer.domain.StatutReservation;

import java.time.LocalDate;

public record ReservationResponseDTO(
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
