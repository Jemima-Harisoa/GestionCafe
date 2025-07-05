package com.gestioncafe.repository;

import com.gestioncafe.model.PrixVenteProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrixVenteProduitRepository extends JpaRepository<PrixVenteProduit, Integer> {
    List<PrixVenteProduit> findByProduitId(Integer produitId);
    List<PrixVenteProduit> findByProduitIdOrderByDateApplicationDesc(Integer produitId);
}
