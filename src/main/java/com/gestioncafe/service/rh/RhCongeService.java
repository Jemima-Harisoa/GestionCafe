package com.gestioncafe.service.rh;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
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
    public void ajoutConge(Long idEmploye, Long idTypeConge, Date dateDebut, Date dateFin) {
        // Validate inputs
        if (dateDebut == null || dateFin == null) {
            throw new IllegalArgumentException("Les dates de début et de fin ne peuvent pas être nulles");
        }
        if (dateDebut.after(dateFin)) {
            throw new IllegalArgumentException("La date de début ne peut pas être postérieure à la date de fin");
        }

        // Fetch employee and leave type
        Employe employe = employeRepository.findById(idEmploye)
                .orElseThrow(() -> new IllegalArgumentException("Employé non trouvé avec l'ID: " + idEmploye));
        TypeConge typeConge = typeCongeRepository.findById(idTypeConge)
                .orElseThrow(() -> new IllegalArgumentException("Type de congé non trouvé avec l'ID: " + idTypeConge));

        // Check for overlapping leaves (employee-specific, if needed)
        if (congeRepository.existeChevauchementGlobal(dateDebut, dateFin)) {
            throw new IllegalArgumentException("Les dates sélectionnées chevauchent un congé existant");
        }

        // Fetch holidays within the date range for efficiency
        LocalDate startLocalDate = dateDebut.toLocalDate();
        LocalDate endLocalDate = dateFin.toLocalDate();
        List<JourFerie> joursFeries = jourFerieRepository.findAll();
        Set<LocalDate> joursFeriesSet = joursFeries.stream()
                .map(jf -> jf.getDateFerie().toLocalDate())
                .collect(Collectors.toSet());

        // Collect valid leave periods (excluding weekends and holidays)
        List<Conge> congesToSave = new ArrayList<>();
        LocalDate currentStart = startLocalDate;

        while (!currentStart.isAfter(endLocalDate)) {
            LocalDate currentEnd = currentStart;

            // Find the end of the current valid period (non-weekend, non-holiday)
            while (!currentEnd.isAfter(endLocalDate)) {
                LocalDate nextDay = currentEnd.plusDays(1);
                DayOfWeek dow = nextDay.getDayOfWeek();
                if (nextDay.isAfter(endLocalDate) || dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY || joursFeriesSet.contains(nextDay)) {
                    break;
                }
                currentEnd = nextDay;
            }

            // Calculate duration and create Conge entity
            if (!joursFeriesSet.contains(currentStart) && currentStart.getDayOfWeek() != DayOfWeek.SATURDAY && currentStart.getDayOfWeek() != DayOfWeek.SUNDAY) {
                long intervalleDuree = ChronoUnit.DAYS.between(currentStart, currentEnd) + 1;
                Conge conge = new Conge();
                conge.setTypeConge(typeConge);
                conge.setDateDebut(Date.valueOf(currentStart));
                conge.setDateFin(Date.valueOf(currentEnd));
                conge.setDuree((int) intervalleDuree);
                conge.setIdEmploye(idEmploye); // Set the Employe entity, not just ID
                congesToSave.add(conge);
            }

            // Move to the next valid start date
            currentStart = currentEnd.plusDays(1);
            while (!currentStart.isAfter(endLocalDate) &&
                (currentStart.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    currentStart.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    joursFeriesSet.contains(currentStart))) {
                currentStart = currentStart.plusDays(1);
            }
        }

        // Batch save all leave periods
        congeRepository.saveAll(congesToSave);
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
