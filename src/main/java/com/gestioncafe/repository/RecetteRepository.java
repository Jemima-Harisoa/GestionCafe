package com.gestioncafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.Recette;

public interface RecetteRepository extends JpaRepository<Recette, Integer> {
    List<Recette> findByProduitId(Integer produitId);
    boolean existsByProduitId(Integer produitId);
}
