package com.gestioncafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.HistoriqueEstimation;
import com.gestioncafe.model.MatierePremiere;

public interface HistoriqueEstimationRepository extends JpaRepository<HistoriqueEstimation, Integer> {
    List<HistoriqueEstimation> findByMatierePremiere(MatierePremiere matierePremiere);
}
