package com.gestioncafe.service.production;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.Recette;
import com.gestioncafe.repository.RecetteRepository;

@Service
public class RecetteService {
    private final RecetteRepository recetteRepository;

    public RecetteService(RecetteRepository recetteRepository) {
        this.recetteRepository = recetteRepository;
    }

    public List<Recette> findAll() {
        return recetteRepository.findAll();
    }

    public Optional<Recette> findById(Integer id) {
        return recetteRepository.findById(id);
    }

    public Recette save(Recette recette) {
        return recetteRepository.save(recette);
    }

    public void deleteById(Integer id) {
        recetteRepository.deleteById(id);
    }

    public List<Recette> findByProduitId(Integer produitId) {
        return recetteRepository.findByProduitId(produitId);
    }

    public boolean existsByProduitId(Integer produitId) {
        return recetteRepository.existsByProduitId(produitId);
    }
}
