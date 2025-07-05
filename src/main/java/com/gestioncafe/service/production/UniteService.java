package com.gestioncafe.service.production;

import com.gestioncafe.model.Unite;
import com.gestioncafe.repository.UniteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniteService {
    private final UniteRepository uniteRepository;

    public UniteService(UniteRepository uniteRepository) {
        this.uniteRepository = uniteRepository;
    }

    public List<Unite> findAll() {
        return uniteRepository.findAll();
    }

    public Optional<Unite> findById(Integer id) {
        return uniteRepository.findById(id);
    }

    public Unite save(Unite unite) {
        return uniteRepository.save(unite);
    }

    public void deleteById(Integer id) {
        uniteRepository.deleteById(id);
    }
}
