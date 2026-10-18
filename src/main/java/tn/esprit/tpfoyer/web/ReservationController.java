package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController extends AbstractCrudController<Reservation> {

    public ReservationController(ReservationService service) {
        super(service);
    }

    @Override
    protected void setId(Reservation reservation, Long id) {
        reservation.setIdReservation(id);
    }
}
