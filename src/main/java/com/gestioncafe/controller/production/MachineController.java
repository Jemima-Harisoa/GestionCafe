
package com.gestioncafe.controller.production;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestioncafe.model.Machine;
import com.gestioncafe.model.Produit;
import com.gestioncafe.model.Unite;
import com.gestioncafe.model.UtilisationMachine;
import com.gestioncafe.repository.UniteRepository;
import com.gestioncafe.repository.UtilisationMachineRepository;
import com.gestioncafe.service.production.MachineService;
import com.gestioncafe.service.production.ProduitService;

@Controller
@RequestMapping("/machines")
public class MachineController {
    private final MachineService machineService;
    private final ProduitService produitService;
    private final UniteRepository uniteRepository;
    private final UtilisationMachineRepository utilisationMachineRepository;

    public MachineController(MachineService machineService, ProduitService produitService, UniteRepository uniteRepository, UtilisationMachineRepository utilisationMachineRepository) {
        this.machineService = machineService;
        this.produitService = produitService;
        this.uniteRepository = uniteRepository;
        this.utilisationMachineRepository = utilisationMachineRepository;
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Machine machine = machineService.findById(id).orElseThrow();
        model.addAttribute("machine", machine);
        List<UtilisationMachine> utilisations = utilisationMachineRepository.findByMachineId(id);
        model.addAttribute("utilisations", utilisations);

        return "administratif/production/machine/details";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("machines", machineService.findAll());
        model.addAttribute("produits", produitService.findAll());

        return "administratif/production/machine/list";

    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("machine", new Machine());
        model.addAttribute("produits", produitService.findAll());
        model.addAttribute("unites", uniteRepository.findAll());

        return "administratif/production/machine/form";
    }

    @PostMapping("/save")
    public String save(
        @ModelAttribute Machine machine,
        @RequestParam(value = "utilisations.idProduit", required = false) List<Integer> idProduits,
        @RequestParam(value = "utilisations.duree", required = false) List<Double> durees,
        @RequestParam(value = "utilisations.idUnite", required = false) List<Integer> idUnites,
        @RequestParam(value = "utilisationsExistantesIds", required = false) List<Integer> utilisationsExistantesIds,
        @RequestParam(value = "utilisationsExistantes.idProduit", required = false) List<String> existantsNomProduits,
        @RequestParam(value = "utilisationsExistantes.duree", required = false) List<Double> existantsDurees,
        @RequestParam(value = "utilisationsExistantes.idUnite", required = false) List<Integer> existantsIdUnites,
        @RequestParam(value = "updateUtilisationId", required = false) Integer updateUtilisationId,
        @RequestParam(value = "deleteUtilisationId", required = false) Integer deleteUtilisationId
    ) {
        Machine savedMachine = machineService.save(machine);

        // Ajout d'utilisations (nouvelles)
        if (idProduits != null && durees != null && idUnites != null) {
            for (int i = 0; i < idProduits.size(); i++) {
                UtilisationMachine utilisation = new UtilisationMachine();
                utilisation.setMachine(savedMachine);
                Produit produit = new Produit();
                produit.setId(idProduits.get(i));
                utilisation.setProduit(produit);
                utilisation.setDuree(durees.get(i));
                Unite unite = new Unite();
                unite.setId(idUnites.get(i));
                utilisation.setUnite(unite);
                utilisationMachineRepository.save(utilisation);
            }
        }

        // Suppression d'une utilisation existante
        if (deleteUtilisationId != null) {
            utilisationMachineRepository.deleteById(deleteUtilisationId);
            return "redirect:/machines/edit/" + machine.getId();
        }

        // Mise à jour d'une utilisation existante
        if (updateUtilisationId != null && utilisationsExistantesIds != null && existantsNomProduits != null && existantsDurees != null && existantsIdUnites != null) {
            int idx = utilisationsExistantesIds.indexOf(updateUtilisationId);
            if (idx >= 0) {
                UtilisationMachine utilisation = utilisationMachineRepository.findById(updateUtilisationId).orElse(null);
                if (utilisation != null) {
                    // Trouver l'id du produit à partir du nom (car input text datalist)
                    Integer produitId = null;
                    String nomProduit = existantsNomProduits.get(idx);
                    // Recherche du produit par nom (à améliorer si plusieurs produits ont le même nom)
                    Produit produit = produitService.findAll().stream().filter(p -> p.getNom().equals(nomProduit)).findFirst().orElse(null);
                    if (produit != null) {
                        produitId = produit.getId();
                    }
                    if (produitId != null) {
                        Produit p = new Produit();
                        p.setId(produitId);
                        utilisation.setProduit(p);
                    }
                    utilisation.setDuree(existantsDurees.get(idx));
                    Unite unite = new Unite();
                    unite.setId(existantsIdUnites.get(idx));
                    utilisation.setUnite(unite);
                    utilisationMachineRepository.save(utilisation);
                }
            }
            return "redirect:/machines/edit/" + machine.getId();
        }

        return "redirect:/machines";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Machine machine = machineService.findById(id).orElseThrow();
        model.addAttribute("machine", machine);
        model.addAttribute("produits", produitService.findAll());
        model.addAttribute("unites", uniteRepository.findAll());
        // Charge les utilisations existantes de la machine
        List<UtilisationMachine> utilisations = utilisationMachineRepository.findByMachineId(id);
        model.addAttribute("utilisationsExistantes", utilisations);

        return "administratif/production/machine/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        machineService.deleteById(id);

        return "redirect:/machines";
    }
}
