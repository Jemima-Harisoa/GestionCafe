package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valeur;

    private String description;

    public Formation() {}

    // Getters et setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeur() {
        return this.valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // ...
}
