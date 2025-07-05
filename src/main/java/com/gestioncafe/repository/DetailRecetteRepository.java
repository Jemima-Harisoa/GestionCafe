package com.gestioncafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.DetailRecette;

public interface DetailRecetteRepository extends JpaRepository<DetailRecette, Integer> {
    List<DetailRecette> findByRecetteId(Integer recetteId);
}
