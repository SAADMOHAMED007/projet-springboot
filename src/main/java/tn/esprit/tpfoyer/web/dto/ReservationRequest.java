package tn.esprit.tpfoyer.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tn.esprit.tpfoyer.domain.StatutReservation;

import java.time.LocalDate;

public record ReservationRequest(

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        @NotNull(message = "La date de fin est obligatoire")
        LocalDate dateFin,

        @NotNull(message = "Le statut de la réservation est obligatoire")
        StatutReservation statut,

        @NotNull(message = "L'identifiant du véhicule est obligatoire")
        @Positive(message = "L'identifiant du véhicule doit être positif")
        Long idVehicule,

        @NotNull(message = "L'identifiant du client est obligatoire")
        @Positive(message = "L'identifiant du client doit être positif")
        Long idClient
) {
}
