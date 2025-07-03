package com.gestioncafe.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface PayementRepository extends JpaRepository<Payement, Long>{
    public List<Payement> findByIdEmployeOrderByMoisReferenceDesc(Long idEmploye);
    @Query("SELECT p FROM Payement p WHERE p.idEmploye = :idEmploye ORDER BY p.moisReference DESC")
    public Payement findLatestPayementByEmployeId(Long idEmploye);
    @Query("SELECT p FROM Payement p WHERE p.moisReference IN :moisRefs")
    public List<Payement> findAllByMoisReferenceIn(@Param("moisRefs") List<Date> moisRefs);

}
