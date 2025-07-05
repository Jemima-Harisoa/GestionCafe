package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface TypeCongeRepository extends JpaRepository<TypeConge, Long> {
    @Query("SELECT SUM(t.nbJour) FROM TypeConge t WHERE t.obligatoire = true")
    public Integer getNbJourTotalObligatoire();

}
