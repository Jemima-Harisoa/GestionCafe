package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestioncafe.model.PackageProduit;

public interface PackageProduitRepository extends JpaRepository<PackageProduit, Integer> {
}
