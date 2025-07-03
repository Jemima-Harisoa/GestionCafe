package com.gestioncafe.service.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.sql.*;
import java.util.List;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.*;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private StatutEmployeService statutEmployeService;

    @Autowired
    private DetailCandidatService detailCandidatService;

    @Autowired
    private CandidatService candidatService;

    @Autowired
    private GradeEmployeService gradeEmployeService;



    public void recruterCandidat(Long candidatId) {
        Candidat candidat = candidatRepository.findById(candidatId)
            .orElseThrow(() -> new RuntimeException("Candidat non trouvé"));

        Employe employe = new Employe();
        employe.setNom(candidat.getNom());
        employe.setDateNaissance(candidat.getDateNaissance());
        employe.setContact(candidat.getContact());
        employe.setDateRecrutement(Date.valueOf(LocalDate.now()));

        // Liaison avec entités
        employe.setGenre(candidat.getGenre());
        employe.setCandidat(candidat);

        employe = employeRepository.save(employe);

        // Création statut employé avec statut id = 1
        StatutEmploye statutEmploye = new StatutEmploye();
        statutEmploye.setEmploye(employe);
        statutEmploye.setDateStatut(Date.valueOf(LocalDate.now()));

        Statut statut = new Statut();
        statut.setId(1L);
        statutEmploye.setIdStatut(statut.getId());

        statutEmployeService.saveStatutEmploye(statutEmploye);

        // Récupérer idGrade du candidat directement
        Long idGrade = null;

        if (candidat.getGrade() != null) {
            idGrade = candidat.getGrade().getId();
        }

        if (idGrade != null) {
            GradeEmploye gradeEmploye = new GradeEmploye();
            gradeEmploye.setIdEmploye(employe.getId());
            gradeEmploye.setDateGrade(Date.valueOf(LocalDate.now()));

            Grade grade = new Grade();
            grade.setId(idGrade);
            gradeEmploye.setGrade(grade);

            gradeEmployeService.saveGradeEmploye(gradeEmploye);
        } else {
            throw new RuntimeException("Grade du candidat non trouvé");
        }
    }

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Employe findById(Long id) {
        return employeRepository.findById(id).orElseThrow();
    }

    public Employe save(Employe Employe) {
        return employeRepository.save(Employe);
    }

    public void delete(Employe Employe) {
        employeRepository.delete(Employe);
    }

    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }

}
