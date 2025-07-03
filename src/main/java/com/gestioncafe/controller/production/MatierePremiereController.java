package com.gestioncafe.controller.production;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gestioncafe.model.DetailFournisseur;
import com.gestioncafe.model.HistoriqueEstimation;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.SeuilMatierePremiere;
import com.gestioncafe.repository.CategorieUniteRepository;
import com.gestioncafe.repository.UniteRepository;
import com.gestioncafe.service.production.DetailFournisseurService;
import com.gestioncafe.service.production.HistoriqueEstimationService;
import com.gestioncafe.service.production.MatierePremiereService;
import com.gestioncafe.service.production.SeuilMatierePremiereService;
import com.gestioncafe.service.tiers.FournisseurService;

@Controller
@RequestMapping("/administratif/production/matiere-premiere")
public class MatierePremiereController {

    private final MatierePremiereService service;
    private final UniteRepository uniteRepository;
    private final CategorieUniteRepository categorieUniteRepository;
    private final DetailFournisseurService detailFournisseurService;
    private final SeuilMatierePremiereService seuilMatierePremiereService;
    private final HistoriqueEstimationService historiqueEstimationService;
    private final FournisseurService fournisseurService;

    public MatierePremiereController(
            MatierePremiereService service,
            UniteRepository uniteRepository,
            CategorieUniteRepository categorieUniteRepository,
            DetailFournisseurService detailFournisseurService,
            SeuilMatierePremiereService seuilMatierePremiereService,
            HistoriqueEstimationService historiqueEstimationService,
            FournisseurService fournisseurService
    ) {
        this.service = service;
        this.uniteRepository = uniteRepository;
        this.categorieUniteRepository = categorieUniteRepository;
        this.detailFournisseurService = detailFournisseurService;
        this.seuilMatierePremiereService = seuilMatierePremiereService;
        this.historiqueEstimationService = historiqueEstimationService;
        this.fournisseurService = fournisseurService;
    }

    // Liste des matières premières
    @GetMapping
    // S'assurer que la catégorie d'unité est bien transmise à la vue si besoin
    public String list(Model model) {
        model.addAttribute("matieres", service.findAll());
        return "administratif/production/matiere-premiere/list";
    }

    // Formulaire d'ajout
    @GetMapping("/ajouter")
    public String ajouterForm(Model model) {
        model.addAttribute("matierePremiere", new MatierePremiere());
        model.addAttribute("unites", uniteRepository.findAll());
        model.addAttribute("categoriesUnite", categorieUniteRepository.findAll());
        return "administratif/production/matiere-premiere/form";
    }

    // Traitement de l'ajout
    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute MatierePremiere matierePremiere,
                          @RequestParam("imageFile") MultipartFile imageFile,
                          Model model) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            imageFile.transferTo(dest);
            matierePremiere.setImage("/" + uploadDir + fileName);
        }
        service.save(matierePremiere);

        return "redirect:/administratif/production/matiere-premiere";
    }

    // Formulaire de modification
    @GetMapping("/modifier/{id}")
    public String modifierForm(@PathVariable Integer id, Model model) {
        model.addAttribute("matierePremiere", service.findById(id).orElseThrow());
        model.addAttribute("unites", uniteRepository.findAll());
        model.addAttribute("categoriesUnite", categorieUniteRepository.findAll());
        return "administratif/production/matiere-premiere/form";
    }

    // Traitement de la modification
    @PostMapping("/modifier/{id}")
    public String modifier(@PathVariable Integer id,
                           @ModelAttribute MatierePremiere matierePremiere,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           Model model) throws IOException {
        // Charger l'entité existante depuis la base
        MatierePremiere existant = service.findById(id).orElseThrow();

        // Mettre à jour les champs simples
        existant.setNom(matierePremiere.getNom());
        existant.setUnite(matierePremiere.getUnite());
        // Ajoute ici les autres champs simples à mettre à jour si besoin

        // Gérer l'image
        if (imageFile != null && !imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(uploadDir + fileName);
            imageFile.transferTo(dest);
            existant.setImage("/" + uploadDir + fileName);
        }
        // Sinon, garder l'image existante (déjà présente sur l'entité)

        // Ne pas toucher aux collections (detailsFournisseur, etc.) ici

        service.save(existant);
        return "redirect:/administratif/production/matiere-premiere";
    }

    // Suppression
    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/administratif/production/matiere-premiere";
    }

    // Détails
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        MatierePremiere mp = service.findById(id).orElseThrow();
        List<DetailFournisseur> prixFournisseurs = detailFournisseurService.findByMatierePremiere(mp);
        List<SeuilMatierePremiere> seuils = seuilMatierePremiereService.findByMatierePremiere(mp);
        List<HistoriqueEstimation> historiques = historiqueEstimationService.findByMatierePremiere(mp);

        model.addAttribute("matierePremiere", mp);
        model.addAttribute("prixFournisseurs", prixFournisseurs);
        model.addAttribute("seuils", seuils);
        model.addAttribute("historiques", historiques);
        model.addAttribute("fournisseurs", fournisseurService.findAll());
        return "administratif/production/matiere-premiere/details";
    }
}
