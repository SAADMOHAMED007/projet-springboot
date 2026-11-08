package tn.esprit.tpfoyer.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.domain.Employe;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IAgenceRepository;
import tn.esprit.tpfoyer.web.dto.EmployeRequest;
import tn.esprit.tpfoyer.web.dto.EmployeResponse;

@Component
@RequiredArgsConstructor
public class EmployeMapper implements EntityMapper<Employe, EmployeRequest, EmployeResponse> {

    private final IAgenceRepository agenceRepository;

    @Override
    public Employe toEntity(EmployeRequest request) {
        Employe employe = new Employe();
        employe.setNom(request.nom());
        employe.setPrenom(request.prenom());
        employe.setRole(request.role());
        employe.setAgence(resolveAgence(request.idAgence()));
        return employe;
    }

    @Override
    public EmployeResponse toResponse(Employe employe) {
        Agence agence = employe.getAgence();
        return new EmployeResponse(
                employe.getIdEmploye(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getRole(),
                agence != null ? agence.getIdAgence() : null,
                agence != null ? agence.getNom() : null
        );
    }

    private Agence resolveAgence(Long idAgence) {
        if (idAgence == null) {
            return null;
        }
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }
}
