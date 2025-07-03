package com.gestioncafe.repository;

import com.gestioncafe.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Integer> {}
