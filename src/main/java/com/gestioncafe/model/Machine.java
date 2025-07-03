package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String marque;
    private double puissance;
    private double tauxActivite;

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }

    public double getPuissance() { return puissance; }
    public void setPuissance(double puissance) { this.puissance = puissance; }

    public double getTauxActivite() { return tauxActivite; }
    public void setTauxActivite(double tauxActivite) { this.tauxActivite = tauxActivite; }
}
