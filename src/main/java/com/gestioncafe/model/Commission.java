package com.gestioncafe.model;

import jakarta.persistence.*;
import java.sql.*;

@Entity
@Table(name = "commission") 
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_raison_commission") 
    private RaisonCommission raisonCommission;
    private Long idEmploye;
    private double montant;
    private Date dateCommission;
    public Commission() {
    }
    public Commission(RaisonCommission raisonCommission, Long idEmploye, double montant, Date dateCommission) {
        this.raisonCommission = raisonCommission;
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.dateCommission = dateCommission;
    }
    public Commission(Long id, RaisonCommission raisonCommission, Long idEmploye, double montant, Date dateCommission) {
        this.id = id;
        this.raisonCommission = raisonCommission;
        this.idEmploye = idEmploye;
        this.montant = montant;
        this.dateCommission = dateCommission;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public RaisonCommission getRaisonCommission() {
        return raisonCommission;
    }
    public void setRaisonCommission(RaisonCommission raisonCommission) {
        this.raisonCommission = raisonCommission;
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
    public Date getDateCommission() {
        return dateCommission;
    }
    public void setDateCommission(Date dateCommission) {
        this.dateCommission = dateCommission;
    }
}
