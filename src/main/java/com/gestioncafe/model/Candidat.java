package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "candidat")
public class Candidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;

    @Column(name = "date_naissance", nullable = false)
    private Date dateNaissance;

    @Column(name = "date_candidature", nullable = false)
    private Date dateCandidature;

    private String contact;

    private String image;

    @Column(name = "reference_cv")
    private String referenceCv;

    @ManyToOne
    @JoinColumn(name = "id_grade", nullable = false)
    private Grade grade;

    public Candidat() {}

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

    public Date getDateCandidature() {
        return this.dateCandidature;
    }

    public void setDateCandidature(Date dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public Grade getGrade() {
        return this.grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

}
