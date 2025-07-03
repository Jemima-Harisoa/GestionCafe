CREATE TABLE administratif
(
    id           SERIAL PRIMARY KEY,
    nom          VARCHAR(255),
    mot_de_passe VARCHAR(255)
);

-- Table genre
CREATE TABLE genre
(
    id  SERIAL PRIMARY KEY,
    nom VARCHAR(255)
);

CREATE TABLE tiers
(
    id       SERIAL PRIMARY KEY,
    nom      VARCHAR(255) NOT NULL,
    prenom   VARCHAR(255) NOT NULL,
    id_genre INTEGER      NOT NULL,
    contact  VARCHAR(255),
    email    VARCHAR(255),
    image    VARCHAR(255),

    CONSTRAINT fk_client_genre
        FOREIGN KEY (id_genre)
            REFERENCES genre (id)
);

-- Table client
CREATE TABLE client
(
    id             SERIAL PRIMARY KEY,
    id_tiers       INTEGER NOT NULL,
    date_adhesion  DATE    NOT NULL,
    date_naissance DATE    NULL,

    FOREIGN KEY (id_tiers) REFERENCES tiers (id)
);

-- Table grade
CREATE TABLE grade
(
    id      SERIAL PRIMARY KEY,
    salaire DECIMAL(10, 2) NOT NULL,
    nom     VARCHAR(255)   NOT NULL
);

-- Table serie_bac
CREATE TABLE serie_bac
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table formation
CREATE TABLE formation
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table experience
CREATE TABLE experience
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table langue
CREATE TABLE langue
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

CREATE TABLE candidat
(
    id               SERIAL PRIMARY KEY,
    date_candidature DATE         NOT NULL,
    nom              VARCHAR(255) NOT NULL,
    id_genre         INTEGER      NOT NULL,
    date_naissance   DATE         NOT NULL,
    contact          VARCHAR(20)  NOT NULL,
    image            VARCHAR(255) NULL,
    reference_cv     TEXT         NULL,
    id_grade         INTEGER      NOT NULL,

    CONSTRAINT fk_candidat_genre
        FOREIGN KEY (id_genre)
            REFERENCES genre (id),

    CONSTRAINT fk_candidat_grade
        FOREIGN KEY (id_grade)
            REFERENCES grade (id)
);

-- Table detail_candidat
CREATE TABLE detail_candidat
(
    id            SERIAL PRIMARY KEY,
    id_candidat   INTEGER NOT NULL,
    id_serie_bac  INTEGER NULL,
    id_formation  INTEGER NULL,
    id_langue     INTEGER NULL,
    id_experience INTEGER NULL,

    CONSTRAINT fk_detail_candidat_candidat
        FOREIGN KEY (id_candidat)
            REFERENCES candidat (id),

    CONSTRAINT fk_detail_candidat_serie_bac
        FOREIGN KEY (id_serie_bac)
            REFERENCES serie_bac (id),

    CONSTRAINT fk_detail_candidat_formation
        FOREIGN KEY (id_formation)
            REFERENCES formation (id),

    CONSTRAINT fk_detail_candidat_langue
        FOREIGN KEY (id_langue)
            REFERENCES langue (id),

    CONSTRAINT fk_detail_candidat_experience
        FOREIGN KEY (id_experience)
            REFERENCES experience (id)
);

-- Table employe

CREATE TABLE employe
(
    id               SERIAL PRIMARY KEY,
    id_tiers         INTEGER NOT NULL,
    id_genre         INTEGER NOT NULL,
    date_naissance   DATE    NOT NULL,
    date_recrutement DATE    NOT NULL,
    date_demission   DATE,
    reference_cv     TEXT    NULL,

    FOREIGN KEY (id_tiers) REFERENCES tiers (id),
    FOREIGN KEY (id_genre) REFERENCES genre (id)
);

CREATE TABLE statut
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

CREATE TABLE statut_employe
(
    id          SERIAL PRIMARY KEY,
    id_employe  INTEGER   NOT NULL,
    date_statut TIMESTAMP NOT NULL,
    id_statut   INTEGER   NOT NULL,

    CONSTRAINT fk_statut_employe_statut
        FOREIGN KEY (id_statut)
            REFERENCES statut (id),

    CONSTRAINT fk_activite_employe_employe
        FOREIGN KEY (id_employe)
            REFERENCES employe (id)
);

-- Table grade_employe
CREATE TABLE grade_employe
(
    id         SERIAL PRIMARY KEY,
    id_employe INTEGER NOT NULL,
    id_grade   INTEGER NOT NULL,
    date_grade DATE    NOT NULL,

    CONSTRAINT fk_grade_employe_employe
        FOREIGN KEY (id_employe)
            REFERENCES employe (id),

    CONSTRAINT fk_grade_employe_grade
        FOREIGN KEY (id_grade)
            REFERENCES grade (id)
);

-- Table presence_employe
CREATE TABLE presence
(
    id            SERIAL PRIMARY KEY,
    id_employe    INTEGER   NOT NULL,
    date_presence DATE      NOT NULL,
    date_arrivee  TIMESTAMP NULL,
    est_present   BOOLEAN   NULL,

    CONSTRAINT fk_presence_employe_employe
        FOREIGN KEY (id_employe)
            REFERENCES employe (id)
);

-- Table jour_ferie
CREATE TABLE jour_ferie
(
    id         SERIAL PRIMARY KEY,
    nom        VARCHAR(255) NOT NULL,
    date_ferie DATE         NOT NULL
);

-- Table type_conge
CREATE TABLE type_conge
(
    id          SERIAL PRIMARY KEY,
    nom         VARCHAR(255) NOT NULL,
    nb_jour     INTEGER      NOT NULL,
    obligatoire BOOLEAN      NOT NULL,
    description VARCHAR(500) NULL
);

-- Table conge
CREATE TABLE conge
(
    id            SERIAL PRIMARY KEY,
    id_type_conge INTEGER NOT NULL,
    date_debut    DATE    NOT NULL,
    date_fin      DATE    NOT NULL,
    duree         INTEGER NOT NULL,
    id_employe    INTEGER NOT NULL,

    CONSTRAINT fk_conge_type_conge
        FOREIGN KEY (id_type_conge)
            REFERENCES type_conge (id)
);

-- Table irsa
CREATE TABLE irsa
(
    id          SERIAL PRIMARY KEY,
    taux        DECIMAL(5, 2)  NOT NULL,
    salaire_min DECIMAL(10, 2) NOT NULL,
    salaire_max DECIMAL(10, 2) NOT NULL
);

-- Table cotisation_sociale
CREATE TABLE cotisation_sociale
(
    id   SERIAL PRIMARY KEY,
    nom  VARCHAR(255)  NOT NULL,
    taux DECIMAL(5, 2) NOT NULL
);

-- Table raison_avance
CREATE TABLE raison_avance
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table avance
CREATE TABLE avance
(
    id               SERIAL PRIMARY KEY,
    id_raison_avance INTEGER        NOT NULL,
    id_employe       INTEGER        NOT NULL,
    date_avance      DATE           NOT NULL,
    montant          DECIMAL(10, 2) NOT NULL,


    CONSTRAINT fk_avance_raison_avance
        FOREIGN KEY (id_raison_avance)
            REFERENCES raison_avance (id)
);

-- Table raison_commission
CREATE TABLE raison_commission
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table commission
CREATE TABLE commission
(
    id                   SERIAL PRIMARY KEY,
    id_raison_commission INTEGER        NOT NULL,
    id_employe           INTEGER        NOT NULL,
    date_commission      DATE           NOT NULL,
    montant              DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_commission_raison_commission
        FOREIGN KEY (id_raison_commission)
            REFERENCES raison_commission (id)
);

-- Table payement employe
CREATE TABLE payement
(
    id                 SERIAL PRIMARY KEY,
    id_employe         INTEGER        NOT NULL,
    date_payement      DATE           NOT NULL,
    montant            DECIMAL(10, 2) NOT NULL,
    reference_payement VARCHAR(255)   NULL,
    mois_reference     DATE           NOT NULL,

    CONSTRAINT fk_payement_employe_employe
        FOREIGN KEY (id_employe)
            REFERENCES employe (id)
);

-- catégorie des unités
CREATE TABLE categorie_unite
(
    id    SERIAL PRIMARY KEY,
    nom   VARCHAR(100), -- volume, masse, temps, ...
    norme VARCHAR(100)  -- kg, litre, ... : norme d'unité utilisée par l'application (ex : masse -> kg, ...)
);

-- Table unite pour l'utilisateur
CREATE TABLE unite
(
    id                 SERIAL PRIMARY KEY,
    nom                VARCHAR(50),   --kg, g, l, cl, ...
    categorie_unite_id INTEGER REFERENCES categorie_unite (id),
    valeur_pr_norme    DECIMAL(10, 10) -- valeur par rapport au norme
);

-- Table matiere_premiere
CREATE TABLE matiere_premiere
(
    id                   SERIAL PRIMARY KEY,
    nom                  VARCHAR(255) NOT NULL,
    id_unite             INTEGER      NOT NULL,
    id_categorie_unite_id INTEGER      NOT NULL REFERENCES categorie_unite (id),
    stock                DECIMAL(10, 2) DEFAULT 0, -- d
    image                VARCHAR(255) NULL,

    CONSTRAINT fk_matiere_premiere_unite
        FOREIGN KEY (id_unite)
            REFERENCES unite (id)
);

-- Table historique d'estimation des prix des matières premières (fait par le gérant)
CREATE TABLE historique_estimation
(
    id                  SERIAL PRIMARY KEY,
    id_matiere_premiere INTEGER NOT NULL REFERENCES matiere_premiere (id),
    prix                DECIMAL(10, 2),
    quatite             DECIMAL(10, 2),
    id_unite            INTEGER NOT NULL REFERENCES unite (id),
    date_application    DATE
);

CREATE TABLE seuil_matiere_premiere
(
    id                  SERIAL PRIMARY KEY,
    id_matiere_premiere INTEGER,
    seuil_min           DECIMAL(10, 2) NOT NULL,
    seuil_max           DECIMAL(10, 2) NOT NULL,
    date_application    DATE
);

-- Table fournisseur
CREATE TABLE fournisseur
(
    id      SERIAL PRIMARY KEY,
    nom     VARCHAR(255)   NOT NULL,
    contact VARCHAR(255)   NULL,
    frais   DECIMAL(10, 2) NOT NULL,
    email   VARCHAR(255)   NULL
);

-- Table detail_fournisseur
CREATE TABLE detail_fournisseur
(
    id                  SERIAL PRIMARY KEY,
    id_fournisseur      INTEGER        NOT NULL,
    id_matiere_premiere INTEGER        NOT NULL,
    quantite            DECIMAL(10, 2) NOT NULL, -- à préciser car chez certains fournisseurs, la quantité pourrait se mesurer avec des sacs de 50kg.
    id_unite            INTEGER        NOT NULL,
    prix                DECIMAL(10, 2) NOT NULL,
    date_modification   DATE           NOT NULL,

    FOREIGN KEY (id_unite) REFERENCES unite (id),

    CONSTRAINT fk_detail_fournisseur_fournisseur
        FOREIGN KEY (id_fournisseur)
            REFERENCES fournisseur (id),

    CONSTRAINT fk_detail_fournisseur_matiere_premiere
        FOREIGN KEY (id_matiere_premiere)
            REFERENCES matiere_premiere (id)
);

-- Table package
CREATE TABLE package
(
    id   SERIAL PRIMARY KEY,
    nom  VARCHAR(255)   NOT NULL,
    cout DECIMAL(10, 2) NOT NULL
);

-- Table produit
CREATE TABLE produit
(
    id               SERIAL PRIMARY KEY,
    nom              VARCHAR(255)   NOT NULL,
    description      VARCHAR(500)   NULL,
    stock            DECIMAL(10, 2) NOT NULL,
    image            VARCHAR(255)   NULL,
    delai_peremption DECIMAL(10, 2) NULL,
    id_unite         INTEGER        NOT NULL, -- pièce, gobelet, ...
    id_package       INTEGER        NOT NULL,

    FOREIGN KEY (id_unite) REFERENCES unite (id),

    CONSTRAINT fk_recette_package
        FOREIGN KEY (id_package)
            REFERENCES package (id)
);

-- historique des prix de vente des produits
CREATE TABLE prix_vente_produit
(
    id               SERIAL PRIMARY KEY,
    id_produit       INTEGER NOT NULL,
    prix_vente       DECIMAL(10, 2),
    date_application TIMESTAMP
);

-- Table recette
CREATE TABLE recette
(
    id                SERIAL PRIMARY KEY,
    id_produit        INTEGER        NOT NULL,
    quantite_produite DECIMAL(10, 2) NOT NULL,
    temps_fabrication DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_recette_produit
        FOREIGN KEY (id_produit)
            REFERENCES produit (id)
);

-- Table detail_recette
CREATE TABLE detail_recette
(
    id                  SERIAL PRIMARY KEY,
    id_recette          INTEGER        NOT NULL,
    id_matiere_premiere INTEGER        NOT NULL,
    id_unite            INTEGER        NOT NULL,
    quantite            DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (id_unite) REFERENCES unite (id),

    CONSTRAINT fk_detail_recette_recette
        FOREIGN KEY (id_recette)
            REFERENCES recette (id),

    CONSTRAINT fk_detail_recette_matiere_premiere
        FOREIGN KEY (id_matiere_premiere)
            REFERENCES matiere_premiere (id)
);

-- Table vente
CREATE TABLE vente
(
    id         SERIAL PRIMARY KEY,
    date_vente TIMESTAMP NOT NULL,
    id_client  INTEGER,
    id_employe INTEGER   NOT NULL,

    CONSTRAINT fk_vente_client
        FOREIGN KEY (id_client)
            REFERENCES client (id),

    CONSTRAINT fk_vente_employe
        FOREIGN KEY (id_employe)
            REFERENCES employe (id)
);

-- Table details_vente
CREATE TABLE details_vente
(
    id            SERIAL PRIMARY KEY,
    id_vente      INTEGER        NOT NULL,
    id_produit    INTEGER        NOT NULL,
    quantite      DECIMAL(10, 2) NOT NULL,
    prix_unitaire DECIMAL(10, 2) NOT NULL,
    montant       DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_details_vente_vente
        FOREIGN KEY (id_vente)
            REFERENCES vente (id),

    CONSTRAINT fk_details_vente_produit
        FOREIGN KEY (id_produit)
            REFERENCES produit (id)
);

CREATE TABLE production
(
    id              SERIAL PRIMARY KEY,
    id_employe      INTEGER,
    id_recette      INTEGER,
    quantite        DECIMAL(10, 2),
    date_production TIMESTAMP,

    FOREIGN KEY (id_employe) REFERENCES employe (id),
    FOREIGN KEY (id_recette) REFERENCES recette (id)
);

CREATE TABLE mouvement_stock_produit
(
    id              SERIAL PRIMARY KEY,
    id_produit      INTEGER,
    id_production   INTEGER,
    id_vente        INTEGER,
    date_mouvement  TIMESTAMP NOT NULL,
    date_peremption TIMESTAMP NOT NULl,
    quantite        DECIMAL(10, 2),

    FOREIGN KEY (id_produit) REFERENCES produit (id),
    FOREIGN KEY (id_production) REFERENCES production (id),
    FOREIGN KEY (id_vente) REFERENCES vente (id)
);

-- Table approvisionnement
CREATE TABLE approvisionnement
(
    id                     SERIAL PRIMARY KEY,
    id_detail_fournisseur  INTEGER        NOT NULL,
    id_matiere_premiere    INTEGER        NOT NULL,
    quantite               DECIMAL(10, 2) NOT NULL,
    total                  DECIMAL(10, 2) NOT NULL,
    date_approvisionnement TIMESTAMP      NOT NULL,
    date_peremption        TIMESTAMP      NOT NULL,
    reference_facture      VARCHAR(255)   NOT NULL,

    CONSTRAINT fk_approvisionnement_detail_fournisseur
        FOREIGN KEY (id_detail_fournisseur)
            REFERENCES detail_fournisseur (id),

    CONSTRAINT fk_approvisionnement_matiere_premiere
        FOREIGN KEY (id_matiere_premiere)
            REFERENCES matiere_premiere (id)
);

-- Table mouvement_stock
CREATE TABLE mouvement_stock
(
    id                   SERIAL PRIMARY KEY,
    id_matiere_premiere  INTEGER        NOT NULL,
    id_approvisionnement INTEGER,
    id_production        INTEGER,
    date_mouvement_stock TIMESTAMP      NOT NULL,
    quantite             DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (id_production) REFERENCES production (id),
    FOREIGN KEY (id_approvisionnement) REFERENCES approvisionnement (id),

    CONSTRAINT fk_mouvement_stock_matiere_premiere
        FOREIGN KEY (id_matiere_premiere)
            REFERENCES matiere_premiere (id)
);

CREATE TABLE electricite
(
    id            SERIAL PRIMARY KEY,
    id_unite      INTEGER        NOT NULL,
    prix_unitaire DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (id_unite) REFERENCES unite (id)
);

-- Table machine
CREATE TABLE machine
(
    id            SERIAL PRIMARY KEY,
    nom           VARCHAR(255)   NOT NULL,
    marque        VARCHAR(255)   NULL,
    puissance     DECIMAL(10, 2) NOT NULL,
    taux_activite DECIMAL(5, 2)  NOT NULL -- pourcentage
);

-- Table utilisation_machine
CREATE TABLE utilisation_machine
(
    id         SERIAL PRIMARY KEY,
    id_machine INTEGER        NOT NULL,
    id_produit INTEGER,
    duree      DECIMAL(10, 2) NOT NULL,
    id_unite   INTEGER,

    CONSTRAINT fk_utilisation_machine_machine
        FOREIGN KEY (id_machine)
            REFERENCES machine (id),

    FOREIGN KEY (id_produit)
        REFERENCES produit (id),

    FOREIGN KEY (id_unite)
        REFERENCES unite (id)
);