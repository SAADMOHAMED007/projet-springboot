package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
}
