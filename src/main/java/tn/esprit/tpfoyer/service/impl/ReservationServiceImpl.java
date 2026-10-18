package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.repository.IReservationRepository;
import tn.esprit.tpfoyer.service.IReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl extends AbstractCrudService<Reservation> implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    protected JpaRepository<Reservation, Long> repository() {
        return reservationRepository;
    }

    @Override
    protected Long getId(Reservation reservation) {
        return reservation.getIdReservation();
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
}
