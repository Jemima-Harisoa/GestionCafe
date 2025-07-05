package com.gestioncafe.service.rh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class JourFerieService {
    @Autowired
    private JourFerieRepository jourFerieRepository;

    public List<JourFerie> findAll() {
        return jourFerieRepository.findAll();
    }

    public JourFerie findById(Long id) {
        return jourFerieRepository.findById(id).orElseThrow();
    }

    public JourFerie save(JourFerie jourFerie) {
        return jourFerieRepository.save(jourFerie);
    }

    public void delete(JourFerie jourFerie) {
        jourFerieRepository.delete(jourFerie);
    }

    public void deleteById(Long id) {
        jourFerieRepository.deleteById(id);
    }
}
