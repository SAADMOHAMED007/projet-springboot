package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IContratRepository;
import tn.esprit.tpfoyer.repository.IReservationRepository;
import tn.esprit.tpfoyer.service.IContratService;
import tn.esprit.tpfoyer.web.dto.ContratDTO;
import tn.esprit.tpfoyer.web.dto.ContratRequest;
import tn.esprit.tpfoyer.web.mapper.ContratMapper;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl extends AbstractCrudService<Contrat, ContratRequest, ContratDTO>
        implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    protected JpaRepository<Contrat, Long> repository() {
        return contratRepository;
    }

    @Override
    protected ContratMapper mapper() {
        return contratMapper;
    }

    @Override
    protected String entityName() {
        return "Contrat";
    }

    @Override
    protected void validate(Contrat contrat) {
        if (contrat.getMontantTotal() != null && contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("Le montant total du contrat ne peut pas être négatif");
        }
    }

    @Override
    protected void copyMutableFields(Contrat existing, Contrat updates) {
        existing.setDateSignature(updates.getDateSignature());
        existing.setMontantTotal(updates.getMontantTotal());
        existing.setValide(updates.isValide());
        if (updates.getReservation() != null) {
            existing.setReservation(updates.getReservation());
        }
    }

    @Override
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        Contrat contrat = contratRepository.findFirstByDateSignature(dateSignature)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contrat introuvable avec la date de signature " + dateSignature));
        Reservation reservation = reservationRepository.findFirstByDateDebutAndDateFin(dateDebut, dateFin)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Réservation introuvable du " + dateDebut + " au " + dateFin));

        Contrat contratExistant = reservation.getContrat();
        if (contratExistant != null && !contratExistant.getIdContrat().equals(contrat.getIdContrat())) {
            throw new IllegalArgumentException(
                    "La réservation %d est déjà associée au contrat %d"
                            .formatted(reservation.getIdReservation(), contratExistant.getIdContrat()));
        }

        contrat.setReservation(reservation);
        reservation.setContrat(contrat);

        return contratMapper.toResponse(contratRepository.save(contrat));
    }
}
