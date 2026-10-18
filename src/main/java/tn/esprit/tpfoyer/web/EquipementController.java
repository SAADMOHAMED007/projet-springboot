package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Equipement;
import tn.esprit.tpfoyer.service.IEquipementService;

@RestController
@RequestMapping("/api/equipements")
public class EquipementController extends AbstractCrudController<Equipement> {

    public EquipementController(IEquipementService service) {
        super(service);
    }
}
