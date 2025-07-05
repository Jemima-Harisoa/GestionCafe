package com.gestioncafe.service.production;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.PackageProduit;
import com.gestioncafe.repository.PackageProduitRepository;

@Service
public class PackageProduitService {
    private final PackageProduitRepository packageProduitRepository;

    public PackageProduitService(PackageProduitRepository packageProduitRepository) {
        this.packageProduitRepository = packageProduitRepository;
    }

    public List<PackageProduit> findAll() {
        return packageProduitRepository.findAll();
    }

    public Optional<PackageProduit> findById(Integer id) {
        return packageProduitRepository.findById(id);
    }

    public PackageProduit save(PackageProduit packageProduit) {
        return packageProduitRepository.save(packageProduit);
    }

    public void deleteById(Integer id) {
        packageProduitRepository.deleteById(id);
    }
}
