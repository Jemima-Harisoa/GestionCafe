package com.gestioncafe.controller.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.Period;

import com.gestioncafe.service.rh.*;
import com.gestioncafe.service.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

@Controller
@RequestMapping("/administratif/rh")
public class RhController {

    @Autowired
    private RhParametreService rhParametreService;

    @Autowired
    private RhService rhService;

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private DetailCandidatService detailCandidatService;

    @Autowired
    private GenreService genreService;
    
    @Autowired
    private GradeService gradeService;

    @Autowired
    private SerieBacService serieBacService;

    @Autowired
    private LangueService langueService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private FormationService formationService;


    @GetMapping
    public String accueil() {
        return "redirect:/administratif/rh/gestion-employes";
    }

    @GetMapping("/gestion-employes")
    public String gestiontEmployes() {
        return "administratif/rh/gestion-employes";
    }

    @GetMapping("/gestion-salaires")
    public String gestionSalaires(Model model) {
        List<StatutEmploye> statutEmployes = rhService.getAllEmployesActifs();
        List<Employe> employes = statutEmployes.stream()
            .map(StatutEmploye::getEmploye)
            .collect(Collectors.toList());
        model.addAttribute("variationSalaireNet", rhService.variationSalaireNet());
        model.addAttribute("variationCommission", rhService.variationCommission());
        model.addAttribute("variationAvance", rhService.variationAvance());
        model.addAttribute("employes", employes);
        return "administratif/rh/gestion-salaires";
    }

    @GetMapping("/gestion-recrutements")
    public String gestionRecrutements(Model model) {
        List<Candidat> candidats = candidatService.getAllCandidats();

        Map<Long, List<DetailCandidat>> detailsMap = candidats.stream()
            .collect(Collectors.toMap(
                Candidat::getId,
                candidat -> detailCandidatService.getDetailsByCandidatId(candidat.getId())
            ));

        model.addAttribute("genres", genreService.getAllGenres());
        model.addAttribute("grades", gradeService.getAllGrades());
        model.addAttribute("seriesBac", serieBacService.getAllSerieBacs());

        // Ajout des nouvelles listes
        model.addAttribute("langues", langueService.getAllLangues());
        model.addAttribute("formations", formationService.getAllFormations());
        model.addAttribute("experiences", experienceService.getAllExperiences());

        model.addAttribute("candidats", candidats);
        model.addAttribute("detailsMap", detailsMap);

        return "administratif/rh/gestion-recrutements";
    }

    @GetMapping("/gestion-conges")
    public String gestionConges(Model model) {
        List<StatutEmploye> statutEmployes = rhService.getAllEmployesActifs();
        List<Employe> employes = statutEmployes.stream()
                    .map(StatutEmploye::getEmploye)
                    .collect(Collectors.toList());
        model.addAttribute("employes", employes);
        model.addAttribute("nbjCongeUtilise", rhService.nbjCongeUtilise(employes));
        model.addAttribute("nbjCongeReserve", rhService.nbjCongeReserve(employes));
        model.addAttribute("nbjCongeNonUtilise", rhService.nbjCongeNonUtilise(employes));
        model.addAttribute("typeConges", rhService.getAllTypeConges());
        return "administratif/rh/gestion-conges";
    }

    @GetMapping("/parametre")
    public String parametre(Model model) {
        List<Irsa> irsas = rhParametreService.getIrsaService().findAll();
        if (!model.containsAttribute("jourFerie")) {
            model.addAttribute("jourFerie", new JourFerie());
        }
        if (!model.containsAttribute("grade")) {
            model.addAttribute("grade", new Grade());
        }
        if (!model.containsAttribute("irsa")) {
            model.addAttribute("irsa", new Irsa());
        }
        if (model.containsAttribute("irsaWrapper")) {
            IrsaWrapper irsaWrapper = (IrsaWrapper) model.getAttribute("irsaWrapper");

            // Fusionner ou synchroniser avec les données réelles
            for (Irsa dbIrsa : irsas) {
                boolean existe = irsaWrapper.getIrsas().stream()
                        .anyMatch(i -> i.getId() != null && i.getId().equals(dbIrsa.getId()));
                if (!existe) {
                    irsaWrapper.addIrsa(dbIrsa);
                }
            }

            model.addAttribute("irsaWrapper", irsaWrapper);
        } else {
            IrsaWrapper irsaWrapper = new IrsaWrapper();
            irsaWrapper.setIrsas(rhParametreService.getIrsaService().findAll());
            model.addAttribute("irsaWrapper", irsaWrapper);
        }

        model.addAttribute("listeJoursFeries", rhParametreService.getJourFerieService().findAll());
        model.addAttribute("listeGrades", rhParametreService.getGradeService().findAll());
        model.addAttribute("listeIrsas", irsas);

        return "administratif/rh/parametrage";
    }

}
