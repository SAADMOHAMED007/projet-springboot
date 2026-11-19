package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.web.dto.ReservationRequest;
import tn.esprit.tpfoyer.web.dto.ReservationResponseDTO;
import tn.esprit.tpfoyer.web.dto.ReservationWithContratRequestDTO;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponseDTO> {

    /**
     * Affecte une réservation existante au client identifié par son nom et son prénom.
     */
    ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom);

    /**
     * Crée une réservation et le contrat associé en une seule opération.
     */
    ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
