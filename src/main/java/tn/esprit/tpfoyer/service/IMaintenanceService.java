package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.web.dto.MaintenanceRequest;
import tn.esprit.tpfoyer.web.dto.MaintenanceResponse;
import tn.esprit.tpfoyer.web.dto.VehiculeDTO;

public interface IMaintenanceService extends CrudService<MaintenanceRequest, MaintenanceResponse> {

    /**
     * Affecte une maintenance existante au véhicule identifié par son immatriculation
     * et renvoie le véhicule mis à jour.
     */
    VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation);
}
