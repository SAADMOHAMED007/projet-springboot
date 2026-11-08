package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.domain.Employe;
import tn.esprit.tpfoyer.repository.IEmployeRepository;
import tn.esprit.tpfoyer.service.IEmployeService;
import tn.esprit.tpfoyer.web.dto.EmployeRequest;
import tn.esprit.tpfoyer.web.dto.EmployeResponse;
import tn.esprit.tpfoyer.web.mapper.EmployeMapper;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl extends AbstractCrudService<Employe, EmployeRequest, EmployeResponse>
        implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final EmployeMapper employeMapper;

    @Override
    protected JpaRepository<Employe, Long> repository() {
        return employeRepository;
    }

    @Override
    protected EmployeMapper mapper() {
        return employeMapper;
    }

    @Override
    protected String entityName() {
        return "Employé";
    }

    @Override
    protected void validate(Employe employe) {
        if (employe.getNom() == null || employe.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'employé est obligatoire");
        }
    }

    @Override
    protected void copyMutableFields(Employe existing, Employe updates) {
        existing.setNom(updates.getNom());
        existing.setPrenom(updates.getPrenom());
        existing.setRole(updates.getRole());
        existing.setAgence(updates.getAgence());
    }
}
