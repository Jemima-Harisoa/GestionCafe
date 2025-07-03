package com.gestioncafe.controller.production;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gestioncafe.model.HistoriqueEstimation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestioncafe.dto.IngredientFormDTO;
import com.gestioncafe.dto.IngredientsFormWrapper;
import com.gestioncafe.model.DetailRecette;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.PrixVenteProduit;
import com.gestioncafe.model.Produit;
import com.gestioncafe.model.Recette;
import com.gestioncafe.model.Unite;
import com.gestioncafe.service.production.DetailRecetteService;
import com.gestioncafe.service.production.MatierePremiereService;
import com.gestioncafe.service.production.PackageProduitService;
import com.gestioncafe.service.production.PrixVenteProduitService;
import com.gestioncafe.service.production.ProduitService;
import com.gestioncafe.service.production.RecetteService;
import com.gestioncafe.service.production.UniteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    private final ProduitService produitService;
    private final UniteService uniteService;
    private final PackageProduitService packageProduitService;
    private final RecetteService recetteService;
    private final DetailRecetteService detailRecetteService;
    private final MatierePremiereService matierePremiereService;
    private final PrixVenteProduitService prixVenteProduitService;
    private final com.gestioncafe.service.production.HistoriqueEstimationService historiqueEstimationService;

    public ProduitController(ProduitService produitService, UniteService uniteService, PackageProduitService packageProduitService, RecetteService recetteService, DetailRecetteService detailRecetteService, MatierePremiereService matierePremiereService, PrixVenteProduitService prixVenteProduitService, com.gestioncafe.service.production.HistoriqueEstimationService historiqueEstimationService) {
        this.produitService = produitService;
        this.uniteService = uniteService;
        this.packageProduitService = packageProduitService;
        this.recetteService = recetteService;
        this.detailRecetteService = detailRecetteService;
        this.matierePremiereService = matierePremiereService;
        this.prixVenteProduitService = prixVenteProduitService;
        this.historiqueEstimationService = historiqueEstimationService;
    }

    @GetMapping
    public String listProduits(Model model) {
        var produits = produitService.findAll();
        Map<Integer, BigDecimal> prixVenteMap = new HashMap<>();
        for (Produit produit : produits) {
            PrixVenteProduit prix = prixVenteProduitService.findLastByProduitId(produit.getId());
            prixVenteMap.put(produit.getId(), prix != null ? prix.getPrixVente() : BigDecimal.ZERO);
        }
        model.addAttribute("produits", produits);
        model.addAttribute("prixVenteMap", prixVenteMap);
        return "administratif/production/produit/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("unites", uniteService.findAll());
        model.addAttribute("packages", packageProduitService.findAll());
        model.addAttribute("matieresPremieres", matierePremiereService.findAll());
        model.addAttribute("ingredientsWrapper", new IngredientsFormWrapper());

        return "administratif/production/produit/form";
    }

    @PostMapping("/save")
    public String saveProduit(
        @ModelAttribute Produit produit,
        @RequestParam String modePrix,
        @RequestParam(required = false) BigDecimal coefficient,
        @RequestParam(required = false) BigDecimal prixVenteManuel,
        @ModelAttribute("ingredientsWrapper") IngredientsFormWrapper ingredientsWrapper,
        @RequestParam BigDecimal quantiteProduiteRecette,
        @RequestParam BigDecimal tempsFabricationRecette,
        HttpServletRequest req
    ) {

        Produit savedProduit;
        if (produit.getId() != null) {
            // Edition : on met à jour l'existant
            Optional<Produit> oldOpt = produitService.findById(produit.getId());
            if (oldOpt.isPresent()) {
                Produit old = oldOpt.get();
                old.setNom(produit.getNom());
                old.setDescription(produit.getDescription());
                old.setUnite(produit.getUnite());
                old.setPackageProduit(produit.getPackageProduit());
                old.setStock(produit.getStock());
                old.setImage(produit.getImage());
                old.setDelaiPeremption(produit.getDelaiPeremption());
                savedProduit = produitService.save(old);
            } else {
                // Si jamais l'id n'existe pas, fallback création
                savedProduit = produitService.save(produit);
            }
        } else {
            // Création
            savedProduit = produitService.save(produit);
        }

        // 2. Création ou MAJ de la recette associée
        Recette recette;
        List<Recette> recettesExistantes = recetteService.findByProduitId(savedProduit.getId());
        if (recettesExistantes != null && !recettesExistantes.isEmpty()) {
            recette = recettesExistantes.get(0);
            recette.setQuantiteProduite(quantiteProduiteRecette);
            recette.setTempsFabrication(tempsFabricationRecette);
        } else {
            recette = new Recette();
            recette.setProduit(savedProduit);
            recette.setQuantiteProduite(quantiteProduiteRecette);
            recette.setTempsFabrication(tempsFabricationRecette);
        }
        Recette savedRecette = recetteService.save(recette);

        // 3. Suppression des anciens ingrédients (detail_recette) si édition
        List<DetailRecette> oldDetails = detailRecetteService.findByRecetteId(savedRecette.getId());
        for (DetailRecette old : oldDetails) {
            detailRecetteService.deleteById(old.getId());
        }

        // 4. Ajout des nouveaux ingrédients
        List<IngredientFormDTO> ingredientsList = (ingredientsWrapper != null && ingredientsWrapper.getIngredients() != null && !ingredientsWrapper.getIngredients().isEmpty())
            ? ingredientsWrapper.getIngredients()
            : com.gestioncafe.util.IngredientFormParser.parseFromRequest(req);

        for (IngredientFormDTO ing : ingredientsList) {
            try {
                if (ing.getIdMatierePremiere() != null && ing.getIdUnite() != null && ing.getQuantite() != null) {
                    MatierePremiere mp = matierePremiereService.findById(ing.getIdMatierePremiere()).orElse(null);
                    Unite unite = uniteService.findById(ing.getIdUnite()).orElse(null);
                    if (mp != null && unite != null) {
                        DetailRecette detail = new DetailRecette();
                        detail.setRecette(savedRecette);
                        detail.setMatierePremiere(mp);
                        detail.setUnite(unite);
                        detail.setQuantite(BigDecimal.valueOf(ing.getQuantite()));
                        detailRecetteService.save(detail);
                    }
                }
            } catch (Exception e) {
                // Log ou gestion d'erreur si besoin
            }
        }

        // 4. Calcul et insertion du prix de vente dans prix_vente_produit
        BigDecimal prixVente = produitService.calculerPrixVente(
            savedProduit.getId(), modePrix, coefficient, prixVenteManuel
        );
        PrixVenteProduit pvp = new PrixVenteProduit();
        pvp.setProduit(savedProduit);
        pvp.setPrixVente(prixVente);
        pvp.setDateApplication(java.time.LocalDateTime.now());
        prixVenteProduitService.save(pvp);

        return "redirect:/produits";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Produit> produitOpt = produitService.findById(id);
        if (produitOpt.isPresent()) {
            Produit produit = produitOpt.get();
            model.addAttribute("produit", produit);
            model.addAttribute("unites", uniteService.findAll());
            model.addAttribute("packages", packageProduitService.findAll());
            model.addAttribute("matieresPremieres", matierePremiereService.findAll());

            // Pré-remplissage de la recette et des ingrédients
            List<Recette> recettes = recetteService.findByProduitId(produit.getId());
            if (recettes != null && !recettes.isEmpty()) {
                Recette recette = recettes.get(0); // On prend la première recette trouvée
                model.addAttribute("quantiteProduiteRecette", recette.getQuantiteProduite());
                model.addAttribute("tempsFabricationRecette", recette.getTempsFabrication());
                // Préparer la liste des ingrédients
                java.util.List<DetailRecette> details = detailRecetteService.findByRecetteId(recette.getId());
                java.util.List<com.gestioncafe.dto.IngredientFormDTO> ingredientDTOs = new java.util.ArrayList<>();
                java.math.BigDecimal coutTotal = java.math.BigDecimal.ZERO;
                for (DetailRecette detail : details) {
                    com.gestioncafe.dto.IngredientFormDTO dto = new com.gestioncafe.dto.IngredientFormDTO();
                    dto.setIdMatierePremiere(detail.getMatierePremiere().getId());
                    dto.setQuantite(detail.getQuantite().doubleValue());
                    dto.setIdUnite(detail.getUnite().getId());
                    ingredientDTOs.add(dto);
                    // Calcul du coût à partir de l'historique d'estimation, conversion à la norme
                    java.util.List<HistoriqueEstimation> historiques = historiqueEstimationService.findByMatierePremiere(detail.getMatierePremiere());
                    HistoriqueEstimation estimationRecente = historiques.stream()
                        .max(java.util.Comparator.comparing(HistoriqueEstimation::getDateApplication))
                        .orElse(null);
                    if (estimationRecente != null && estimationRecente.getPrix() != null) {
                        // Conversion des quantités à la norme
                        BigDecimal quantiteRecette = detail.getQuantite();
                        BigDecimal valeurParNormeRecette = detail.getUnite().getValeurParNorme();
                        BigDecimal quantiteNorme = quantiteRecette.multiply(valeurParNormeRecette);

                        BigDecimal prixEstime = BigDecimal.valueOf(estimationRecente.getPrix());
                        BigDecimal valeurParNormeEstimation = estimationRecente.getUnite().getValeurParNorme();
                        // Prix par unité de norme
                        BigDecimal prixParNorme = prixEstime.divide(valeurParNormeEstimation, 6, java.math.RoundingMode.HALF_UP);

                        coutTotal = coutTotal.add(quantiteNorme.multiply(prixParNorme));
                    }
                }
                com.gestioncafe.dto.IngredientsFormWrapper wrapper = new com.gestioncafe.dto.IngredientsFormWrapper();
                wrapper.setIngredients(ingredientDTOs);
                model.addAttribute("ingredientsWrapper", wrapper);

                // Pré-remplissage du coefficient si possible
                PrixVenteProduit prixVente = prixVenteProduitService.findLastByProduitId(produit.getId());
                java.math.BigDecimal coefficient = null;
                if (prixVente != null && coutTotal != null && coutTotal.compareTo(java.math.BigDecimal.ZERO) > 0) {
                    coefficient = prixVente.getPrixVente().divide(coutTotal, 2, java.math.RoundingMode.HALF_UP);
                }
                model.addAttribute("coefficient", coefficient);
            } else {
                // Si pas de recette, valeurs par défaut
                model.addAttribute("quantiteProduiteRecette", null);
                model.addAttribute("tempsFabricationRecette", null);
                model.addAttribute("ingredientsWrapper", new com.gestioncafe.dto.IngredientsFormWrapper());
                model.addAttribute("coefficient", null);
            }

            return "administratif/production/produit/form";
        } else {
            return "redirect:/produits";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // Vérifie si le produit est utilisé ailleurs
        if (produitService.isProduitUtilise(id)) {
            redirectAttributes.addFlashAttribute("deleteError", "Impossible de supprimer ce produit car il est utilisé dans d'autres opérations (recettes, ventes, etc.).");
            return "redirect:/produits";
        }
        produitService.deleteById(id);
        return "redirect:/produits";
    }

    @GetMapping("/fiche/{id}")
    public String ficheTechnique(@PathVariable Integer id, Model model) {
        Optional<Produit> produitOpt = produitService.findById(id);
        if (produitOpt.isEmpty()) {
            return "redirect:/produits";
        }
        Produit produit = produitOpt.get();
        model.addAttribute("produit", produit);

        // Récupérer la recette principale
        List<Recette> recettes = recetteService.findByProduitId(id);
        if (recettes == null || recettes.isEmpty()) {
            model.addAttribute("details", java.util.Collections.emptyList());
        } else {
            Recette recette = recettes.get(0);
            List<DetailRecette> details = detailRecetteService.findByRecetteId(recette.getId());
            java.util.List<java.util.Map<String, Object>> detailsFiche = new java.util.ArrayList<>();
            for (DetailRecette detail : details) {
                java.util.Map<String, Object> map = new java.util.HashMap<>();
                map.put("matierePremiere", detail.getMatierePremiere());
                map.put("quantite", detail.getQuantite());
                map.put("unite", detail.getUnite());
                // Calcul du prix unitaire (converti à la norme) et unité d'estimation
                java.math.BigDecimal prixUnitaire = java.math.BigDecimal.ZERO;
                String uniteEstimation = "";
                java.util.List<HistoriqueEstimation> historiques = historiqueEstimationService.findByMatierePremiere(detail.getMatierePremiere());
                HistoriqueEstimation estimationRecente = historiques.stream()
                        .filter(h -> h.getPrix() != null)
                        .max(java.util.Comparator.comparing(HistoriqueEstimation::getDateApplication))
                        .orElse(null);
                if (estimationRecente != null && estimationRecente.getPrix() != null) {
                    java.math.BigDecimal prixEstime = java.math.BigDecimal.valueOf(estimationRecente.getPrix());
                    java.math.BigDecimal valeurParNormeEstimation = estimationRecente.getUnite() != null && estimationRecente.getUnite().getValeurParNorme() != null ? estimationRecente.getUnite().getValeurParNorme() : java.math.BigDecimal.ONE;
                    prixUnitaire = prixEstime.divide(valeurParNormeEstimation, 6, java.math.RoundingMode.HALF_UP);
                    uniteEstimation = estimationRecente.getUnite() != null ? estimationRecente.getUnite().getNom() : "";
                }
                map.put("prixUnitaire", prixUnitaire);
                map.put("uniteEstimation", uniteEstimation);
                // Conversion de la quantité à la norme (ex: g -> kg)
                java.math.BigDecimal quantiteNorme = detail.getQuantite();
                if (detail.getUnite() != null && detail.getUnite().getValeurParNorme() != null) {
                    quantiteNorme = quantiteNorme.multiply(detail.getUnite().getValeurParNorme());
                }
                map.put("quantiteNorme", quantiteNorme);
                // Prix total = prix unitaire * quantité convertie à la norme
                java.math.BigDecimal prixTotal = prixUnitaire.multiply(quantiteNorme);
                map.put("prixTotal", prixTotal);
                detailsFiche.add(map);
            }
            model.addAttribute("details", detailsFiche);
        }

        // Coût de fabrication
        BigDecimal coutFabrication = produitService.calculerCoutFabrication(id);
        model.addAttribute("coutFabrication", coutFabrication);

        // Prix de vente actuel
        PrixVenteProduit prixVente = prixVenteProduitService.findLastByProduitId(id);
        model.addAttribute("prixVente", prixVente != null ? prixVente.getPrixVente() : java.math.BigDecimal.ZERO);

        return "administratif/production/produit/fiche";
    }
}