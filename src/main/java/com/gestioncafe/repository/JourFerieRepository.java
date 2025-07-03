package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface JourFerieRepository extends JpaRepository<JourFerie, Long>  {
    
}
