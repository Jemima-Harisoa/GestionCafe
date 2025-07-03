package com.gestioncafe.service.production;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.DetailRecette;
import com.gestioncafe.repository.DetailRecetteRepository;

@Service
public class DetailRecetteService {
    private final DetailRecetteRepository detailRecetteRepository;

    public DetailRecetteService(DetailRecetteRepository detailRecetteRepository) {
        this.detailRecetteRepository = detailRecetteRepository;
    }

    public List<DetailRecette> findAll() {
        return detailRecetteRepository.findAll();
    }

    public Optional<DetailRecette> findById(Integer id) {
        return detailRecetteRepository.findById(id);
    }

    public DetailRecette save(DetailRecette detailRecette) {
        return detailRecetteRepository.save(detailRecette);
    }

    public void deleteById(Integer id) {
        detailRecetteRepository.deleteById(id);
    }

    public List<DetailRecette> findByRecetteId(Integer recetteId) {
        return detailRecetteRepository.findByRecetteId(recetteId);
    }
}
