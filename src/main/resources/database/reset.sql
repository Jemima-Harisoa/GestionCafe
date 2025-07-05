-- Désactiver les contraintes de clé étrangère temporairement
DO $$
BEGIN
    EXECUTE (
        SELECT string_agg('ALTER TABLE "' || tablename || '" DISABLE TRIGGER ALL;', ' ')
        FROM pg_tables
        WHERE schemaname = 'public'
    );
END $$;

-- Vider toutes les tables (dans l'ordre pour respecter les FK)
TRUNCATE TABLE 
    mouvement_stock,
    approvisionnement,
    detail_fournisseur,
    fournisseur,
    seuil_matiere_premiere,
    historique_estimation,
    matiere_premiere,
    unite,
    categorie_unite,
    details_vente,
    vente,
    prix_vente_produit,
    produit,
    package,
    detail_recette,
    recette,
    client,
    employe,
    tiers,
    genre,
    administratif
RESTART IDENTITY CASCADE;

-- Réactiver les triggers (FK)
DO $$
BEGIN
    EXECUTE (
        SELECT string_agg('ALTER TABLE "' || tablename || '" ENABLE TRIGGER ALL;', ' ')
        FROM pg_tables
        WHERE schemaname = 'public'
    );
END $$;
