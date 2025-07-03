package com.gestioncafe.repository;

import java.util.List;
import java.sql.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface AvanceRepository extends JpaRepository<Avance, Long> {
    public List<Avance> findByIdEmployeOrderByDateAvanceDesc(Long idEmploye);
    public List<Avance> findByIdEmployeAndDateAvanceAfter(Long idEmploye, Date dateRepere);
    @Query("SELECT a FROM Avance a WHERE a.dateAvance BETWEEN :start AND :end")
    public List<Avance> findAllBetweenDates(@Param("start") Date start, @Param("end") Date end);
}