package tn.esprit.tpfoyer.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.web.dto.PaiementRequest;
import tn.esprit.tpfoyer.web.dto.PaiementResponse;

@Component
public class PaiementMapper implements EntityMapper<Paiement, PaiementRequest, PaiementResponse> {

    @Override
    public Paiement toEntity(PaiementRequest request) {
        Paiement paiement = new Paiement();
        paiement.setMontant(request.montant());
        paiement.setDatePaiement(request.datePaiement());
        paiement.setModePaiement(request.modePaiement());
        return paiement;
    }

    @Override
    public PaiementResponse toResponse(Paiement paiement) {
        return new PaiementResponse(
                paiement.getIdPaiement(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement(),
                paiement.getContrat() != null ? paiement.getContrat().getIdContrat() : null
        );
    }
}
