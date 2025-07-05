package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "jour_ferie") 
public class JourFerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    private String nom;
    private Date dateFerie;
    public JourFerie() {
    }
    public JourFerie(String nom, Date dateFerie) {
        this.nom = nom;
        this.dateFerie = dateFerie;
    }
    public JourFerie(Long id, String nom, Date dateFerie) {
        this.id = id;
        this.nom = nom;
        this.dateFerie = dateFerie;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Date getDateFerie() {
        return dateFerie;
    }
    public void setDateFerie(Date dateFerie) {
        this.dateFerie = dateFerie;
    } 
}
