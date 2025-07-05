package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.Marketing;

@Repository
public interface MarketingRepository extends JpaRepository<Marketing, Long> {
    // Tu peux ajouter ici des méthodes personnalisées si besoin, par exemple :
    // List<Marketing> findByAgeGreaterThan(int age);
}
