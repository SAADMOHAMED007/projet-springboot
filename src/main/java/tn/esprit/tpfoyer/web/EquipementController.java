package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Equipement;
import tn.esprit.tpfoyer.service.EquipementService;

@RestController
@RequestMapping("/api/equipements")
public class EquipementController extends AbstractCrudController<Equipement> {

    public EquipementController(EquipementService service) {
        super(service);
    }

    @Override
    protected void setId(Equipement equipement, Long id) {
        equipement.setIdEquipement(id);
    }
}
