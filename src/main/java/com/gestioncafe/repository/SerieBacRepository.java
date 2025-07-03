package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.SerieBac;

@Repository
public interface SerieBacRepository extends JpaRepository<SerieBac, Long> {
}
