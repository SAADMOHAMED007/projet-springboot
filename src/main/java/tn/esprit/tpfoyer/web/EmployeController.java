package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Employe;
import tn.esprit.tpfoyer.service.EmployeService;

@RestController
@RequestMapping("/api/employes")
public class EmployeController extends AbstractCrudController<Employe> {

    public EmployeController(EmployeService service) {
        super(service);
    }

    @Override
    protected void setId(Employe employe, Long id) {
        employe.setIdEmploye(id);
    }
}
