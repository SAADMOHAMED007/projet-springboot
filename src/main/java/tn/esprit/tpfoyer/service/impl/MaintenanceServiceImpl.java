package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.repository.IMaintenanceRepository;
import tn.esprit.tpfoyer.service.IMaintenanceService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl extends AbstractCrudService<Maintenance> implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    protected JpaRepository<Maintenance, Long> repository() {
        return maintenanceRepository;
    }

    @Override
    protected Long getId(Maintenance maintenance) {
        return maintenance.getIdMaintenance();
    }

    @Override
    protected String entityName() {
        return "Maintenance";
    }

    @Override
    protected void validate(Maintenance maintenance) {
        if (maintenance.getCout() != null && maintenance.getCout().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le coût de la maintenance ne peut pas être négatif");
        }
        if (maintenance.getDateDebut() != null && maintenance.getDateFin() != null
                && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être égale ou postérieure à la date de début");
        }
    }

    @Override
    protected void copyMutableFields(Maintenance existing, Maintenance updates) {
        existing.setDateDebut(updates.getDateDebut());
        existing.setDateFin(updates.getDateFin());
        existing.setDescription(updates.getDescription());
        existing.setCout(updates.getCout());
        existing.setVehicule(updates.getVehicule());
    }
}
