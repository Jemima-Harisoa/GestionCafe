package com.gestioncafe.dto;

import java.math.BigDecimal;

public class BeneficePeriodeStatDTO {
    private final String periode;
    private final BigDecimal quantiteMoyenne;
    private final BigDecimal beneficeTotal;

    public BeneficePeriodeStatDTO(String periode, BigDecimal quantiteMoyenne, BigDecimal beneficeTotal) {
        this.periode = periode;
        this.quantiteMoyenne = quantiteMoyenne;
        this.beneficeTotal = beneficeTotal;
    }

    public String getPeriode() {
        return periode;
    }

    public BigDecimal getQuantiteMoyenne() {
        return quantiteMoyenne;
    }

    public BigDecimal getBeneficeTotal() {
        return beneficeTotal;
    }
}
