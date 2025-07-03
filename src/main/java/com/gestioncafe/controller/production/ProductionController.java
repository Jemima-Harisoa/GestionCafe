package com.gestioncafe.controller.production;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gestioncafe.dto.VentePeriodeStatDTO;
import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.service.production.DetailsVenteService;

@Controller
@RequestMapping("/administratif/production")
public class ProductionController {
    private final DetailsVenteService detailsVenteService;

    public ProductionController(DetailsVenteService detailsVenteService) {
        this.detailsVenteService = detailsVenteService;
    }

    @GetMapping
    public String index() {
        return "administratif/production/stats";
    }

    @GetMapping("/produit")
    public String produit() {
        return "administratif/production/produit";
    }

    @GetMapping("/machine")
    public String machine() {
        return "redirect:/machines";
    }

    @GetMapping("/mat-premiere")
    public String metPremiere() {
        return "administratif/production/mat-premiere";
    }

    @GetMapping("/stats")
    public String statistiques(
        @RequestParam(value = "periodeCourbe", defaultValue = "jour") String periodeCourbe,
        Model model) {
        System.out.println(">>> Appel de la méthode statistiques avec periodeCourbe = " + periodeCourbe);
        List<VenteProduitStatDTO> stats = detailsVenteService.getVenteStatParProduit();
        List<VentePeriodeStatDTO> courbeTotal = detailsVenteService.getTotalProduitVenduParPeriode(periodeCourbe);

        // Ajout : calcul du max quantiteTotale pour stats
        int maxQuantiteTotale = stats.stream()
            .map(VenteProduitStatDTO::getQuantiteTotale)
            .filter(Objects::nonNull)
            .max(Comparator.naturalOrder())
            .orElse(BigDecimal.ONE) // éviter division par zéro
            .intValue();

        model.addAttribute("stats", stats != null ? stats : new ArrayList<>());
        model.addAttribute("courbeTotal", courbeTotal);
        model.addAttribute("periodeCourbe", periodeCourbe);
        model.addAttribute("maxQuantiteTotale", maxQuantiteTotale);

        courbeTotal.sort(Comparator.comparing(VentePeriodeStatDTO::getPeriode));

        List<String> periodes = new ArrayList<>();
        DateTimeFormatter formatter;
        LocalDate today = LocalDate.now();

        if ("jour".equals(periodeCourbe)) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = 59; i >= 0; i--) {
                periodes.add(today.minusDays(i).format(formatter));
            }
        } else if ("mois".equals(periodeCourbe)) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth thisMonth = YearMonth.now();
            for (int i = 11; i >= 0; i--) {
                periodes.add(thisMonth.minusMonths(i).format(formatter));
            }
        } else if ("annee".equals(periodeCourbe)) {
            formatter = DateTimeFormatter.ofPattern("yyyy");
            int thisYear = today.getYear();
            for (int i = 2; i >= 0; i--) {
                periodes.add(String.valueOf(thisYear - i));
            }
        } else {
            courbeTotal.sort(Comparator.comparing(VentePeriodeStatDTO::getPeriode));
            periodes = courbeTotal.stream().map(VentePeriodeStatDTO::getPeriode).toList();
        }

        Map<String, BigDecimal> map = new HashMap<>();
        for (VentePeriodeStatDTO v : courbeTotal) {
            map.put(v.getPeriode(), v.getQuantiteTotale());
        }

        List<String> labels = new ArrayList<>();
        List<BigDecimal> data = new ArrayList<>();
        for (String p : periodes) {
            labels.add(p);
            data.add(map.getOrDefault(p, BigDecimal.ZERO));
        }

        // Calcul du nombre moyen de produits vendus sur la période sélectionnée
        BigDecimal somme = BigDecimal.ZERO;
        int nbPeriodes = 0;
        for (BigDecimal val : data) {
            somme = somme.add(val);
            nbPeriodes++;
        }
        BigDecimal totalVenduPeriode = somme; // Ajout : total vendu pour la période sélectionnée
        BigDecimal moyenne = nbPeriodes > 0 ? somme.divide(BigDecimal.valueOf(nbPeriodes), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        // Calcul du bénéfice moyen par période (jour/mois/année) sur la période sélectionnée
        List<BigDecimal> beneficesParPeriode = detailsVenteService.getBeneficeMoyenParPeriodeList(periodeCourbe, periodes);
        BigDecimal sommeBenef = BigDecimal.ZERO;
        for (BigDecimal b : beneficesParPeriode) {
            sommeBenef = sommeBenef.add(b);
        }
        BigDecimal beneficeMoyen = nbPeriodes > 0 ? sommeBenef.divide(BigDecimal.valueOf(nbPeriodes), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        model.addAttribute("labels", labels);
        model.addAttribute("data", data);
        model.addAttribute("moyenneProduits", moyenne);
        model.addAttribute("beneficeMoyen", beneficeMoyen);
        model.addAttribute("totalVenduPeriode", totalVenduPeriode); // Ajout

        System.out.println("periodeCourbe=" + periodeCourbe + ", periodes=" + periodes);
        System.out.println("data=" + data);
        System.out.println("beneficesParPeriode=" + beneficesParPeriode);

        return "administratif/production/stats";
    }

    @GetMapping("/courbe-produit/{produitId}")
    public String courbeProduit(@PathVariable Integer produitId, @RequestParam(defaultValue = "jour") String periode, Model model) {
        List<VentePeriodeStatDTO> courbe = detailsVenteService.getVenteStatParPeriodeEtProduit(periode, produitId);
        model.addAttribute("courbe", courbe);
        model.addAttribute("periode", periode);
        model.addAttribute("produitId", produitId);

        return "administratif/production/courbe-produit";
    }

    @GetMapping("/stats-filtre")
    public String statistiquesFiltre(
        @RequestParam(required = false) LocalDate dateExacte,
        @RequestParam(required = false) Integer annee,
        @RequestParam(required = false) Integer mois,
        @RequestParam(required = false) Integer jourMois,
        @RequestParam(required = false) Integer jourSemaine,
        @RequestParam(required = false) LocalDateTime dateDebut,
        @RequestParam(required = false) LocalDateTime dateFin,
        Model model
    ) {
        List<VenteProduitStatDTO> stats = detailsVenteService.getVenteStatParProduitFiltre(
            dateExacte, annee, mois, jourMois, jourSemaine, dateDebut, dateFin
        );
        model.addAttribute("stats", stats != null ? stats : new ArrayList<>());
        model.addAttribute("annee", annee);
        model.addAttribute("mois", mois);
        model.addAttribute("jourMois", jourMois);
        model.addAttribute("jourSemaine", jourSemaine);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);

        return "administratif/production/stats";
    }

    @GetMapping(value = "/stats-json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> statistiquesJson(
        @RequestParam(value = "periodeCourbe", defaultValue = "jour") String periodeCourbe
    ) {
        System.out.println(">>> Appel de la méthode statistiquesJson avec periodeCourbe = " + periodeCourbe);
        List<VentePeriodeStatDTO> courbeTotal = detailsVenteService.getTotalProduitVenduParPeriode(periodeCourbe);
        courbeTotal.sort(Comparator.comparing(VentePeriodeStatDTO::getPeriode));
        List<String> periodes = new ArrayList<>();
        DateTimeFormatter formatter;
        LocalDate today = LocalDate.now();
        if ("jour".equals(periodeCourbe)) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = 59; i >= 0; i--) {
                periodes.add(today.minusDays(i).format(formatter));
            }
        } else if ("mois".equals(periodeCourbe)) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth thisMonth = YearMonth.now();
            for (int i = 11; i >= 0; i--) {
                periodes.add(thisMonth.minusMonths(i).format(formatter));
            }
        } else if ("annee".equals(periodeCourbe)) {
            formatter = DateTimeFormatter.ofPattern("yyyy");
            int thisYear = today.getYear();
            for (int i = 2; i >= 0; i--) {
                periodes.add(String.valueOf(thisYear - i));
            }
        } else {
            courbeTotal.sort((a, b) -> a.getPeriode().compareTo(b.getPeriode()));
            periodes = courbeTotal.stream().map(VentePeriodeStatDTO::getPeriode).toList();
        }

        Map<String, BigDecimal> map = new HashMap<>();
        for (VentePeriodeStatDTO v : courbeTotal) {
            map.put(v.getPeriode(), v.getQuantiteTotale());
        }

        List<String> labels = new ArrayList<>();
        List<BigDecimal> data = new ArrayList<>();
        for (String p : periodes) {
            labels.add(p);
            data.add(map.getOrDefault(p, BigDecimal.ZERO));
        }

        // Calcul du nombre moyen de produits vendus sur la période sélectionnée
        BigDecimal somme = BigDecimal.ZERO;
        int nbPeriodes = 0;
        for (BigDecimal val : data) {
            somme = somme.add(val);
            nbPeriodes++;
        }
        BigDecimal moyenne = nbPeriodes > 0 ? somme.divide(BigDecimal.valueOf(nbPeriodes), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        // Calcul du bénéfice moyen par période (jour/mois/année) sur la période sélectionnée
        List<BigDecimal> beneficesParPeriode = detailsVenteService.getBeneficeMoyenParPeriodeList(periodeCourbe, periodes);
        BigDecimal sommeBenef = BigDecimal.ZERO;
        for (BigDecimal b : beneficesParPeriode) {
            sommeBenef = sommeBenef.add(b);
        }
        BigDecimal beneficeMoyen = nbPeriodes > 0 ? sommeBenef.divide(BigDecimal.valueOf(nbPeriodes), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("data", data);
        result.put("moyenneProduit", moyenne);
        result.put("beneficeMoyen", beneficeMoyen);

        return result;
    }
}