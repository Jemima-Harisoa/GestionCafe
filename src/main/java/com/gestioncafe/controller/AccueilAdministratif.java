package com.gestioncafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/administratif")
public class AccueilAdministratif {

    @GetMapping
    public String accueil() {
        return "administratif/accueil-administratif";
    }
    
}
