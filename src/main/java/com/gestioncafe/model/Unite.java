package com.gestioncafe.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "unite")
public class Unite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @Column(name = "valeur_pr_norme", precision = 20, scale = 10)
    private BigDecimal valeurParNorme;

    @ManyToOne
    @JoinColumn(name = "categorie_unite_id")
    private CategorieUnite categorieUnite;

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getValeurParNorme() {
        return valeurParNorme;
    }

    public void setValeurParNorme(BigDecimal valeurParNorme) {
        this.valeurParNorme = valeurParNorme;
    }

    public CategorieUnite getCategorieUnite() {
        return categorieUnite;
    }

    public void setCategorieUnite(CategorieUnite categorieUnite) {
        this.categorieUnite = categorieUnite;
    }

    // Si tu utilises encore getValeurPrNorme() dans le code, ajoute aussi :
    public BigDecimal getValeurPrNorme() {
        return getValeurParNorme();
    }
}
