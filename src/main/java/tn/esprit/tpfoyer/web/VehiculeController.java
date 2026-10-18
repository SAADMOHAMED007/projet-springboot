package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.service.VehiculeService;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController extends AbstractCrudController<Vehicule> {

    public VehiculeController(VehiculeService service) {
        super(service);
    }

    @Override
    protected void setId(Vehicule vehicule, Long id) {
        vehicule.setIdVehicule(id);
    }
}
