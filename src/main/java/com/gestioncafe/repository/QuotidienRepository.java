package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface QuotidienRepository extends JpaRepository<Quotidien, Long> {
    public Quotidien findByNomAndMotDePasse(String nom, String motDePasse);
}
