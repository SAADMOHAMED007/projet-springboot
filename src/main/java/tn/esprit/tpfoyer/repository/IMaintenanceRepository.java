package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Maintenance;

import java.util.Optional;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Optional<Maintenance> findFirstByDescriptionIgnoreCase(String description);
}
