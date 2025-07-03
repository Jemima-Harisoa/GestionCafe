package com.gestioncafe.service.production;

import com.gestioncafe.model.PrixVenteProduit;
import com.gestioncafe.repository.PrixVenteProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrixVenteProduitService {
    private final PrixVenteProduitRepository prixVenteProduitRepository;

    public PrixVenteProduitService(PrixVenteProduitRepository prixVenteProduitRepository) {
        this.prixVenteProduitRepository = prixVenteProduitRepository;
    }

    public PrixVenteProduit save(PrixVenteProduit prixVenteProduit) {
        return prixVenteProduitRepository.save(prixVenteProduit);
    }

    public List<PrixVenteProduit> findByProduitId(Integer produitId) {
        return prixVenteProduitRepository.findByProduitId(produitId);
    }

    public PrixVenteProduit findLastByProduitId(Integer produitId) {
        List<PrixVenteProduit> list = prixVenteProduitRepository.findByProduitIdOrderByDateApplicationDesc(produitId);
        return list.isEmpty() ? null : list.get(0);
    }
}
