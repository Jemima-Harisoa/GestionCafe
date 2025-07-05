package com.gestioncafe.service.rh;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class RhService {
    @Autowired
    private StatutEmployeRepository statutEmployeRepository;
    @Autowired
    private PayementRepository payementRepository;
    @Autowired
    private CommissionRepository commissionRepository;
    @Autowired
    private AvanceRepository avanceRepository;
    @Autowired 
    private CongeRepository congeRepository;
    @Autowired
    private TypeCongeRepository typeCongeRepository;

    public List<StatutEmploye> getAllEmployesActifs() {
        return statutEmployeRepository.findDerniersStatutsParEmployeEtStatut(Long.parseLong("1"));
    }

    public List<Map<String, Object>> variationSalaireNet() {
        List<Map<String, Object>> result = new ArrayList<>();

        // 1. Obtenir les 5 derniers mois
        List<LocalDate> derniersMois = new ArrayList<>();
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        for (int i = 0; i < 5; i++) {
            derniersMois.add(now.minusMonths(i));
        }

        // 2. Transformer en java.sql.Date
        List<Date> moisRefs = derniersMois.stream()
            .map(mois -> Date.valueOf(mois))
            .collect(Collectors.toList());

        // 3. Récupérer les paiements
        List<Payement> payements = payementRepository.findAllByMoisReferenceIn(moisRefs);

        // 4. Grouper par mois et id_employe
        Map<LocalDate, Map<Long, Double>> grouped = new HashMap<>();
        for (Payement p : payements) {
            LocalDate mois = p.getMoisReference().toLocalDate();
            grouped.putIfAbsent(mois, new HashMap<>());
            Map<Long, Double> salaires = grouped.get(mois);
            salaires.put(p.getIdEmploye(),
                        salaires.getOrDefault(p.getIdEmploye(), 0.0) + p.getMontant());
        }

        // 5. Construire le format final
        for (LocalDate mois : derniersMois) {
            Map<String, Object> moisEntry = new HashMap<>();
            moisEntry.put("mois", mois.toString());

            List<Map<String, Object>> salairesList = new ArrayList<>();
            Map<Long, Double> salaires = grouped.getOrDefault(mois, Collections.emptyMap());

            for (Map.Entry<Long, Double> entry : salaires.entrySet()) {
                Map<String, Object> salaireMap = new HashMap<>();
                salaireMap.put("idEmploye", entry.getKey());
                salaireMap.put("montant", entry.getValue());
                salairesList.add(salaireMap);
            }

            moisEntry.put("salaires", salairesList);
            result.add(moisEntry);
        }

        return result;
    }

    public List<Map<String, Object>> variationCommission() {
        List<Map<String, Object>> result = new ArrayList<>();

        // Étape 1 : Obtenir les 5 derniers mois
        List<YearMonth> derniersMois = new ArrayList<>();
        YearMonth current = YearMonth.now();
        for (int i = 0; i < 5; i++) {
            derniersMois.add(current.minusMonths(i));
        }

        // Étape 2 : Calculer la plage de dates
        LocalDate minDate = derniersMois.get(4).atDay(1);
        LocalDate maxDate = derniersMois.get(0).atEndOfMonth();

        // Étape 3 : Récupérer les commissions dans la plage
        List<Commission> commissions = commissionRepository.findAllBetweenDates(
            java.sql.Date.valueOf(minDate), java.sql.Date.valueOf(maxDate)
        );

        // Étape 4 : Grouper les montants par mois et employé
        Map<YearMonth, Map<Long, Double>> grouped = new HashMap<>();
        for (Commission c : commissions) {
            LocalDate date = c.getDateCommission().toLocalDate();
            YearMonth mois = YearMonth.from(date);

            grouped.putIfAbsent(mois, new HashMap<>());
            Map<Long, Double> empMap = grouped.get(mois);
            empMap.put(c.getIdEmploye(),
                empMap.getOrDefault(c.getIdEmploye(), 0.0) + c.getMontant());
        }

        // Étape 5 : Créer la structure du résultat final
        for (YearMonth mois : derniersMois) {
            Map<String, Object> moisEntry = new HashMap<>();
            moisEntry.put("mois", mois.toString());

            List<Map<String, Object>> commissionsList = new ArrayList<>();
            Map<Long, Double> data = grouped.getOrDefault(mois, Collections.emptyMap());

            for (Map.Entry<Long, Double> entry : data.entrySet()) {
                Map<String, Object> empEntry = new HashMap<>();
                empEntry.put("idEmploye", entry.getKey());
                empEntry.put("montant", entry.getValue());
                commissionsList.add(empEntry);
            }

            moisEntry.put("commissions", commissionsList);
            result.add(moisEntry);
        }

        return result;
    }

    public List<Map<String, Object>> variationAvance() {
        
        List<Map<String, Object>> result = new ArrayList<>();

        // Étape 1 : 5 derniers mois
        List<YearMonth> derniersMois = new ArrayList<>();
        YearMonth current = YearMonth.now();
        for (int i = 0; i < 5; i++) {
            derniersMois.add(current.minusMonths(i));
        }

        // Étape 2 : Dates min et max
        LocalDate minDate = derniersMois.get(4).atDay(1);
        LocalDate maxDate = derniersMois.get(0).atEndOfMonth();

        // Étape 3 : Récupérer toutes les avances dans cette plage
        List<Avance> avances = avanceRepository.findAllBetweenDates(
            java.sql.Date.valueOf(minDate), java.sql.Date.valueOf(maxDate)
        );

        // Étape 4 : Grouper par mois et employé
        Map<YearMonth, Map<Long, Double>> grouped = new HashMap<>();
        for (Avance a : avances) {
            LocalDate date = a.getDateAvance().toLocalDate();
            YearMonth mois = YearMonth.from(date);

            grouped.putIfAbsent(mois, new HashMap<>());
            Map<Long, Double> empMap = grouped.get(mois);
            empMap.put(a.getIdEmploye(),
                empMap.getOrDefault(a.getIdEmploye(), 0.0) + a.getMontant());
        }

        // Étape 5 : Format de sortie
        for (YearMonth mois : derniersMois) {
            Map<String, Object> moisEntry = new HashMap<>();
            moisEntry.put("mois", mois.toString());

            List<Map<String, Object>> avancesList = new ArrayList<>();
            Map<Long, Double> data = grouped.getOrDefault(mois, Collections.emptyMap());

            for (Map.Entry<Long, Double> entry : data.entrySet()) {
                Map<String, Object> empEntry = new HashMap<>();
                empEntry.put("idEmploye", entry.getKey());
                empEntry.put("montant", entry.getValue());
                avancesList.add(empEntry);
            }

            moisEntry.put("avances", avancesList);
            result.add(moisEntry);
        }

        return result;
    }

    public List<TypeConge> getAllTypeConges() {
        return typeCongeRepository.findAll();
    }

    public Map<Long, Integer> nbjCongeUtilise(List<Employe> employes) {
        Map<Long, Integer> map = new HashMap<>();
        int anneeActuelle = LocalDate.now().getYear();

        List<Object[]> resultats = congeRepository.getDureeCongeParEmploye(anneeActuelle);

        for (Object[] ligne : resultats) {
            Long idEmploye = (Long) ligne[0];
            Long somme = (Long) ligne[1]; // Car SUM retourne un Long (type SQL)
            map.put(idEmploye, somme.intValue());
        }

        // S'assurer que chaque employé de la liste a une entrée dans la map
        for (Employe e : employes) {
            map.putIfAbsent(e.getId(), 0);
        }

        return map;
    }

    public Map<Long, Integer> nbjCongeReserve(List<Employe> employes) {
        Map<Long, Integer> map = new HashMap<>();
        int anneeActuelle = LocalDate.now().getYear();

        List<Object[]> resultats = congeRepository.getDureeCongeReserveParEmploye(anneeActuelle);

        for (Object[] ligne : resultats) {
            Long idEmploye = (Long) ligne[0];
            Long somme = (Long) ligne[1];
            map.put(idEmploye, somme.intValue());
        }

        // S'assurer que tous les employés ont une valeur, même si 0
        for (Employe e : employes) {
            map.putIfAbsent(e.getId(), 0);
        }

        return map;
    }

    public Map<Long, Integer> nbjCongeNonUtilise(List<Employe> employes) {
        Map<Long, Integer> map = new HashMap<>();

        // 1. Récupérer le nombre total de jours autorisés pour tous les types obligatoires
        Integer nbTotal = typeCongeRepository.getNbJourTotalObligatoire();
        if (nbTotal == null) nbTotal = 0;

        // 2. Récupérer les jours utilisés et réservés déjà calculés
        Map<Long, Integer> utilise = nbjCongeUtilise(employes);
        Map<Long, Integer> reserve = nbjCongeReserve(employes);

        // 3. Calcul du reste
        for (Employe emp : employes) {
            Long id = emp.getId();
            int u = utilise.getOrDefault(id, 0);
            int r = reserve.getOrDefault(id, 0);
            int restant = nbTotal - (u + r);
            map.put(id, Math.max(restant, 0)); // pour éviter les valeurs négatives
        }

        return map;
    }

} 

