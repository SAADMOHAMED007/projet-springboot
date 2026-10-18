package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.service.IVehiculeService;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController extends AbstractCrudController<Vehicule> {

    public VehiculeController(IVehiculeService service) {
        super(service);
    }
}
