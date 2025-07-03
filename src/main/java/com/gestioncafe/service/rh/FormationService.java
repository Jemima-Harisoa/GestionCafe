package com.gestioncafe.service.rh;

import com.gestioncafe.model.Formation;
import com.gestioncafe.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationService {

    @Autowired
    private FormationRepository formationRepository;

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Formation getById(Long id) {
        return formationRepository.findById(id).orElse(null);
    }
}
