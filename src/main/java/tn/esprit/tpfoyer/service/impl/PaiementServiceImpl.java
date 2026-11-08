package tn.esprit.tpfoyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer.domain.Paiement;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.repository.IPaiementRepository;
import tn.esprit.tpfoyer.service.IPaiementService;
import tn.esprit.tpfoyer.web.dto.PaiementResponse;
import tn.esprit.tpfoyer.web.mapper.PaiementMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementResponse findById(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        return paiementMapper.toResponse(paiement);
    }

    @Override
    public List<PaiementResponse> findAll() {
        return paiementRepository.findAll().stream()
                .map(paiementMapper::toResponse)
                .toList();
    }
}
