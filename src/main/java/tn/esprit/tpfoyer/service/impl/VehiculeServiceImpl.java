package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.service.IVehiculeService;
import tn.esprit.tpfoyer.web.dto.VehiculeRequest;
import tn.esprit.tpfoyer.web.dto.VehiculeResponse;
import tn.esprit.tpfoyer.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl extends AbstractCrudService<Vehicule, VehiculeRequest, VehiculeResponse>
        implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    protected JpaRepository<Vehicule, Long> repository() {
        return vehiculeRepository;
    }

    @Override
    protected VehiculeMapper mapper() {
        return vehiculeMapper;
    }

    @Override
    protected String entityName() {
        return "Véhicule";
    }

    @Override
    protected void validate(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException("Le tarif journalier est obligatoire");
        }
        if (vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }

    @Override
    protected void copyMutableFields(Vehicule existing, Vehicule updates) {
        existing.setImmatriculation(updates.getImmatriculation());
        existing.setMarque(updates.getMarque());
        existing.setModele(updates.getModele());
        existing.setCategorie(updates.getCategorie());
        existing.setTarifJournalier(updates.getTarifJournalier());
        existing.setStatut(updates.getStatut());
        existing.setAgence(updates.getAgence());
        existing.setEquipements(updates.getEquipements());
    }
}
