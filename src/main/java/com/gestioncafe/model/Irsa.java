package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "irsa") 
public class Irsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double taux;
    private double salaireMin;
    private double salaireMax;
    public Irsa() {
    }
    public Irsa(double taux, double salaireMin, double salaireMax) {
        this.taux = taux;
        this.salaireMin = salaireMin;
        this.salaireMax = salaireMax;
    }
    public Irsa(Long id, double taux, double salaireMin, double salaireMax) {
        this.id = id;
        this.taux = taux;
        this.salaireMin = salaireMin;
        this.salaireMax = salaireMax;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getTaux() {
        return taux;
    }
    public void setTaux(double taux) {
        this.taux = taux;
    }
    public double getSalaireMin() {
        return salaireMin;
    }
    public void setSalaireMin(double salaireMin) {
        this.salaireMin = salaireMin;
    }
    public double getSalaireMax() {
        return salaireMax;
    }
    public void setSalaireMax(double salaireMax) {
        this.salaireMax = salaireMax;
    }
}
