package com.gestioncafe.repository;

import java.util.List;
import java.util.Optional;
import java.sql.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestioncafe.model.*;

@Repository
public interface StatutEmployeRepository extends JpaRepository<StatutEmploye, Long> {
    @Query(value = """
        SELECT se.* FROM statut_employe se
        INNER JOIN (
            SELECT id_employe, MAX(date_statut) AS max_date
            FROM statut_employe
            GROUP BY id_employe
        ) last_status ON se.id_employe = last_status.id_employe AND se.date_statut = last_status.max_date
        WHERE se.id_statut = :idStatut
    """, nativeQuery = true)
    public List<StatutEmploye> findDerniersStatutsParEmployeEtStatut(@Param("idStatut") Long idStatut);
    public Optional<StatutEmploye> findTopByEmploye_IdOrderByDateStatutDesc(Long idEmploye);
    public Optional<StatutEmploye> findTopByEmploye_IdAndDateStatutLessThanEqualOrderByDateStatutDesc(Long idEmploye, Date dateStatut);

    List<StatutEmploye> findByEmployeOrderByDateStatutDesc(Employe employe);
    List<StatutEmploye> findByIdStatut(Long statutId);

}
