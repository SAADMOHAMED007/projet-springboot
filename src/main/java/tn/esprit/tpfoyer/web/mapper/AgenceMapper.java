package tn.esprit.tpfoyer.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.web.dto.AgenceRequest;
import tn.esprit.tpfoyer.web.dto.AgenceResponse;

@Component
public class AgenceMapper implements EntityMapper<Agence, AgenceRequest, AgenceResponse> {

    @Override
    public Agence toEntity(AgenceRequest request) {
        Agence agence = new Agence();
        agence.setNom(request.nom());
        agence.setVille(request.ville());
        agence.setAdresse(request.adresse());
        agence.setTelephone(request.telephone());
        return agence;
    }

    @Override
    public AgenceResponse toResponse(Agence agence) {
        return new AgenceResponse(
                agence.getIdAgence(),
                agence.getNom(),
                agence.getVille(),
                agence.getAdresse(),
                agence.getTelephone()
        );
    }
}
