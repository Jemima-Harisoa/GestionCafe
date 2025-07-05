package com.gestioncafe.dto;

import java.math.BigDecimal;

public class VenteProduitStatDTO {
    private final Integer produitId;
    private final String produitNom;
    private final BigDecimal quantiteTotale;
    private final BigDecimal beneficeTotal;

    public VenteProduitStatDTO(Integer produitId, String produitNom, BigDecimal quantiteTotale, BigDecimal beneficeTotal) {
        this.produitId = produitId;
        this.produitNom = produitNom;
        this.quantiteTotale = quantiteTotale;
        this.beneficeTotal = beneficeTotal;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public String getProduitNom() {
        return produitNom;
    }

    public BigDecimal getQuantiteTotale() {
        return quantiteTotale;
    }

    public BigDecimal getBeneficeTotal() {
        return beneficeTotal;
    }
}
