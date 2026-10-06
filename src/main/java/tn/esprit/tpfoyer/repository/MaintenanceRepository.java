package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
