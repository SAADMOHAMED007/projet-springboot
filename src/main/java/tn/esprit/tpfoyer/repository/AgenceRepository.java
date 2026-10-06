package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Agence;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}
