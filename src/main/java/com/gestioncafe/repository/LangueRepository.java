package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.Langue;

@Repository
public interface LangueRepository extends JpaRepository<Langue, Long> {
}
