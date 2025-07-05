package com.gestioncafe.service.rh;

import com.gestioncafe.model.SerieBac;
import com.gestioncafe.repository.SerieBacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieBacService {

    @Autowired
    private SerieBacRepository serieBacRepository;

    public List<SerieBac> getAllSerieBacs() {
        return serieBacRepository.findAll();
    }

    public SerieBac getById(Long id) {
        return serieBacRepository.findById(id).orElse(null);
    }
}
