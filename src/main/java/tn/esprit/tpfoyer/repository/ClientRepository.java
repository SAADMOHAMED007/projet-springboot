package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpfoyer.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
