package tn.esprit.tpfoyer.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.domain.Agence;
import tn.esprit.tpfoyer.service.AgenceService;
import tn.esprit.tpfoyer.web.dto.AgenceRequest;
import tn.esprit.tpfoyer.web.dto.AgenceResponse;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
public class AgenceController {

    private final AgenceService agenceService;

    public AgenceController(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceResponse create(@RequestBody AgenceRequest request) {
        return toResponse(agenceService.save(toEntity(request)));
    }

    @GetMapping
    public List<AgenceResponse> findAll() {
        return agenceService.findAll().stream().map(AgenceController::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AgenceResponse findById(@PathVariable Long id) {
        return toResponse(agenceService.findById(id));
    }

    @PutMapping("/{id}")
    public AgenceResponse update(@PathVariable Long id, @RequestBody AgenceRequest request) {
        agenceService.findById(id);
        Agence agence = toEntity(request);
        agence.setIdAgence(id);
        return toResponse(agenceService.save(agence));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        agenceService.deleteById(id);
    }

    private static Agence toEntity(AgenceRequest request) {
        Agence agence = new Agence();
        agence.setNom(request.nom());
        agence.setVille(request.ville());
        agence.setAdresse(request.adresse());
        agence.setTelephone(request.telephone());
        return agence;
    }

    private static AgenceResponse toResponse(Agence agence) {
        return new AgenceResponse(
                agence.getIdAgence(),
                agence.getNom(),
                agence.getVille(),
                agence.getAdresse(),
                agence.getTelephone()
        );
    }
}
