package com.gestioncafe.service.rh;

import com.gestioncafe.model.Langue;
import com.gestioncafe.repository.LangueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LangueService {

    @Autowired
    private LangueRepository langueRepository;

    public List<Langue> getAllLangues() {
        return langueRepository.findAll();
    }

    public Langue getById(Long id) {
        return langueRepository.findById(id).orElse(null);
    }
}
