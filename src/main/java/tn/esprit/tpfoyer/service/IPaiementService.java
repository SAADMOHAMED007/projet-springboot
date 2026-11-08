package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.web.dto.PaiementResponse;

import java.util.List;

public interface IPaiementService {

    PaiementResponse findById(Long id);

    List<PaiementResponse> findAll();
}
