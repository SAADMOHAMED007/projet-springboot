package tn.esprit.tpfoyer.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IReservationRepository;
import tn.esprit.tpfoyer.web.dto.ContratRequest;
import tn.esprit.tpfoyer.web.dto.ContratResponse;
import tn.esprit.tpfoyer.web.dto.PaiementRequest;
import tn.esprit.tpfoyer.web.dto.PaiementResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContratMapper implements EntityMapper<Contrat, ContratRequest, ContratResponse> {

    private final IReservationRepository reservationRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public Contrat toEntity(ContratRequest request) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(request.dateSignature());
        contrat.setMontantTotal(request.montantTotal());
        contrat.setValide(request.valide());
        contrat.setReservation(resolveReservation(request.idReservation()));
        if (request.paiements() != null) {
            for (PaiementRequest paiementRequest : request.paiements()) {
                Paiement paiement = paiementMapper.toEntity(paiementRequest);
                paiement.setContrat(contrat);
                contrat.getPaiements().add(paiement);
            }
        }
        return contrat;
    }

    @Override
    public ContratResponse toResponse(Contrat contrat) {
        List<PaiementResponse> paiements = contrat.getPaiements().stream()
                .map(paiementMapper::toResponse)
                .toList();
        return new ContratResponse(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.isValide(),
                contrat.getReservation() != null ? contrat.getReservation().getIdReservation() : null,
                paiements
        );
    }

    private Reservation resolveReservation(Long idReservation) {
        if (idReservation == null) {
            return null;
        }
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation", idReservation));
    }
}
