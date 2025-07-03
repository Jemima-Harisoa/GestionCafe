package com.gestioncafe.dto;

public class SimpleMatierePremiereDTO {
    private Integer id;
    private String nom;
    private String categorieUniteNom;

    public SimpleMatierePremiereDTO(Integer id, String nom, String categorieUniteNom) {
        this.id = id;
        this.nom = nom;
        this.categorieUniteNom = categorieUniteNom;
    }
    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public String getCategorieUniteNom() { return categorieUniteNom; }
}
