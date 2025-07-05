package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lieux")
public class Lieux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lieu")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    public Lieux() {}

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
}
