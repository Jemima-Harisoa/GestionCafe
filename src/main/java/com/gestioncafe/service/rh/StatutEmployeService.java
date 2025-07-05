package com.gestioncafe.service.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.StatutEmploye;
import com.gestioncafe.repository.StatutEmployeRepository;

@Service
public class StatutEmployeService {

    @Autowired
    private StatutEmployeRepository statutEmployeRepository;

    // Méthode pour sauvegarder un statut employé
    public StatutEmploye saveStatutEmploye(StatutEmploye statutEmploye) {
        return statutEmployeRepository.save(statutEmploye);
    }

    // Tu peux ajouter d'autres méthodes ici si besoin (findById, delete, etc.)
}
