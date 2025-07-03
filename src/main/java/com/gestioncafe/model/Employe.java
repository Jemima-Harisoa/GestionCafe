package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.Date;
import com.gestioncafe.model.Genre;
import com.gestioncafe.model.Candidat;


@Entity
@Table(name = "employe")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_genre", referencedColumnName = "id")
    private Genre genre;

    @Column(name = "date_naissance", nullable = false)
    private Date dateNaissance;

    @Column(nullable = false)
    private String contact;

    @Column(name = "date_recrutement", nullable = false)
    private Date dateRecrutement;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_candidat", referencedColumnName = "id")
    private Candidat candidat;

    @Column
    private String image;

    @Column(name = "reference_cv", columnDefinition = "TEXT")
    private String referenceCv;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Date getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getDateRecrutement() {
        return this.dateRecrutement;
    }

    public void setDateRecrutement(Date dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }

    public Candidat getCandidat() {
        return this.candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReferenceCv() {
        return this.referenceCv;
    }

    public void setReferenceCv(String referenceCv) {
        this.referenceCv = referenceCv;
    }

    // Getters & Setters
}
