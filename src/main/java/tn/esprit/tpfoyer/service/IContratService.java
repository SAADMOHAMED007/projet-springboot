package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.web.dto.ContratDTO;
import tn.esprit.tpfoyer.web.dto.ContratRequest;

import java.time.LocalDate;

public interface IContratService extends CrudService<ContratRequest, ContratDTO> {

    /**
     * Affecte le contrat signé à la date donnée à la réservation qui couvre la période donnée.
     */
    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
