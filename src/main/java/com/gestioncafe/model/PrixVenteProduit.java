package com.gestioncafe.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prix_vente_produit")
public class PrixVenteProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produit produit;

    @Column(name = "prix_vente", nullable = false)
    private BigDecimal prixVente;

    @Column(name = "date_application", nullable = false)
    private LocalDateTime dateApplication;

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

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public LocalDateTime getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(LocalDateTime dateApplication) {
        this.dateApplication = dateApplication;
    }
}
