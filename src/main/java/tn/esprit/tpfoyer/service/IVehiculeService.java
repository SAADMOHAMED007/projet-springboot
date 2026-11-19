package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.web.dto.VehiculeDTO;
import tn.esprit.tpfoyer.web.dto.VehiculeRequest;

public interface IVehiculeService extends CrudService<VehiculeRequest, VehiculeDTO> {

    /**
     * Affecte un équipement existant à un véhicule existant.
     */
    VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);
}
