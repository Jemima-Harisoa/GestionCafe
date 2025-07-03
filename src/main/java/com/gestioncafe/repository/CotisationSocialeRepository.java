package com.gestioncafe.repository;

import java.sql.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestioncafe.model.*;

@Repository
public interface CotisationSocialeRepository  extends JpaRepository<CotisationSociale, Long>{
    
}
