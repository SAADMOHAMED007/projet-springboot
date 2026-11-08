package tn.esprit.tpfoyer.web.dto;

import tn.esprit.tpfoyer.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaiementResponse(
        Long idPaiement,
        BigDecimal montant,
        LocalDate datePaiement,
        ModePaiement modePaiement,
        Long idContrat
) {
}
