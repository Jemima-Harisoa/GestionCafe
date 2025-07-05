package com.gestioncafe.repository.tiers;

import com.gestioncafe.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
