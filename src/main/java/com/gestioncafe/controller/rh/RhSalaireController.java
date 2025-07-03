package com.gestioncafe.controller.rh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.gestioncafe.service.rh.*;

import jakarta.servlet.http.HttpServletResponse;

import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

@Controller
@RequestMapping("/administratif/rh/salaire")
public class RhSalaireController {
    
    @Autowired
    private RhSalaireService rhSalaireService;

    @GetMapping("/fiche-de-paie")
    public String ficheDePaie(@RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur="";
        try{
            Long id = Long.parseLong(idEmploye);
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    model.addAttribute("employe", employe);
                    model.addAttribute("ficheDePaies", rhSalaireService.getFicheDePaiesByEmployeId(id));
                    model.addAttribute("payements", rhSalaireService.getPayementsByEmployeId(id));
                    return "administratif/rh/fiche-de-paie";
                }
            } catch (RuntimeException e) {
                erreur = "Employé non trouvé avec l'ID: " + idEmploye;
            }
        } catch (NumberFormatException e) {
            erreur = "L'ID de l'employé doit être un nombre valide.";
        }
        model.addAttribute("erreurFicheDePaie", erreur);
        return "redirect:/administratif/rh/gestion-salaires";
    }

    @GetMapping("/avance")
    public String avance(@RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur="";
        try{
            Long id = Long.parseLong(idEmploye);
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    model.addAttribute("employe", employe);
                    model.addAttribute("prochainSalaire", rhSalaireService.prochainSalaire(id));
                    model.addAttribute("retenuPourAvance", rhSalaireService.retenuPourAvance(id));
                    model.addAttribute("raisonAvances", rhSalaireService.getAllRaisonAvances());
                    model.addAttribute("avances", rhSalaireService.getAvancesByEmployeId(id));
                    return "administratif/rh/avance";
                }
            } catch (RuntimeException e) {
                erreur = "Employé non trouvé avec l'ID: " + idEmploye;
            } 
        }catch (NumberFormatException e) {
            erreur = "L'ID de l'employé doit être un nombre valide.";
        }
        model.addAttribute("erreurAvance", erreur);
        return "redirect:/administratif/rh/gestion-salaires";
    }

    @GetMapping("/commission")
    public String commission(@RequestParam("idEmploye") String idEmploye, Model model) {
        String erreur="";
        try{
            Long id = Long.parseLong(idEmploye);
            try{
                Employe employe = rhSalaireService.getEmployeById(id);
                if(employe != null){
                    model.addAttribute("employe", employe);
                    model.addAttribute("raisonCommissions", rhSalaireService.getAllRaisonCommissions());
                    model.addAttribute("commissions", rhSalaireService.getCommissionsByEmployeId(id));
                    return "administratif/rh/commission";
                }
            } catch (RuntimeException e) {
                erreur = "Employé non trouvé avec l'ID: " + idEmploye;
            }
        } catch (NumberFormatException e) {
            erreur = "L'ID de l'employé doit être un nombre valide.";
        }
        model.addAttribute("erreurCommission", erreur);
        return "redirect:/administratif/rh/gestion-salaires";
    }

        @PostMapping("/payer-fiche-de-paie")
    public String payer(@RequestParam("idEmploye") String idEmploye, @RequestParam("moisReference") String moisReference, @RequestParam("salaireDeBase") String salaireDeBase, 
        @RequestParam("abscences") String abscences, @RequestParam("commissions") String commissions, @RequestParam("retenuesSociales") String retenuesSociales, 
        @RequestParam("impots") String impots, @RequestParam("salaireBrut") String salaireBrut, @RequestParam("salaireNetImposable") String salaireNetImposable,
        @RequestParam("salaireNet") String salaireNet, @RequestParam("retenueAvance") String retenueAvance, @RequestParam("netAPayer") String montant, Model model) {
        String erreur = "";
        try {
            Long id = Long.parseLong(idEmploye);
            java.sql.Date date = java.sql.Date.valueOf(moisReference);
            double base = Double.parseDouble(salaireDeBase);
            double abs = Double.parseDouble(abscences);
            double comm = Double.parseDouble(commissions);
            double retenueSoc = Double.parseDouble(retenuesSociales);
            double imp = Double.parseDouble(impots);
            double brut = Double.parseDouble(salaireBrut);
            double netImp = Double.parseDouble(salaireNetImposable);
            double net = Double.parseDouble(salaireNet);
            double avance = Double.parseDouble(retenueAvance);
            double netAPayer = Double.parseDouble(montant);

            rhSalaireService.ajoutPayement(
                id, base, abs, comm, retenueSoc, imp, brut, netImp, net, avance, netAPayer, date
            );

            model.addAttribute("succesPayemet", "Payement de " + montant + " pour l'employe");
            return "redirect:/administratif/rh/salaire/fiche-de-paie?idEmploye=" + idEmploye;

        } catch (NumberFormatException e) {
            erreur = "Erreur : une valeur numérique est invalide (" + e.getMessage() + ")";
        } catch (IllegalArgumentException e) {
            erreur = "Erreur : la date est invalide (" + moisReference + ")";
        } catch (Exception e) {
            erreur = "Erreur dans le payement : " + e.getMessage();
        }
        model.addAttribute("erreurPayement", erreur);
        return "/administratif/rh/salaire/fiche-de-paie(idEmploye=" + idEmploye + ")";
    }

    @GetMapping("/importer-fiche-de-paie")
    public void importerFicheDePaie(@RequestParam("idPayement") Long idPayement, HttpServletResponse response) {
        try {
            Payement payement = rhSalaireService.getPayementById(idPayement);
            if (payement == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Payement introuvable.");
                return;
            }
            String referencePayement = payement.getReferencePayement();
            File file = new File(referencePayement);
            if (!file.exists()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Fichier PDF non trouvé à : " + referencePayement);
                return;
            }
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentLengthLong(file.length());
            Files.copy(file.toPath(), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'envoi du PDF.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @PostMapping("/ajout-avance")
    public String ajoutAvance(@RequestParam("idEmploye") String idEmploye, @RequestParam("montant") String montant, @RequestParam("idRaison") String idRaison, Model model) {
        String erreur = "";
        try {
            double montantDouble = Double.parseDouble(montant);    
            try{
                Long raison = Long.parseLong(idRaison);
                Long id = Long.parseLong(idEmploye); 
                try{
                    rhSalaireService.ajoutAvance(id, raison, montantDouble);
                    model.addAttribute("succesAjoutAvance", "Avance de "+montant+" accorde a l'employe");
                    return "redirect:/administratif/rh/salaire/avance?idEmploye="+idEmploye;
                } catch(Exception e) {
                    erreur = "Erreur dans l'ajout : "+ e.getMessage();
                }
            } catch(NumberFormatException e) {
                erreur = "Erreur dans l'ajout : L'employe et la raison doivent être valide.";
            }
        } catch(NumberFormatException e) {
            erreur = "Erreur dans l'ajout :  Le montant doit être un nombre valide.";
        }
        model.addAttribute("erreurAjoutAvance", erreur);
        return "redirect:/administratif/rh/salaire/avance?idEmploye="+idEmploye;
    }

    @PostMapping("/ajout-commission")
    public String ajoutCommission(@RequestParam("idEmploye") String idEmploye, @RequestParam("montant") String montant, @RequestParam("idRaison") String idRaison, Model model) {
        String erreur = "";
        try {
            double montantDouble = Double.parseDouble(montant);    
            try{
                Long raison = Long.parseLong(idRaison);
                Long id = Long.parseLong(idEmploye); 
                try{
                    rhSalaireService.ajoutCommission(id, raison, montantDouble);
                    model.addAttribute("succesAjoutCommission", "Commission de "+montant+" descerne a l'employe");
                    return "redirect:/administratif/rh/salaire/commission?idEmploye="+idEmploye;
                } catch(Exception e) {
                    erreur = "Erreur dans l'ajout : "+ e.getMessage();
                }
            } catch(NumberFormatException e) {
                erreur = "Erreur dans l'ajout : L'employe et la raison doivent être valide.";
            }
        } catch(NumberFormatException e) {
            erreur = "Erreur dans l'ajout :  Le montant doit être un nombre valide.";
        }
        model.addAttribute("erreurAjoutCommission", erreur);
        return "redirect:/administratif/rh/salaire/commission?idEmploye="+idEmploye;
    }

}

