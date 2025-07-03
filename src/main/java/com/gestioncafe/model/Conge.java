package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "conge") 
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_type_conge")  
    private TypeConge typeConge;
    private Date dateDebut;
    private Date dateFin;
    private int duree;
    private Long idEmploye;
    public Conge() {
    }
    public Conge(TypeConge typeConge, Date dateDebut, Date dateFin, int duree, Long idEmploye) {
        this.typeConge = typeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.duree = duree;
        this.idEmploye = idEmploye;
    }
    public Conge(Long id, TypeConge typeConge, Date dateDebut, Date dateFin, int duree, Long idEmploye) {
        this.id = id;
        this.typeConge = typeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.duree = duree;
        this.idEmploye = idEmploye;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public TypeConge getTypeConge() {
        return typeConge;
    }
    public void setTypeConge(TypeConge typeConge) {
        this.typeConge = typeConge;
    }
    public Date getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public Date getDateFin() {
        return dateFin;
    }
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    public int getDuree() {
        return duree;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }
    public Long getIdEmploye() {
        return idEmploye;
    }
    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }
}
