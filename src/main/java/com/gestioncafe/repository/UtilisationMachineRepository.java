package com.gestioncafe.repository;

import com.gestioncafe.model.UtilisationMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisationMachineRepository extends JpaRepository<UtilisationMachine, Integer> {
    List<UtilisationMachine> findByMachineId(Integer machineId);
}
