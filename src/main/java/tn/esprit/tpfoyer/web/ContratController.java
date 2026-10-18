package tn.esprit.tpfoyer.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.service.ContratService;

@RestController
@RequestMapping("/api/contrats")
public class ContratController extends AbstractCrudController<Contrat> {

    public ContratController(ContratService service) {
        super(service);
    }

    @Override
    protected void setId(Contrat contrat, Long id) {
        contrat.setIdContrat(id);
    }
}
