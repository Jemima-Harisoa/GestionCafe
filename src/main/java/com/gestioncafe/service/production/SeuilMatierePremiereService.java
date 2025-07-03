package com.gestioncafe.service.production;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.SeuilMatierePremiere;
import com.gestioncafe.repository.SeuilMatierePremiereRepository;

@Service
public class SeuilMatierePremiereService {
    private final SeuilMatierePremiereRepository repository;

    public SeuilMatierePremiereService(SeuilMatierePremiereRepository repository) {
        this.repository = repository;
    }

    public List<SeuilMatierePremiere> findByMatierePremiere(MatierePremiere mp) {
        return repository.findByMatierePremiere(mp);
    }
}
