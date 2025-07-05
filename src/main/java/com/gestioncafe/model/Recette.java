package com.gestioncafe.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "recette")
public class Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    @Column(name = "quantite_produite", nullable = false)
    private BigDecimal quantiteProduite;

    @Column(name = "temps_fabrication", nullable = false)
    private BigDecimal tempsFabrication;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public BigDecimal getQuantiteProduite() {
        return quantiteProduite;
    }

    public void setQuantiteProduite(BigDecimal quantiteProduite) {
        this.quantiteProduite = quantiteProduite;
    }

    public BigDecimal getTempsFabrication() {
        return tempsFabrication;
    }

    public void setTempsFabrication(BigDecimal tempsFabrication) {
        this.tempsFabrication = tempsFabrication;
    }
}