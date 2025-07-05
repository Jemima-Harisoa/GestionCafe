package com.gestioncafe.repository;

import java.util.List;
import java.sql.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long> {
    public List<Commission> findByIdEmployeOrderByDateCommissionDesc(Long idEmploye);
    public List<Commission> findByIdEmployeAndDateCommissionBetween(Long idEmploye, Date dateDebut, Date dateFin);
    @Query("SELECT c FROM Commission c WHERE c.dateCommission BETWEEN :start AND :end")
    public List<Commission> findAllBetweenDates(@Param("start") Date start, @Param("end") Date end);
}
