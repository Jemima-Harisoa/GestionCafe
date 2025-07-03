package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.CategorieUnite;

public interface CategorieUniteRepository extends JpaRepository<CategorieUnite, Integer> {
    // méthodes personnalisées si besoin
}
