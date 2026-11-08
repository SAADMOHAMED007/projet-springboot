package tn.esprit.tpfoyer.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Client;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IClientRepository;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.web.dto.ReservationRequest;
import tn.esprit.tpfoyer.web.dto.ReservationResponse;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements EntityMapper<Reservation, ReservationRequest, ReservationResponse> {

    private final IVehiculeRepository vehiculeRepository;
    private final IClientRepository clientRepository;

    @Override
    public Reservation toEntity(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setStatut(request.statut());
        reservation.setVehicule(resolveVehicule(request.idVehicule()));
        reservation.setClient(resolveClient(request.idClient()));
        return reservation;
    }

    @Override
    public ReservationResponse toResponse(Reservation reservation) {
        Vehicule vehicule = reservation.getVehicule();
        Client client = reservation.getClient();
        return new ReservationResponse(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                vehicule != null ? vehicule.getIdVehicule() : null,
                vehicule != null ? vehicule.getImmatriculation() : null,
                client != null ? client.getIdClient() : null,
                client != null ? client.getNom() : null,
                client != null ? client.getPrenom() : null,
                reservation.getContrat() != null ? reservation.getContrat().getIdContrat() : null
        );
    }

    private Vehicule resolveVehicule(Long idVehicule) {
        if (idVehicule == null) {
            return null;
        }
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", idVehicule));
    }

    private Client resolveClient(Long idClient) {
        if (idClient == null) {
            return null;
        }
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
    }
}
