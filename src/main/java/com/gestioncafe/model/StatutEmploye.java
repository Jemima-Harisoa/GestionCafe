package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "statut_employe") 
public class StatutEmploye {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "id_employe")  
    private Employe employe;
    Date dateStatut;
    Long idStatut;
    public StatutEmploye() {
    }
    public StatutEmploye(Employe employe, Date dateStatut, Long idStatut) {
        this.employe = employe;
        this.dateStatut = dateStatut;
        this.idStatut = idStatut;
    }
    public StatutEmploye(Long id, Employe employe, Date dateStatut, Long idStatut) {
        this.id = id;
        this.employe = employe;
        this.dateStatut = dateStatut;
        this.idStatut = idStatut;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Employe getEmploye() {
        return employe;
    }
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    public Date getDateStatut() {
        return dateStatut;
    }
    public void setDateStatut(Date dateStatut) {
        this.dateStatut = dateStatut;
    }
    public Long getIdStatut() {
        return idStatut;
    }
    public void setIdStatut(Long idStatut) {
        this.idStatut = idStatut;
    }
   
    
}
