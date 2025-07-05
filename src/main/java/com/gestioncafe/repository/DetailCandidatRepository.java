package com.gestioncafe.repository;

import com.gestioncafe.model.DetailCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailCandidatRepository extends JpaRepository<DetailCandidat, Long> {

    List<DetailCandidat> findByCandidatId(Long candidatId);

}
