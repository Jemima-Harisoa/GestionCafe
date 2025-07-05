package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "raison_avance") 
public class RaisonAvance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valeur;
    private String description;
    public RaisonAvance() {
    }
    public RaisonAvance(String valeur, String description) {
        this.valeur = valeur;
        this.description = description;
    }
    public RaisonAvance(Long id, String valeur, String description) {
        this.id = id;
        this.valeur = valeur;
        this.description = description;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValeur() {
        return valeur;
    }
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    } 
}
