package com.gestioncafe.service.rh;

import com.gestioncafe.model.DetailCandidat;
import com.gestioncafe.repository.DetailCandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailCandidatService {

    @Autowired
    private DetailCandidatRepository detailCandidatRepository;

    public List<DetailCandidat> getDetailsByCandidatId(Long candidatId) {
        return detailCandidatRepository.findByCandidatId(candidatId);
    }
}
