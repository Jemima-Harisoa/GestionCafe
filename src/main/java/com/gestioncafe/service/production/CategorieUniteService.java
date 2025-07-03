package com.gestioncafe.service.production;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.CategorieUnite;
import com.gestioncafe.repository.CategorieUniteRepository;

@Service
public class CategorieUniteService {
    @Autowired
    private CategorieUniteRepository categorieUniteRepository;

    public List<CategorieUnite> findAll() {
        return categorieUniteRepository.findAll();
    }
}
