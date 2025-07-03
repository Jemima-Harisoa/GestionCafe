package com.gestioncafe.controller.rh;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.service.rh.RhParametreService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/administratif/rh/parametre")
public class RhParametreController {

    @Autowired
    private RhParametreService rhParametreService;

    @PostMapping("/ajout-jour-ferie")
    public String ajoutJourFerie(@ModelAttribute("jourFerie") JourFerie jourFerie) {
        rhParametreService.getJourFerieService().save(jourFerie);
        return "redirect:/administratif/rh/parametre";
    }

    @PostMapping("/ajout-grade")
    public String ajoutGrade(@ModelAttribute("grade") Grade grade) {
        rhParametreService.getGradeService().save(grade);
        return "redirect:/administratif/rh/parametre";
    }

    @PostMapping("/supprimer-irsa")
    @ResponseBody
    public ResponseEntity<?> supprimerIrsa(@RequestParam Long id) {
        try {
            rhParametreService.getIrsaService().deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression");
        }
    }

    @PostMapping("/ajout-irsa")
    public String enregistrerIrsas(@ModelAttribute("irsaWrapper") IrsaWrapper irsaWrapper,
            RedirectAttributes redirectAttributes) {
        try {
            List<Irsa> irsas = irsaWrapper.getIrsas();
            for (Irsa irsa : irsas) {
                rhParametreService.getIrsaService().save(irsa);
            }
            return "redirect:/administratif/rh/parametre";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
            return "redirect:/administratif/rh/parametre";
        }
    }


}
