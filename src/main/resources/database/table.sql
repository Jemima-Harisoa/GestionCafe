CREATE TABLE administratif
(
    id           SERIAL PRIMARY KEY,
    nom          VARCHAR(255),
    mot_de_passe VARCHAR(255)
);

-- Table compte_bilan
CREATE TABLE compte_bilan
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table categorie_bilan
CREATE TABLE categorie_bilan
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table compte_charge_produit
CREATE TABLE compte_charge_produit
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table categorie_budget
CREATE TABLE categorie_budget
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table type_budget
CREATE TABLE type_budget
(
    id                       SERIAL PRIMARY KEY,
    id_categorie_budget      INTEGER      NOT NULL,
    id_compte_charge_produit INTEGER      NOT NULL,
    nom                      VARCHAR(255) NOT NULL,
    imposable                BOOLEAN      NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_type_budget_categorie_budget
        FOREIGN KEY (id_categorie_budget)
            REFERENCES categorie_budget (id),

    CONSTRAINT fk_type_budget_compte_charge_produit
        FOREIGN KEY (id_compte_charge_produit)
            REFERENCES compte_charge_produit (id)
);

-- Table tva
CREATE TABLE tva
(
    id     SERIAL PRIMARY KEY,
    valeur DECIMAL(5, 2) NOT NULL
);

-- Table impot_societe
CREATE TABLE impot_societe
(
    id     SERIAL PRIMARY KEY,
    valeur DECIMAL(5, 2) NOT NULL
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
    nom                VARCHAR(50),    --kg, g, l, cl, ...
    categorie_unite_id INTEGER,
    valeur_pr_norme    DECIMAL(10, 2), -- valeur par rapport au norme

    FOREIGN KEY (categorie_unite_id) REFERENCES categorie_unite (id)
);

-- Table matiere_premiere
CREATE TABLE matiere_premiere
(
    id               SERIAL PRIMARY KEY,
    nom              VARCHAR(255)   NOT NULL,
    id_unite         INTEGER        NOT NULL,
    stock            DECIMAL(10, 2) NOT NULL,
    image            VARCHAR(255)   NULL,
    delai_peremption DECIMAL(10, 2) NULL,

    CONSTRAINT fk_matiere_premiere_unite
        FOREIGN KEY (id_unite)
            REFERENCES unite (id)
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

-- Table approvisionnement
CREATE TABLE approvisionnement
(
    id                     SERIAL PRIMARY KEY,
    id_detail_fournisseur  INTEGER        NOT NULL,
    id_matiere_premiere    INTEGER        NOT NULL,
    quantite               DECIMAL(10, 2) NOT NULL,
    total                  DECIMAL(10, 2) NOT NULL,
    date_approvisionnement DATE           NOT NULL,
    reference_facture      VARCHAR(255)   NOT NULL,

    CONSTRAINT fk_approvisionnement_detail_fournisseur
        FOREIGN KEY (id_detail_fournisseur)
            REFERENCES detail_fournisseur (id),

    CONSTRAINT fk_approvisionnement_matiere_premiere
        FOREIGN KEY (id_matiere_premiere)
            REFERENCES matiere_premiere (id)
);

-- Table package
CREATE TABLE package
(
    id         SERIAL PRIMARY KEY,
    nom        VARCHAR(255)   NOT NULL,
    valeur     VARCHAR(255)   NOT NULL,
    estimation DECIMAL(10, 2) NOT NULL
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
    id_package       INTEGER        NOT NULL,

    CONSTRAINT fk_recette_package
        FOREIGN KEY (id_package)
            REFERENCES package (id)
);

CREATE TABLE pv_produit
(
    id               SERIAL PRIMARY KEY,
    id_produit       INTEGER NOT NULL,
    prix_vente       DECIMAL(10, 2),
    date_application DATE
);

-- Table recette
CREATE TABLE recette
(
    id         SERIAL PRIMARY KEY,
    id_produit INTEGER        NOT NULL,
    id_unite   INTEGER        NOT NULL,
    quantite   DECIMAL(10, 2) NOT NULL,
    estimation DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_recette_produit
        FOREIGN KEY (id_produit)
            REFERENCES produit (id),

    CONSTRAINT fk_recette_unite
        FOREIGN KEY (id_unite)
            REFERENCES unite (id)
);

-- Table detail_recette
CREATE TABLE detail_recette
(
    id                  SERIAL PRIMARY KEY,
    id_recette          INTEGER        NOT NULL,
    id_matiere_premiere INTEGER        NOT NULL,
    quantite            DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_detail_recette_recette
        FOREIGN KEY (id_recette)
            REFERENCES recette (id),

    CONSTRAINT fk_detail_recette_matiere_premiere
        FOREIGN KEY (id_matiere_premiere)
            REFERENCES matiere_premiere (id)
);

-- Table machine
CREATE TABLE machine
(
    id        SERIAL PRIMARY KEY,
    nom       VARCHAR(255)   NOT NULL,
    marque    VARCHAR(255)   NULL,
    puissance DECIMAL(10, 2) NOT NULL
);

-- Table utilisation machine en fonction de la période
CREATE TABLE utilisation_machine
(
    id               SERIAL PRIMARY KEY,
    id_machine       INTEGER NOT NULL,
    mois_debut       INTEGER,
    mois_fin         INTEGER,
    date_application DATE,
    utilisation      DECIMAL(10, 2)
);

-- Table etat
CREATE TABLE etat
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table etat_machine
CREATE TABLE etat_machine
(
    id         SERIAL PRIMARY KEY,
    id_machine INTEGER   NOT NULL,
    id_etat    INTEGER   NOT NULL,
    date_etat  TIMESTAMP NOT NULL,

    CONSTRAINT fk_etat_machine_machine
        FOREIGN KEY (id_machine)
            REFERENCES machine (id),

    CONSTRAINT fk_etat_machine_etat
        FOREIGN KEY (id_etat)
            REFERENCES etat (id)
);

-- Table tiquet
CREATE TABLE tiquet
(
    id             SERIAL PRIMARY KEY,
    id_type_budget INTEGER      NOT NULL,
    description    VARCHAR(500) NOT NULL,
    date_creation  TIMESTAMP    NOT NULL,

    CONSTRAINT fk_tiquet_type_budget
        FOREIGN KEY (id_type_budget)
            REFERENCES type_budget (id)
);

-- Table tiquet_machine
CREATE TABLE tiquet_machine
(
    id         SERIAL PRIMARY KEY,
    id_tiquet  INTEGER NOT NULL,
    id_machine INTEGER NOT NULL,

    CONSTRAINT fk_tiquet_machine_tiquet
        FOREIGN KEY (id_tiquet)
            REFERENCES tiquet (id),

    CONSTRAINT fk_tiquet_machine_machine
        FOREIGN KEY (id_machine)
            REFERENCES machine (id)
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

-- Table employe
CREATE TABLE employe
(
    id               SERIAL PRIMARY KEY,
    id_tiers         INTEGER NOT NULL,
    date_naissance   DATE    NOT NULL,
    date_recrutement DATE    NOT NULL,
    date_demission   DATE,
    reference_cv     TEXT    NULL,

    FOREIGN KEY (id_tiers) REFERENCES tiers (id)
);

-- Table detail_employe
CREATE TABLE detail_employe
(
    id            SERIAL PRIMARY KEY,
    id_employe    INTEGER NOT NULL,
    id_serie_bac  INTEGER NULL,
    id_formation  INTEGER NULL,
    id_langue     INTEGER NULL,
    id_experience INTEGER NULL,

    CONSTRAINT fk_detail_employe_employe
        FOREIGN KEY (id_employe)
            REFERENCES employe (id),

    CONSTRAINT fk_detail_employe_serie_bac
        FOREIGN KEY (id_serie_bac)
            REFERENCES serie_bac (id),

    CONSTRAINT fk_detail_employe_formation
        FOREIGN KEY (id_formation)
            REFERENCES formation (id),

    CONSTRAINT fk_detail_employe_langue
        FOREIGN KEY (id_langue)
            REFERENCES langue (id),

    CONSTRAINT fk_detail_employe_experience
        FOREIGN KEY (id_experience)
            REFERENCES experience (id)
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
CREATE TABLE presence_employe
(
    id            SERIAL PRIMARY KEY,
    id_employe    INTEGER   NOT NULL,
    date_presence TIMESTAMP NOT NULL,

    CONSTRAINT fk_presence_employe_employe
        FOREIGN KEY (id_employe)
            REFERENCES employe (id)
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

-- Table commande
CREATE TABLE commande
(
    id       SERIAL PRIMARY KEY,
    id_vente INTEGER   NOT NULL,
    date_fin TIMESTAMP NOT NULL,

    CONSTRAINT fk_commande_vente
        FOREIGN KEY (id_vente)
            REFERENCES vente (id)
);

-- Table type_tache
CREATE TABLE type_tache
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table tache
CREATE TABLE tache
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255)   NOT NULL,
    description VARCHAR(500)   NULL,
    estimation  DECIMAL(10, 2) NOT NULL,
    id_type     INTEGER        NOT NULL,

    CONSTRAINT fk_tache_type_tache
        FOREIGN KEY (id_type)
            REFERENCES type_tache (id)
);

-- Table mouvement_stock
CREATE TABLE mouvement_stock
(
    id                   SERIAL PRIMARY KEY,
    id_matiere_premiere  INTEGER        NOT NULL,
    date_mouvement_stock TIMESTAMP      NOT NULL,
    quantite             DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_mouvement_stock_matiere_premiere
        FOREIGN KEY (id_matiere_premiere)
            REFERENCES matiere_premiere (id)
);

-- Table besoins
CREATE TABLE besoins
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL,
    date_ajout  DATE         NOT NULL
);

-- Table type_swot
CREATE TABLE type_swot
(
    id  SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

-- Table swot
CREATE TABLE swot
(
    id           SERIAL PRIMARY KEY,
    id_type_swot INTEGER      NOT NULL,
    valeur       VARCHAR(255) NOT NULL,
    description  VARCHAR(500) NULL,

    CONSTRAINT fk_swot_type_swot
        FOREIGN KEY (id_type_swot)
            REFERENCES type_swot (id)
);

-- Table statut_concurent
CREATE TABLE statut_concurent
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table concurrent
CREATE TABLE concurrent
(
    id  SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

-- Table detail_concurrent
CREATE TABLE detail_concurrent
(
    id                   SERIAL PRIMARY KEY,
    id_concurrent        INTEGER NOT NULL,
    date_modification    DATE    NOT NULL,
    id_statut_concurrent INTEGER NOT NULL,

    CONSTRAINT fk_detail_concurrent_concurrent
        FOREIGN KEY (id_concurrent)
            REFERENCES concurrent (id),

    CONSTRAINT fk_detail_concurrent_statut_concurrent
        FOREIGN KEY (id_statut_concurrent)
            REFERENCES statut_concurent (id)
);

-- Table strategie
CREATE TABLE strategie
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table strategie_concurrent
CREATE TABLE strategie_concurrent
(
    id                   SERIAL PRIMARY KEY,
    date_strategie       DATE    NOT NULL,
    id_statut_concurrent INTEGER NOT NULL,
    id_strategie         INTEGER NOT NULL,

    CONSTRAINT fk_strategie_concurrent_statut_concurrent
        FOREIGN KEY (id_statut_concurrent)
            REFERENCES statut_concurent (id),

    CONSTRAINT fk_strategie_concurrent_strategie
        FOREIGN KEY (id_strategie)
            REFERENCES strategie (id)
);

-- Table lieu
CREATE TABLE lieu
(
    id       SERIAL PRIMARY KEY,
    valeur   VARCHAR(255)   NOT NULL,
    distance DECIMAL(10, 2) NOT NULL
);

-- Table lieu_concurrent
CREATE TABLE lieu_concurrent
(
    id            SERIAL PRIMARY KEY,
    id_lieu       INTEGER NOT NULL,
    id_concurrent INTEGER NOT NULL,
    date_ajout    DATE    NOT NULL,

    CONSTRAINT fk_lieu_concurrent_lieu
        FOREIGN KEY (id_lieu)
            REFERENCES lieu (id),

    CONSTRAINT fk_lieu_concurrent_concurrent
        FOREIGN KEY (id_concurrent)
            REFERENCES concurrent (id)
);

-- Table produit_concurrent
CREATE TABLE produit_concurrent
(
    id            SERIAL PRIMARY KEY,
    nom           VARCHAR(255)   NOT NULL,
    id_produit    INTEGER        NULL,
    prix          DECIMAL(10, 2) NOT NULL,
    date_ajout    DATE           NOT NULL,
    id_concurrent INTEGER        NOT NULL,

    CONSTRAINT fk_produit_concurrent_concurrent
        FOREIGN KEY (id_concurrent)
            REFERENCES concurrent (id),

    CONSTRAINT fk_produit_concurrent_produit
        FOREIGN KEY (id_produit)
            REFERENCES produit (id)
);

-- Table type_action
CREATE TABLE type_action
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL
);

-- Table action
CREATE TABLE action
(
    id             SERIAL PRIMARY KEY,
    id_type_action INTEGER        NOT NULL,
    cout           DECIMAL(10, 2) NOT NULL,
    nom            VARCHAR(255)   NOT NULL,
    date_action    DATE           NOT NULL,

    CONSTRAINT fk_action_type_action
        FOREIGN KEY (id_type_action)
            REFERENCES type_action (id)
);

-- Table reaction
CREATE TABLE reaction
(
    id            SERIAL PRIMARY KEY,
    date_reaction DATE    NOT NULL,
    id_action     INTEGER NOT NULL,
    id_client     INTEGER NOT NULL,

    CONSTRAINT fk_reaction_action
        FOREIGN KEY (id_action)
            REFERENCES action (id),

    CONSTRAINT fk_reaction_client
        FOREIGN KEY (id_client)
            REFERENCES client (id)
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

-- Table jour_ferie
CREATE TABLE jour_ferie
(
    id         SERIAL PRIMARY KEY,
    nom        VARCHAR(255) NOT NULL,
    date_ferie DATE         NOT NULL,
    paye       BOOLEAN      NOT NULL DEFAULT FALSE
);

-- Table type_conge
CREATE TABLE type_conge
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL,
    paye        BOOLEAN      NOT NULL DEFAULT FALSE
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

-- Table raison_avance
CREATE TABLE raison_avance
(
    id          SERIAL PRIMARY KEY,
    valeur      VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL,
    paye        BOOLEAN      NOT NULL DEFAULT FALSE
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
    date_commssion       DATE           NOT NULL,
    montant              DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_commission_raison_commission
        FOREIGN KEY (id_raison_commission)
            REFERENCES raison_commission (id)
);