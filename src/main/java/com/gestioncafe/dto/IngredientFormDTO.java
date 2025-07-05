package com.gestioncafe.dto;

public class IngredientFormDTO {
    private Integer idMatierePremiere;
    private Double quantite;
    private Integer idUnite;

    public IngredientFormDTO() {}

    public Integer getIdMatierePremiere() {
        return idMatierePremiere;
    }

    public void setIdMatierePremiere(Integer idMatierePremiere) {
        this.idMatierePremiere = idMatierePremiere;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Integer getIdUnite() {
        return idUnite;
    }

    public void setIdUnite(Integer idUnite) {
        this.idUnite = idUnite;
    }
}
