package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "grade_employe") 
public class GradeEmploye {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Long idEmploye;
    @ManyToOne
    @JoinColumn(name = "id_grade")  
    private Grade grade;
    Date dateGrade;
    public GradeEmploye() {
    }
    public GradeEmploye(Long idEmploye, Grade grade, Date dateGrade) {
        this.idEmploye = idEmploye;
        this.grade = grade;
        this.dateGrade = dateGrade;
    }
    public GradeEmploye(Long id, Long idEmploye, Grade grade, Date dateGrade) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.grade = grade;
        this.dateGrade = dateGrade;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdEmploye() {
        return idEmploye;
    }
    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }
    public Grade getGrade() {
        return grade;
    }
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public Date getDateGrade() {
        return dateGrade;
    }
    public void setDateGrade(Date dateGrade) {
        this.dateGrade = dateGrade;
    }
}
