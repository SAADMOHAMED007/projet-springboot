package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.service.IVehiculeService;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl extends AbstractCrudService<Vehicule> implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    protected JpaRepository<Vehicule, Long> repository() {
        return vehiculeRepository;
    }

    @Override
    protected Long getId(Vehicule vehicule) {
        return vehicule.getIdVehicule();
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
