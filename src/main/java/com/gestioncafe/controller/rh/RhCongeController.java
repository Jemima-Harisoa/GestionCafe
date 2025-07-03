package com.gestioncafe.controller.rh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.gestioncafe.service.rh.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/administratif/rh/conge")
public class RhCongeController {

    @Autowired
    private RhCongeService rhCongeService;

    @PostMapping("/ajout-conge")
    public String ajoutConge(@RequestParam("typeConge") String typeConge, @RequestParam("dateDebut") String dateDebut, @RequestParam("dateFin") String dateFin, @RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur = "";
        try{
            Long idTypeConge = Long.parseLong(typeConge);
            java.sql.Date debut = java.sql.Date.valueOf(dateDebut);
            java.sql.Date fin = java.sql.Date.valueOf(dateFin);
            Long id = Long.parseLong(idEmploye);
            rhCongeService.ajoutConge(id, idTypeConge, debut, fin);
        } catch (NumberFormatException e) {
            erreur = "Erreur : les Id doivent etre des valeurs numeriques";
        } catch (IllegalArgumentException e) {
            erreur = "Erreur : date en format invalide";
        } catch (Exception e) {
            erreur = "Erreur dans l ajout conge : " + e.getMessage();
        }
        model.addAttribute("erreurAjoutConge", erreur);
        return "redirect:/administratif/rh/gestion-conges";
    }

     @GetMapping("/calendrier")
    public String calendrier(Model model) {
        model.addAttribute("jours", rhCongeService.jours());
        return "administratif/rh/calendrier";
    }

}
