package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Contrat;

import java.time.LocalDate;
import java.util.Optional;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    Optional<Contrat> findFirstByDateSignature(LocalDate dateSignature);
}
