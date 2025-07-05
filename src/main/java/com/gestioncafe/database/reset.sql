\c postgres;
drop database gestioncafe;
create database gestioncafe;

\c gestioncafe;

CREATE TABLE administratif (
    id           SERIAL       PRIMARY KEY,
    nom          VARCHAR(50) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL
);

CREATE TABLE quotidien (
    id           SERIAL       PRIMARY KEY,
    nom          VARCHAR(50) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL
);

INSERT INTO administratif (nom, mot_de_passe) VALUES ('admin', 'blabla');
INSERT INTO quotidien (nom, mot_de_passe) VALUES ('employe', 'blabla');
