package com.gestioncafe.service.production;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.DetailRecette;
import com.gestioncafe.model.HistoriqueEstimation;
import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.model.Produit;
import com.gestioncafe.model.Recette;
import com.gestioncafe.model.Unite;
import com.gestioncafe.repository.DetailsVenteRepository;
import com.gestioncafe.repository.ProduitRepository;
import com.gestioncafe.repository.RecetteRepository;

@Service
public class ProduitService {
    private final ProduitRepository produitRepository;

    private final RecetteRepository recetteRepository;
    private final DetailsVenteRepository detailsVenteRepository;

    private final RecetteService recetteService;
    private final DetailRecetteService detailRecetteService;
    private final MatierePremiereService matierePremiereService;
    private final HistoriqueEstimationService historiqueEstimationService;

    public ProduitService(ProduitRepository produitRepository, RecetteRepository recetteRepository, DetailsVenteRepository detailsVenteRepository, RecetteService recetteService, DetailRecetteService detailRecetteService, MatierePremiereService matierePremiereService, HistoriqueEstimationService historiqueEstimationService) {
        this.produitRepository = produitRepository;
        this.recetteRepository = recetteRepository;
        this.detailsVenteRepository = detailsVenteRepository;
        this.recetteService = recetteService;
        this.detailRecetteService = detailRecetteService;
        this.matierePremiereService = matierePremiereService;
        this.historiqueEstimationService = historiqueEstimationService;
    }

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public Optional<Produit> findById(Integer id) {
        return produitRepository.findById(id);
    }

    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }

    /**
     * Calcule le prix de vente à partir du coût de fabrication et du coefficient multiplicateur.
     * Si le mode est "coefficient", on multiplie le coût par le coefficient.
     * Si le mode est "manuel", on prend le prix de vente manuel.
     *
     * @param idProduit       l'identifiant du produit
     * @param modePrix        "coefficient" ou "manuel"
     * @param coefficient     le coefficient multiplicateur (si mode coefficient)
     * @param prixVenteManuel le prix de vente manuel (si mode manuel)
     * @return le prix de vente à insérer dans la table prix_vente_produit
     */
    public BigDecimal calculerPrixVente(Integer idProduit, String modePrix, BigDecimal coefficient, BigDecimal prixVenteManuel) {
        if ("coefficient".equals(modePrix)) {
            BigDecimal cout = calculerCoutFabrication(idProduit);
            if (coefficient != null) {
                return cout.multiply(coefficient);
            } else {
                return cout;
            }
        } else if ("manuel".equals(modePrix) && prixVenteManuel != null) {
            return prixVenteManuel;
        }
        return BigDecimal.ZERO;
    }

    public void deleteById(Integer id) {
        produitRepository.deleteById(id);
    }

    // Calcul réel du coût de fabrication d'un produit à partir de la recette et de l'historique d'estimation
    public BigDecimal calculerCoutFabrication(Integer idProduit) {
        Optional<Produit> produitOpt = findById(idProduit);
        if (produitOpt.isEmpty()) return BigDecimal.ZERO;
        Produit produit = produitOpt.get();

        // Récupérer la recette du produit (on prend la première si plusieurs)
        List<Recette> recettes = recetteService.findByProduitId(idProduit);
        if (recettes == null || recettes.isEmpty()) return BigDecimal.ZERO;

        Recette recette = recettes.get(0);
        List<DetailRecette> details = detailRecetteService.findByRecetteId(recette.getId());
        BigDecimal cout = BigDecimal.ZERO;

        for (DetailRecette detail : details) {
            MatierePremiere mp = detail.getMatierePremiere();
            Unite uniteRecette = detail.getUnite();

            // Récupérer le prix unitaire le plus récent dans l'historique d'estimation
            List<HistoriqueEstimation> historiques = historiqueEstimationService.findByMatierePremiere(mp);
            HistoriqueEstimation estimationRecente = historiques.stream()
                .filter(h -> h.getPrix() != null)
                .max((h1, h2) -> {
                    if (h1.getDateApplication() == null) return -1;
                    if (h2.getDateApplication() == null) return 1;
                    return h1.getDateApplication().compareTo(h2.getDateApplication());
                })
                .orElse(null);

            if (estimationRecente != null && estimationRecente.getPrix() != null) {
                // Conversion de la quantité à la norme (ex: g -> kg)
                BigDecimal quantiteNorme = detail.getQuantite();
                if (uniteRecette != null && uniteRecette.getValeurParNorme() != null) {
                    quantiteNorme = quantiteNorme.multiply(uniteRecette.getValeurParNorme());
                }

                BigDecimal prixEstime = BigDecimal.valueOf(estimationRecente.getPrix());
                Unite uniteEstimation = estimationRecente.getUnite();
                BigDecimal valeurParNormeEstimation = uniteEstimation != null && uniteEstimation.getValeurParNorme() != null ? uniteEstimation.getValeurParNorme() : BigDecimal.ONE;
                // Prix par unité de norme
                BigDecimal prixParNorme = prixEstime.divide(valeurParNormeEstimation, 6, java.math.RoundingMode.HALF_UP);

                cout = cout.add(quantiteNorme.multiply(prixParNorme));
            }
        }
        return cout;
    }

    // Vérifie si le produit est utilisé dans d'autres tables (recette, vente, etc.)
    public boolean isProduitUtilise(Integer idProduit) {
        // Vérifie dans Recette
        boolean utiliseDansRecette = recetteRepository.existsByProduitId(idProduit);
        // Vérifie dans DetailsVente
        boolean utiliseDansVente = detailsVenteRepository.existsByProduitId(idProduit);
        // Ajoutez d'autres vérifications si besoin
        return utiliseDansRecette || utiliseDansVente;
    }
}
