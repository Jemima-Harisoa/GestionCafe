package com.gestioncafe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_candidat")
public class DetailCandidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_candidat", nullable = false)
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "id_serie_bac")
    private SerieBac serieBac;

    @ManyToOne
    @JoinColumn(name = "id_formation")
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "id_langue")
    private Langue langue;

    @ManyToOne
    @JoinColumn(name = "id_experience")
    private Experience experience;

    public DetailCandidat() {}


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidat getCandidat() {
        return this.candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public SerieBac getSerieBac() {
        return this.serieBac;
    }

    public void setSerieBac(SerieBac serieBac) {
        this.serieBac = serieBac;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Langue getLangue() {
        return this.langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Experience getExperience() {
        return this.experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

}
