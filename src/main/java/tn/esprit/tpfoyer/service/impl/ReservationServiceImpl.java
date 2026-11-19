package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IClientRepository;
import tn.esprit.tpfoyer.repository.IContratRepository;
import tn.esprit.tpfoyer.repository.IReservationRepository;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.service.IReservationService;
import tn.esprit.tpfoyer.web.dto.ContratRequest;
import tn.esprit.tpfoyer.web.dto.ReservationRequest;
import tn.esprit.tpfoyer.web.dto.ReservationResponseDTO;
import tn.esprit.tpfoyer.web.dto.ReservationWithContratRequestDTO;
import tn.esprit.tpfoyer.web.mapper.ContratMapper;
import tn.esprit.tpfoyer.web.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl extends AbstractCrudService<Reservation, ReservationRequest, ReservationResponseDTO>
        implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper reservationMapper;
    private final ContratMapper contratMapper;

    @Override
    protected JpaRepository<Reservation, Long> repository() {
        return reservationRepository;
    }

    @Override
    protected ReservationMapper mapper() {
        return reservationMapper;
    }

    @Override
    protected String entityName() {
        return "Réservation";
    }

    @Override
    protected void validate(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être égale ou postérieure à la date de début");
        }
    }

    @Override
    protected void copyMutableFields(Reservation existing, Reservation updates) {
        existing.setDateDebut(updates.getDateDebut());
        existing.setDateFin(updates.getDateFin());
        existing.setStatut(updates.getStatut());
        existing.setVehicule(updates.getVehicule());
        existing.setClient(updates.getClient());
    }

    @Override
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = findEntity(idReservation);
        Client client = clientRepository.findFirstByNomIgnoreCaseAndPrenomIgnoreCase(nom, prenom)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Client introuvable avec le nom '%s' et le prénom '%s'".formatted(nom, prenom)));
        reservation.setClient(client);
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête ne peut pas être nulle");
        }

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.dateDebut());
        reservation.setDateFin(requestDTO.dateFin());
        reservation.setStatut(requestDTO.statut());
        Vehicule vehicule = vehiculeRepository.findById(requestDTO.idVehicule())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", requestDTO.idVehicule()));
        reservation.setVehicule(vehicule);
        Client client = clientRepository.findById(requestDTO.idClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client", requestDTO.idClient()));
        reservation.setClient(client);
        validate(reservation);

        Reservation savedReservation = reservationRepository.save(reservation);

        ContratRequest contratRequest = new ContratRequest(
                requestDTO.dateSignature(),
                requestDTO.montantTotal(),
                Boolean.TRUE.equals(requestDTO.valide()),
                null,
                requestDTO.paiements()
        );
        Contrat contrat = contratMapper.toEntity(contratRequest);
        contrat.setReservation(savedReservation);
        Contrat savedContrat = contratRepository.save(contrat);
        savedReservation.setContrat(savedContrat);

        return reservationMapper.toResponse(savedReservation);
    }
}
