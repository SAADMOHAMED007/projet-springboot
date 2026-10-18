package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.repository.IAgenceRepository;
import tn.esprit.tpfoyer.service.IAgenceService;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl extends AbstractCrudService<Agence> implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    protected JpaRepository<Agence, Long> repository() {
        return agenceRepository;
    }

    @Override
    protected Long getId(Agence agence) {
        return agence.getIdAgence();
    }

    @Override
    protected String entityName() {
        return "Agence";
    }

    @Override
    protected void validate(Agence agence) {
        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
    }

    @Override
    protected void copyMutableFields(Agence existing, Agence updates) {
        existing.setNom(updates.getNom());
        existing.setVille(updates.getVille());
        existing.setAdresse(updates.getAdresse());
        existing.setTelephone(updates.getTelephone());
    }
}
