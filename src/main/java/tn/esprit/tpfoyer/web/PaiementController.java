package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.service.PaiementService;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController extends AbstractCrudController<Paiement> {

    public PaiementController(PaiementService service) {
        super(service);
    }

    @Override
    protected void setId(Paiement paiement, Long id) {
        paiement.setIdPaiement(id);
    }
}
