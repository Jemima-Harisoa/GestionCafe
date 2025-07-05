package com.gestioncafe.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seuil_matiere_premiere")
public class SeuilMatierePremiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_matiere_premiere", nullable = false)
    private MatierePremiere matierePremiere;

    @Column(name = "seuil_min", nullable = false)
    private Double seuilMin;

    @Column(name = "seuil_max", nullable = false)
    private Double seuilMax;

    @Column(name = "date_application")
    private LocalDate dateApplication;

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public MatierePremiere getMatierePremiere() { return matierePremiere; }
    public void setMatierePremiere(MatierePremiere matierePremiere) { this.matierePremiere = matierePremiere; }

    public Double getSeuilMin() { return seuilMin; }
    public void setSeuilMin(Double seuilMin) { this.seuilMin = seuilMin; }

    public Double getSeuilMax() { return seuilMax; }
    public void setSeuilMax(Double seuilMax) { this.seuilMax = seuilMax; }

    public LocalDate getDateApplication() { return dateApplication; }
    public void setDateApplication(LocalDate dateApplication) { this.dateApplication = dateApplication; }
}
