package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController extends AbstractCrudController<Reservation> {

    public ReservationController(IReservationService service) {
        super(service);
    }
}
