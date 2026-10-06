package tn.esprit.tpfoyer.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationService;

@Service
public class ReservationServiceImpl extends AbstractCrudService<Reservation> implements ReservationService {

    public ReservationServiceImpl(ReservationRepository repository) {
        super(repository);
    }
}
