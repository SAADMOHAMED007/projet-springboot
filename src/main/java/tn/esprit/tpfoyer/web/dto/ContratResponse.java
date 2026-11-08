package tn.esprit.tpfoyer.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ContratResponse(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        boolean valide,
        Long idReservation,
        List<PaiementResponse> paiements
) {
}
