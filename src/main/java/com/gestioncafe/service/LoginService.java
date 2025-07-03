package com.gestioncafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class LoginService {
    
    @Autowired
    private AdministratifRepository administratif;
    @Autowired
    private QuotidienRepository quotidien;

    public boolean loginAdministratif(String nom, String motDePasse) {
        Administratif adminUtilisateur = administratif.findByNomAndMotDePasse(nom, motDePasse);
        if (adminUtilisateur == null) {
            return false; 
        }
        return true;
    }

    public boolean loginQuotidien(String nom, String motDePasse) {
        Quotidien quotidienUtilisateur = quotidien.findByNomAndMotDePasse(nom, motDePasse);
        if (quotidienUtilisateur == null) {
            return false; 
        }
        return true;
    }
}
