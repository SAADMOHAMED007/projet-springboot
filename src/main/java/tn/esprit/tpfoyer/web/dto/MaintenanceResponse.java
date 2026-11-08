package tn.esprit.tpfoyer.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceResponse(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        BigDecimal cout,
        Long idVehicule,
        String vehiculeImmatriculation
) {
}
