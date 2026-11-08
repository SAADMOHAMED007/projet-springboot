package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Contrat;
import tn.esprit.tpfoyer.repository.IContratRepository;
import tn.esprit.tpfoyer.service.IContratService;
import tn.esprit.tpfoyer.web.dto.ContratRequest;
import tn.esprit.tpfoyer.web.dto.ContratResponse;
import tn.esprit.tpfoyer.web.mapper.ContratMapper;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl extends AbstractCrudService<Contrat, ContratRequest, ContratResponse>
        implements IContratService {

    private final IContratRepository contratRepository;
    private final ContratMapper contratMapper;

    @Override
    protected JpaRepository<Contrat, Long> repository() {
        return contratRepository;
    }

    @Override
    protected ContratMapper mapper() {
        return contratMapper;
    }

    @Override
    protected String entityName() {
        return "Contrat";
    }

    @Override
    protected void validate(Contrat contrat) {
        if (contrat.getMontantTotal() != null && contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("Le montant total du contrat ne peut pas être négatif");
        }
    }

    @Override
    protected void copyMutableFields(Contrat existing, Contrat updates) {
        existing.setDateSignature(updates.getDateSignature());
        existing.setMontantTotal(updates.getMontantTotal());
        existing.setValide(updates.isValide());
        if (updates.getReservation() != null) {
            existing.setReservation(updates.getReservation());
        }
    }
}
