package com.gestioncafe.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "detail_fournisseur")
public class DetailFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_fournisseur", nullable = false)
    private Fournisseur fournisseur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_matiere_premiere", nullable = false)
    private MatierePremiere matierePremiere;

    @Column(nullable = false)
    private Double quantite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_unite", nullable = false)
    private Unite unite;

    @Column(nullable = false)
    private Double prix;

    @Column(name = "date_modification", nullable = false)
    private LocalDate dateModification;


    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public MatierePremiere getMatierePremiere() {
        return matierePremiere;
    }

    public void setMatierePremiere(MatierePremiere matierePremiere) {
        this.matierePremiere = matierePremiere;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }
}
