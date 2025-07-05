package com.gestioncafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "marketing")
public class Marketing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    @Column(name = "budget_moyen", nullable = false)
    private BigDecimal budgetMoyen;

    @Column(name = "estimation_prix_produit", nullable = false)
    private BigDecimal estimationPrixProduit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_lieu", nullable = false)
    private Lieux lieu;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    public Marketing() {
    }

    public Marketing(int age, BigDecimal budgetMoyen, BigDecimal estimationPrixProduit, Genre genre, Lieux lieu, Produit produit) {
        this.age = age;
        this.budgetMoyen = budgetMoyen;
        this.estimationPrixProduit = estimationPrixProduit;
        this.genre = genre;
        this.lieu = lieu;
        this.produit = produit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getBudgetMoyen() {
        return budgetMoyen;
    }

    public void setBudgetMoyen(BigDecimal budgetMoyen) {
        this.budgetMoyen = budgetMoyen;
    }

    public BigDecimal getEstimationPrixProduit() {
        return estimationPrixProduit;
    }

    public void setEstimationPrixProduit(BigDecimal estimationPrixProduit) {
        this.estimationPrixProduit = estimationPrixProduit;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Lieux getLieu() {
        return lieu;
    }

    public void setLieu(Lieux lieu) {
        this.lieu = lieu;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Marketing{" +
                "id=" + id +
                ", age=" + age +
                ", budgetMoyen=" + budgetMoyen +
                ", estimationPrixProduit=" + estimationPrixProduit +
                ", genre=" + (genre != null ? genre.getValeur() : "null") +
                ", lieu=" + (lieu != null ? lieu.getNom() : "null") +
                ", produit=" + (produit != null ? produit.getNom() : "null") +
                '}';
    }
}
