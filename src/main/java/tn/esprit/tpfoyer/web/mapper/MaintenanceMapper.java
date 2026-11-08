package tn.esprit.tpfoyer.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Maintenance;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IVehiculeRepository;
import tn.esprit.tpfoyer.web.dto.MaintenanceRequest;
import tn.esprit.tpfoyer.web.dto.MaintenanceResponse;

@Component
@RequiredArgsConstructor
public class MaintenanceMapper implements EntityMapper<Maintenance, MaintenanceRequest, MaintenanceResponse> {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Maintenance toEntity(MaintenanceRequest request) {
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(request.dateDebut());
        maintenance.setDateFin(request.dateFin());
        maintenance.setDescription(request.description());
        maintenance.setCout(request.cout());
        maintenance.setVehicule(resolveVehicule(request.idVehicule()));
        return maintenance;
    }

    @Override
    public MaintenanceResponse toResponse(Maintenance maintenance) {
        Vehicule vehicule = maintenance.getVehicule();
        return new MaintenanceResponse(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                maintenance.getCout(),
                vehicule != null ? vehicule.getIdVehicule() : null,
                vehicule != null ? vehicule.getImmatriculation() : null
        );
    }

    private Vehicule resolveVehicule(Long idVehicule) {
        if (idVehicule == null) {
            return null;
        }
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", idVehicule));
    }
}
