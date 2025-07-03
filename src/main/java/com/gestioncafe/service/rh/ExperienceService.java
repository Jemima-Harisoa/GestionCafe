package com.gestioncafe.service.rh;

import com.gestioncafe.model.Experience;
import com.gestioncafe.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public Experience getById(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }
}
