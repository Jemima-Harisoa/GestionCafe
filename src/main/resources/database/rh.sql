-- Table genre
CREATE TABLE genre (
    id           SERIAL         PRIMARY KEY,
    valeur       VARCHAR(255)   NOT NULL,
    description  VARCHAR(500)   NULL
);

-- Table grade
CREATE TABLE grade (
    id       SERIAL        PRIMARY KEY,
    salaire  DECIMAL(10,2) NOT NULL,
    nom      VARCHAR(255)  NOT NULL
);

-- Table serie_bac
CREATE TABLE serie_bac (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

-- Table formation
CREATE TABLE formation (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

-- Table experience
CREATE TABLE experience (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

-- Table langue
CREATE TABLE langue (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

CREATE TABLE candidat (
    id                  SERIAL       PRIMARY KEY,
    date_candidature    DATE         NOT NULL,
    nom                 VARCHAR(255) NOT NULL,
    id_genre            INTEGER      NOT NULL,
    date_naissance      DATE         NOT NULL,
    contact             VARCHAR(20)  NOT NULL,
    image               VARCHAR(255) NULL,
    reference_cv        TEXT         NULL,
    id_grade            INTEGER      NOT NULL,

    CONSTRAINT fk_candidat_genre
        FOREIGN KEY (id_genre)
        REFERENCES genre(id),

    CONSTRAINT fk_candidat_grade
        FOREIGN KEY (id_grade)
        REFERENCES grade(id)
);

-- Table detail_candidat
CREATE TABLE detail_candidat (
    id               SERIAL  PRIMARY KEY,
    id_candidat      INTEGER NOT NULL,
    id_serie_bac     INTEGER NULL,
    id_formation     INTEGER NULL,
    id_langue        INTEGER NULL,
    id_experience    INTEGER NULL,

    CONSTRAINT fk_detail_candidat_candidat
        FOREIGN KEY (id_candidat)
        REFERENCES candidat(id),

    CONSTRAINT fk_detail_candidat_serie_bac
        FOREIGN KEY (id_serie_bac)
        REFERENCES serie_bac(id),

    CONSTRAINT fk_detail_candidat_formation
        FOREIGN KEY (id_formation)
        REFERENCES formation(id),

    CONSTRAINT fk_detail_candidat_langue
        FOREIGN KEY (id_langue)
        REFERENCES langue(id),

    CONSTRAINT fk_detail_candidat_experience
        FOREIGN KEY (id_experience)
        REFERENCES experience(id)
);

-- Table employe
CREATE TABLE employe (
    id               SERIAL          PRIMARY KEY,
    id_candidat      INTEGER         NOT NULL,
    date_recrutement DATE            NOT NULL,
    nom              VARCHAR(255)    NULL,
    id_genre         INTEGER         NULL,
    date_naissance   DATE            NULL,
    contact          VARCHAR(20)     NULL,
    image            VARCHAR(255)    NULL,
    reference_cv     VARCHAR(255)    NULL,

    CONSTRAINT fk_employe_candidat
        FOREIGN KEY (id_candidat)
        REFERENCES candidat(id),

    CONSTRAINT fk_employe_genre
        FOREIGN KEY (id_genre)
        REFERENCES genre(id)
);

CREATE TABLE statut (
    id          SERIAL        PRIMARY KEY,
    valeur      VARCHAR(255)  NOT NULL,
    description VARCHAR(500)  NULL
);

CREATE TABLE statut_employe (
    id            SERIAL       PRIMARY KEY,
    id_employe    INTEGER      NOT NULL,
    date_statut   TIMESTAMP    NOT NULL,
    id_statut     INTEGER      NOT NULL,

    CONSTRAINT fk_statut_employe_statut
        FOREIGN KEY (id_statut)
        REFERENCES statut(id),

    CONSTRAINT fk_activite_employe_employe
        FOREIGN KEY (id_employe)
        REFERENCES employe(id)
);

-- Table grade_employe
CREATE TABLE grade_employe (
    id          SERIAL      PRIMARY KEY,
    id_employe  INTEGER     NOT NULL,
    id_grade    INTEGER     NOT NULL,
    date_grade  DATE        NOT NULL,

    CONSTRAINT fk_grade_employe_employe
        FOREIGN KEY (id_employe)
        REFERENCES employe(id),

    CONSTRAINT fk_grade_employe_grade
        FOREIGN KEY (id_grade)
        REFERENCES grade(id)
);

-- Table presence_employe
CREATE TABLE presence (
    id            SERIAL      PRIMARY KEY,
    id_employe    INTEGER     NOT NULL,
    date_presence DATE        NOT NULL,
    date_arrivee  TIMESTAMP   NULL,
    est_present   BOOLEAN     NULL

    CONSTRAINT fk_presence_employe_employe
        FOREIGN KEY (id_employe)
        REFERENCES employe(id)
);

-- Table jour_ferie
CREATE TABLE jour_ferie (
    id          SERIAL       PRIMARY KEY,
    nom         VARCHAR(255) NOT NULL,
    date_ferie  DATE         NOT NULL
);

-- Table type_conge
CREATE TABLE type_conge (
    id          SERIAL         PRIMARY KEY,
    nom         VARCHAR(255)   NOT NULL,
    nb_jour     INTEGER        NOT NULL,
    obligatoire BOOLEAN        NOT NULL,
    description VARCHAR(500)   NULL
);

-- Table conge
CREATE TABLE conge (
    id            SERIAL       PRIMARY KEY,
    id_type_conge INTEGER      NOT NULL,
    date_debut    DATE         NOT NULL,
    date_fin      DATE         NOT NULL,
    duree         INTEGER      NOT NULL,
    id_employe    INTEGER      NOT NULL,

    CONSTRAINT fk_conge_type_conge
        FOREIGN KEY (id_type_conge)
        REFERENCES type_conge(id)
);

-- Table irsa
CREATE TABLE irsa (
    id               SERIAL       PRIMARY KEY,
    taux             DECIMAL(5,2) NOT NULL,
    salaire_min      DECIMAL(10,2) NOT NULL,
    salaire_max      DECIMAL(10,2) NOT NULL
);

-- Table cotisation_sociale
CREATE TABLE cotisation_sociale (
    id    SERIAL       PRIMARY KEY,
    nom   VARCHAR(255) NOT NULL,
    taux  DECIMAL(5,2) NOT NULL
);

-- Table raison_avance
CREATE TABLE raison_avance (
    id          SERIAL         PRIMARY KEY,
    valeur      VARCHAR(255)   NOT NULL,
    description VARCHAR(500)   NULL
);

-- Table avance
CREATE TABLE avance (
    id               SERIAL       PRIMARY KEY,
    id_raison_avance INTEGER      NOT NULL,
    id_employe       INTEGER      NOT NULL,
    date_avance      DATE         NOT NULL,
    montant          DECIMAL(10,2) NOT NULL,


    CONSTRAINT fk_avance_raison_avance
        FOREIGN KEY (id_raison_avance)
        REFERENCES raison_avance(id)
);

-- Table raison_commission
CREATE TABLE raison_commission (
    id          SERIAL         PRIMARY KEY,
    valeur      VARCHAR(255)   NOT NULL,
    description VARCHAR(500)   NULL
);

-- Table commission
CREATE TABLE commission (
    id                     SERIAL       PRIMARY KEY,
    id_raison_commission   INTEGER      NOT NULL,
    id_employe             INTEGER      NOT NULL,
    date_commission        DATE         NOT NULL,
    montant                DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_commission_raison_commission
        FOREIGN KEY (id_raison_commission)
        REFERENCES raison_commission(id)
);

-- Table payement employe
CREATE TABLE payement (
    id                 SERIAL        PRIMARY KEY,
    id_employe         INTEGER       NOT NULL,
    date_payement      DATE          NOT NULL,
    montant            DECIMAL(10,2) NOT NULL,
    reference_payement VARCHAR(255)  NULL,
    mois_reference     DATE          NOT NULL,

    CONSTRAINT fk_payement_employe_employe
        FOREIGN KEY (id_employe)
        REFERENCES employe(id)
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


