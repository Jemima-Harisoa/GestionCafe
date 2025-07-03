package com.gestioncafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.Candidat;
import java.util.List;


@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    List<Candidat> findByGenreId(Long genreId);
}
