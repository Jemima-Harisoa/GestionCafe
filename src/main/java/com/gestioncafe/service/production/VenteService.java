package com.gestioncafe.service.production;

import com.gestioncafe.model.Vente;
import com.gestioncafe.repository.VenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenteService {
    private final VenteRepository venteRepository;

    public VenteService(VenteRepository venteRepository) {
        this.venteRepository = venteRepository;
    }

    public List<Vente> findAll() {
        return venteRepository.findAll();
    }

    public Optional<Vente> findById(Integer id) {
        return venteRepository.findById(id);
    }

    public Vente save(Vente vente) {
        return venteRepository.save(vente);
    }

    public void deleteById(Integer id) {
        venteRepository.deleteById(id);
    }
}
