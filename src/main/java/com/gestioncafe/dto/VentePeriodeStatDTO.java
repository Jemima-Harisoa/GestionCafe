package com.gestioncafe.dto;

import java.math.BigDecimal;

public class VentePeriodeStatDTO {
    private String periode;
    private BigDecimal quantiteTotale;
    // Ajout : champ pour le bénéfice moyen
    private BigDecimal beneficeMoyen;

    public VentePeriodeStatDTO(String periode, BigDecimal quantiteTotale) {
        this.periode = periode;
        this.quantiteTotale = quantiteTotale;
    }

    // Ajout : constructeur pour le bénéfice moyen
    public VentePeriodeStatDTO(String periode, BigDecimal quantiteTotale, BigDecimal beneficeMoyen) {
        this.periode = periode;
        this.quantiteTotale = quantiteTotale;
        this.beneficeMoyen = beneficeMoyen;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public BigDecimal getQuantiteTotale() {
        return quantiteTotale;
    }

    public void setQuantiteTotale(BigDecimal quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
    }

    // Ajout : getter/setter pour beneficeMoyen
    public BigDecimal getBeneficeMoyen() {
        return beneficeMoyen;
    }

    public void setBeneficeMoyen(BigDecimal beneficeMoyen) {
        this.beneficeMoyen = beneficeMoyen;
    }
}
