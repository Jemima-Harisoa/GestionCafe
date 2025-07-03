package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "avance") 
public class Avance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_raison_avance")  
    private RaisonAvance raisonAvance;
    private Long idEmploye;
    private double montant;
    private Date dateAvance;
    public Avance() {
    }
    public Avance(RaisonAvance raisonAvance, Long idEmploye, double montant, Date dateAvance) {
        this.raisonAvance = raisonAvance;
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.dateAvance = dateAvance;
    }
    public Avance(Long id, RaisonAvance raisonAvance, Long idEmploye, double montant, Date dateAvance) {
        this.id = id;
        this.raisonAvance = raisonAvance;
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.dateAvance = dateAvance;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public RaisonAvance getRaisonAvance() {
        return raisonAvance;
    }
    public void setRaisonAvance(RaisonAvance raisonAvance) {
        this.raisonAvance = raisonAvance;
    }
    public Long getIdEmploye() {
        return idEmploye;
    }
    public void setIdEmploye(Long idEmploye) {
        this.idEmploye = idEmploye;
    }
    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }
    public Date getDateAvance() {
        return dateAvance;
    }
    public void setDateAvance(Date dateAvance) {
        this.dateAvance = dateAvance;
    }
}