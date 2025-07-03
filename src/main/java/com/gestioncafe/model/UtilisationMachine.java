package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
public class UtilisationMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_machine", nullable = false)
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit produit;

    private Double duree;

    @ManyToOne
    @JoinColumn(name = "id_unite")
    private Unite unite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Double getDuree() {
        return duree;
    }

    public void setDuree(Double duree) {
        this.duree = duree;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    
}
