package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.service.MaintenanceService;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController extends AbstractCrudController<Maintenance> {

    public MaintenanceController(MaintenanceService service) {
        super(service);
    }

    @Override
    protected void setId(Maintenance maintenance, Long id) {
        maintenance.setIdMaintenance(id);
    }
}
