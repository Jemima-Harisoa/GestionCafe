package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "payement") 
public class Payement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idEmploye;
    private double montant;
    private Date moisReference;
    private Date datePayement;
    private String referencePayement;
    public Payement() {
    }

    public Payement(Long idEmploye, double montant, Date moisReference, Date datePayement, String referencePayement) {
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.moisReference = moisReference;
        this.datePayement = datePayement;
        this.referencePayement = referencePayement;
    }
    public Payement(Long id, Long idEmploye, double montant, Date moisReference, Date datePayement,
        String referencePayement) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.moisReference = moisReference;
        this.datePayement = datePayement;
        this.referencePayement = referencePayement;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Date getMoisReference() {
        return moisReference;
    }
    public void setMoisReference(Date moisReference) {
        this.moisReference = moisReference;
    }
    public Date getDatePayement() {
        return datePayement;
    }
    public void setDatePayement(Date datePayement) {
        this.datePayement = datePayement;
    }
    public String getReferencePayement() {
        return referencePayement;
    }
    public void setReferencePayement(String referencePayement) {
        this.referencePayement = referencePayement;
    }
    
}
