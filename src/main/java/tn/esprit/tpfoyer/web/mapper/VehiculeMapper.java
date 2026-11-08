package tn.esprit.tpfoyer.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.domain.Equipement;
import tn.esprit.tpfoyer.domain.Vehicule;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IAgenceRepository;
import tn.esprit.tpfoyer.repository.IEquipementRepository;
import tn.esprit.tpfoyer.web.dto.EquipementResponse;
import tn.esprit.tpfoyer.web.dto.VehiculeRequest;
import tn.esprit.tpfoyer.web.dto.VehiculeResponse;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VehiculeMapper implements EntityMapper<Vehicule, VehiculeRequest, VehiculeResponse> {

    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public Vehicule toEntity(VehiculeRequest request) {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(request.immatriculation());
        vehicule.setMarque(request.marque());
        vehicule.setModele(request.modele());
        vehicule.setCategorie(request.categorie());
        vehicule.setTarifJournalier(request.tarifJournalier());
        vehicule.setStatut(request.statut());
        vehicule.setAgence(resolveAgence(request.idAgence()));
        vehicule.setEquipements(resolveEquipements(request.equipementIds()));
        return vehicule;
    }

    @Override
    public VehiculeResponse toResponse(Vehicule vehicule) {
        Agence agence = vehicule.getAgence();
        List<EquipementResponse> equipements = vehicule.getEquipements().stream()
                .map(equipementMapper::toResponse)
                .toList();
        return new VehiculeResponse(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                agence != null ? agence.getIdAgence() : null,
                agence != null ? agence.getNom() : null,
                equipements
        );
    }

    private Agence resolveAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }

    private Set<Equipement> resolveEquipements(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new LinkedHashSet<>();
        }
        Set<Long> distinctIds = new LinkedHashSet<>(ids);
        List<Equipement> equipements = equipementRepository.findAllById(distinctIds);
        if (equipements.size() != distinctIds.size()) {
            Set<Long> missingIds = new LinkedHashSet<>(distinctIds);
            equipements.forEach(equipement -> missingIds.remove(equipement.getIdEquipement()));
            throw new ResourceNotFoundException("Équipement", missingIds.iterator().next());
        }
        return new LinkedHashSet<>(equipements);
    }
}
