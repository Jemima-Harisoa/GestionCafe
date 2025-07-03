package com.gestioncafe.repository;

import java.util.List;
import java.sql.*;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface GradeEmployeRepository extends JpaRepository<GradeEmploye, Long>{
    @Query("SELECT COALESCE(ge.grade.salaire, 0.0) FROM GradeEmploye ge WHERE ge.idEmploye = :idEmploye AND ge.dateGrade <= :date ORDER BY ge.dateGrade DESC")
    public double findSalaireByEmployeAndDate(Long idEmploye, Date date);
    // public List<GradeEmploye> findByEmployeOrderByDateGradeDesc(Employe employe);
    
}
