package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IMaintenanceRepository;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.service.IMaintenanceService;
import tn.esprit.tpfoyer.web.dto.MaintenanceRequest;
import tn.esprit.tpfoyer.web.dto.MaintenanceResponse;
import tn.esprit.tpfoyer.web.dto.VehiculeDTO;
import tn.esprit.tpfoyer.web.mapper.MaintenanceMapper;
import tn.esprit.tpfoyer.web.mapper.VehiculeMapper;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl extends AbstractCrudService<Maintenance, MaintenanceRequest, MaintenanceResponse>
        implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final VehiculeMapper vehiculeMapper;

    @Override
    protected JpaRepository<Maintenance, Long> repository() {
        return maintenanceRepository;
    }

    @Override
    protected MaintenanceMapper mapper() {
        return maintenanceMapper;
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

    @Override
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        Maintenance maintenance = maintenanceRepository.findFirstByDescriptionIgnoreCase(description)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance introuvable avec la description '" + description + "'"));
        Vehicule vehicule = vehiculeRepository.findByImmatriculationIgnoreCase(immatriculation)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Véhicule introuvable avec l'immatriculation '" + immatriculation + "'"));

        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);
        vehicule.getMaintenances().add(maintenance);

        return vehiculeMapper.toResponse(vehicule);
    }
}
