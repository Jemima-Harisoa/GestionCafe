package com.gestioncafe.service.tiers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.Fournisseur;
import com.gestioncafe.repository.tiers.FournisseurRepository;

@Service
public class FournisseurService {
    private final FournisseurRepository fournisseurRepository;

    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    public List<Fournisseur> findAll() {
        return fournisseurRepository.findAll();
    }

    public Optional<Fournisseur> findById(Integer id) {
        return fournisseurRepository.findById(id);
    }

    public Fournisseur save(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    public void deleteById(Integer id) {
        fournisseurRepository.deleteById(id);
    }
}
