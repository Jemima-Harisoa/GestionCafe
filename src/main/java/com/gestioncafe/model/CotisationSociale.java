package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "cotisation_sociale") 
public class CotisationSociale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double taux;
    public CotisationSociale() {
    }
    public CotisationSociale(String nom, double taux) {
        this.nom = nom;
        this.taux = taux;
    }
    public CotisationSociale(Long id, String nom, double taux) {
        this.id = id;
        this.nom = nom;
        this.taux = taux;
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
    public double getTaux() {
        return taux;
    }
    public void setTaux(double taux) {
        this.taux = taux;
    }
}
