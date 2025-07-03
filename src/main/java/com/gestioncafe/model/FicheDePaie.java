package com.gestioncafe.model;
import java.sql.*;

public class FicheDePaie {
    private Date moisReference;
    private double salaireDeBase;
    private double abscences;
    private double commissions;
    private double retenuesSociales;
    private double impots;
    private double salaireBrut;
    private double salaireNetImposable;
    private double salaireNet;
    private double retenueAvance;
    private double netAPayer;
    public FicheDePaie(Date moisReference, double salaireDeBase, double abscences, double commissions,
            double retenuesSociales, double impots, double retenueAvance) {
        this.moisReference = moisReference;
        this.salaireDeBase = salaireDeBase;
        this.abscences = abscences;
        this.commissions = commissions;
        this.retenuesSociales = retenuesSociales;
        this.impots = impots;
        this.retenueAvance =retenueAvance;
        this.salaireBrut = this.salaireDeBase + this.commissions - this.abscences;
        this.salaireNetImposable = this.salaireBrut - this.retenuesSociales;
        this.salaireNet = this.salaireNetImposable - this.impots;
        this.netAPayer = this.salaireNet - this.retenueAvance;
    }
    public Date getmoisReference() {
        return moisReference;
    }
    public void setmoisReference(Date moisReference) {
        this.moisReference = moisReference;
    }
    public double getSalaireDeBase() {
        return salaireDeBase;
    }
    public void setSalaireDeBase(double salaireDeBase) {
        this.salaireDeBase = salaireDeBase;
    }
    public double getAbscences() {
        return abscences;
    }
    public void setAbscences(double abscences) {
        this.abscences = abscences;
    }
    public double getCommissions() {
        return commissions;
    }
    public void setCommissions(double commissions) {
        this.commissions = commissions;
    }
    public double getRetenuesSociales() {
        return retenuesSociales;
    }
    public void setRetenuesSociales(double retenuesSociales) {
        this.retenuesSociales = retenuesSociales;
    }
    public double getImpots() {
        return impots;
    }
    public void setImpots(double impots) {
        this.impots = impots;
    }
    public double getSalaireBrut() {
        return salaireBrut;
    }
    public void setSalaireBrut(double salaireBrut) {
        this.salaireBrut = salaireBrut;
    }
    public double getSalaireNetImposable() {
        return salaireNetImposable;
    }
    public void setSalaireNetImposable(double salaireNetImposable) {
        this.salaireNetImposable = salaireNetImposable;
    }
    public double getSalaireNet() {
        return salaireNet;
    }
    public void setSalaireNet(double salaireNet) {
        this.salaireNet = salaireNet;
    }
    public double getRetenueAvance() {
        return retenueAvance;
    }
    public void setRetenueAvance(double retenueAvance) {
        this.retenueAvance = retenueAvance;
    }
    public double getNetAPayer() {
        return netAPayer;
    }
    public void setNetAPayer(double netAPayer) {
        this.netAPayer = netAPayer;
    }
    
}
