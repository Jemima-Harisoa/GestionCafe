package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;
import java.math.BigDecimal;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double salaire;
    public Grade() {
    }
    public Grade(String nom, double salaire) {
        this.nom = nom;
        this.salaire = salaire;
    }
    public Grade(Long id, String nom, double salaire) {
        this.id = id;
        this.nom = nom;
        this.salaire = salaire;
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
    public double getSalaire() {
        return salaire;
    }
    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
    
}
