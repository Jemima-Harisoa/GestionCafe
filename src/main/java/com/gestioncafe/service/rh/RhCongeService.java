package com.gestioncafe.service.rh;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class RhCongeService {
    @Autowired
    private CongeRepository congeRepository;
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private TypeCongeRepository typeCongeRepository;
    @Autowired 
    JourFerieRepository jourFerieRepository;
    
    @Transactional
    public void ajoutConge(Long idEmploye, Long idTypeConge, Date dateDebut, Date dateFin) throws Exception{
        Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé avec l'ID: " + idEmploye));
        TypeConge typeConge = typeCongeRepository.findById(idTypeConge)
                .orElseThrow(() -> new RuntimeException("Type Conge non trouvé avec l'ID: " + idTypeConge));
        if(! dateDebut.after(dateFin)) {
            throw new Exception("Date debut apres date fin");
        }
        if(congeRepository.existeChevauchementGlobal(dateDebut, dateFin)) {
            throw new Exception("Date deja pris");
        }
        // Bloc à insérer ici : découpage par jours non fériés et non weekends

        List<JourFerie> joursFeries = jourFerieRepository.findAll();
        Set<LocalDate> joursFeriesSet = joursFeries.stream()
            .map(jf -> jf.getDateFerie().toLocalDate())
            .collect(Collectors.toSet());

        LocalDate currentStart = dateDebut.toLocalDate();
        LocalDate endDate = dateFin.toLocalDate();

        while (!currentStart.isAfter(endDate)) {
            LocalDate currentEnd = currentStart;

            while (true) {
                LocalDate nextDay = currentEnd.plusDays(1);
                if (nextDay.isAfter(endDate)) break;
                DayOfWeek dow = nextDay.getDayOfWeek();
                if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) break;
                if (joursFeriesSet.contains(nextDay)) break;
                currentEnd = nextDay;
            }

            long intervalleDuree = ChronoUnit.DAYS.between(currentStart, currentEnd) + 1;

            congeRepository.save(new Conge(typeConge, Date.valueOf(currentStart), Date.valueOf(currentEnd), (int) intervalleDuree, idEmploye));

            currentStart = currentEnd.plusDays(1);

            while (!currentStart.isAfter(endDate)) {
                DayOfWeek dow = currentStart.getDayOfWeek();
                if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY || joursFeriesSet.contains(currentStart)) {
                    currentStart = currentStart.plusDays(1);
                } else {
                    break;
                }
            }
        }
    } 
    
    public List<JourCongeFerie> jours() {
        List<JourCongeFerie> liste = new ArrayList<>();

        int annee = LocalDate.now().getYear();
        LocalDate debut = LocalDate.of(annee, 1, 1);
        LocalDate fin = LocalDate.of(annee, 12, 31);

        // Récupérer tous les jours fériés et congés
        List<JourFerie> feries = jourFerieRepository.findAll();
        List<Conge> conges = congeRepository.findAll();

        // Charger les employés pour retrouver leurs objets
        Map<Long, Employe> employesMap = employeRepository.findAll().stream()
            .collect(Collectors.toMap(Employe::getId, e -> e));

        // Ajouter les jours fériés dans la plage
        for (JourFerie jf : feries) {
            java.sql.Date sqlDate = jf.getDateFerie();
            if (sqlDate != null) {
                LocalDate date = sqlDate.toLocalDate();
                if (!date.isBefore(debut) && !date.isAfter(fin)) {
                    liste.add(new JourCongeFerie(Date.valueOf(date), jf, null));
                }
            }
        }

        // Ajouter les jours de congé (1 ligne par jour + employé)
        for (Conge c : conges) {
            java.sql.Date dDebut = c.getDateDebut();
            java.sql.Date dFin = c.getDateFin();

            if (dDebut != null && dFin != null) {
                LocalDate d1 = dDebut.toLocalDate();
                LocalDate d2 = dFin.toLocalDate();

                for (LocalDate d = d1; !d.isAfter(d2); d = d.plusDays(1)) {
                    if (!d.isBefore(debut) && !d.isAfter(fin)) {
                        Employe emp = employesMap.get(c.getIdEmploye());
                        liste.add(new JourCongeFerie(Date.valueOf(d), null, emp));
                    }
                }
            }
        }

        return liste;
    }

}
