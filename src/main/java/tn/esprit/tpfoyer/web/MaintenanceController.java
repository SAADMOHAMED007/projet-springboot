package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.service.IMaintenanceService;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController extends AbstractCrudController<Maintenance> {

    public MaintenanceController(IMaintenanceService service) {
        super(service);
    }
}
