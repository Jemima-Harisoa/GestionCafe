package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "type_conge") 
public class TypeConge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int nbJour;
    private boolean obligatoire;
    private String description;
    public TypeConge() {
    }
    public TypeConge(String nom, int nbJour, boolean obligatoire, String description) {
        this.nom = nom;
        this.nbJour = nbJour;
        this.obligatoire = obligatoire;
        this.description = description;
    }
    public TypeConge(Long id, String nom, int nbJour, boolean obligatoire, String description) {
        this.id = id;
        this.nom = nom;
        this.nbJour = nbJour;
        this.obligatoire = obligatoire;
        this.description = description;
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
    public int getNbJour() {
        return nbJour;
    }
    public void setNbJour(int nbJour) {
        this.nbJour = nbJour;
    }
    public boolean isObligatoire() {
        return obligatoire;
    }
    public void setObligatoire(boolean obligatoire) {
        this.obligatoire = obligatoire;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
