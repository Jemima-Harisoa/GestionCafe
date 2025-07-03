INSERT INTO genre (valeur, description) VALUES
('Homme', 'Genre masculin'),
('Femme', 'Genre feminin');

INSERT INTO grade (salaire, nom) VALUES
(800000, 'Stagiaire'),
(1200000, 'Employe junior'),
(1800000, 'Employe confirme'),
(2500000, 'Chef d equipe'),
(3500000, 'Manager'),
(5000000, 'Directeur adjoint'),
(7500000, 'Directeur');

INSERT INTO serie_bac (valeur, description) VALUES
('A1', 'Mathematiques-Physique'),
('A2', 'Mathematiques-Sciences naturelles'),
('C', 'Lettres-Langues'),
('D', 'Lettres-Sciences humaines'),
('L', 'Serie litteraire'),
('S', 'Serie scientifique'),
('Technique', 'Filières techniques');

INSERT INTO formation (valeur, description) VALUES
('BEPC', 'Brevet d etudes du premier cycle'),
('BACC', 'Baccalaureat'),
('BTS', 'Brevet de technicien superieur'),
('Licence', 'Diplôme universitaire de premier cycle'),
('Master', 'Diplôme universitaire de second cycle'),
('Doctorat', 'Diplôme universitaire de troisième cycle');

INSERT INTO experience (valeur, description) VALUES
('Debutant', 'Moins de 2 ans d experience'),
('Intermediaire', 'Entre 2 et 5 ans d experience'),
('Confirme', 'Entre 5 et 10 ans d experience'),
('Expert', 'Plus de 10 ans d experience');

INSERT INTO langue (valeur, description) VALUES
('Malagasy', 'Langue officielle de Madagascar'),
('Français', 'Langue administrative'),
('Anglais', 'Langue internationale'),
('Espagnol', NULL),
('Chinois', NULL);

INSERT INTO statut (valeur, description) VALUES
('Actif', 'Employe actuellement en activite'),
('En conge', 'Employe actuellement en conge'),
('Demission', 'Employe ayant demissionne'),
('Licenciement', 'Employe licencie');

INSERT INTO type_conge (nom, nb_jour, obligatoire, description) VALUES
('Annuel', 30, true, 'Conge annuel paye'),
('Maladie', 15, false, 'Conge pour maladie'),
('Maternite', 90, false, 'Conge maternite'),
('Paternite', 15, false, 'Conge paternite');

INSERT INTO irsa (taux, salaire_min, salaire_max) VALUES
(0, 0, 350000),
(5, 350001, 400000),
(10, 400001, 500000),
(15, 500001, 600000),
(20, 600001, 10000000);

INSERT INTO cotisation_sociale (nom, taux) VALUES
('CNaPS', 1),  -- Caisse Nationale de Prevoyance Sociale
('OSTIE', 1),  -- Organisme de Sante et de Travail Inter-Entreprises
('Mutuelle', 0.5);

INSERT INTO raison_avance (valeur, description) VALUES
('Urgence medicale', 'Avance pour frais medicaux'),
('Mariage', 'Avance pour frais de mariage'),
('Decès', 'Avance pour frais funeraires'),
('Scolarite', 'Avance pour frais de scolarite'),
('Autre', NULL);

INSERT INTO raison_commission (valeur, description) VALUES
('Performance', 'Commission pour performance exceptionnelle'),
('Ventes', 'Commission sur ventes'),
('Projet', 'Commission pour reussite de projet'),
('Prime', 'Prime exceptionnelle');

INSERT INTO candidat (date_candidature, nom, id_genre, date_naissance, contact, image, reference_cv, id_grade) VALUES
('2023-01-15', 'Rakoto Jean', 1, '1990-05-20', '0341234567', 'rakoto_jean.jpg', 'CV_Rakoto_Jean_2023.pdf', 3);

-- Insérer des candidats
INSERT INTO candidat (nom, id_genre, date_naissance, date_candidature, contact, image, reference_cv, id_grade) VALUES
('Alice Dupont', 2, '1995-05-14', '2025-06-01', '0123456789', 'candidatCv01.png', 'CV d Alice', 3),
('Bob Martin', 1, '1990-03-22', '2025-06-02', '0987654321', 'candidatCv01.png', 'CV de Bob', 4),
('Claire Petit', 2, '1988-11-30', '2025-06-03', '0147253698', 'candidatCv01.png', 'CV de Claire', 2),
('David Moreau', 1, '1992-07-19', '2025-06-04', '0165894321', 'candidatCv01.png', 'CV de David', 5),
('Eva Leroy', 2, '1994-01-10', '2025-06-05', '0176543210', 'candidatCv01.png', 'CV d Eva', 1);

-- Insérer des détails des candidats
INSERT INTO detail_candidat (id_candidat, id_serie_bac, id_formation, id_langue, id_experience) VALUES
(1, 1, 1, 1, 1),
(2, 2, 2, 2, 2),
(3, 1, 3, 1, 1),
(4, 3, 4, 2, 3),
(5, 2, 5, 1, 2);

INSERT INTO detail_candidat (id_candidat, id_serie_bac, id_formation, id_langue, id_experience) VALUES
(1, 1, 4, 1, 3),
(1, NULL, 5, 2, NULL),
(1, NULL, NULL, 3, NULL);

INSERT INTO employe (id_candidat, date_recrutement, nom, id_genre, date_naissance, contact, image, reference_cv) VALUES
(1, '2023-02-01', 'Rakoto Jean', 1, '1990-05-20', '0341234567', 'rakoto_jean.jpg', 'CV_Rakoto_Jean_2023.pdf');

INSERT INTO statut_employe (id_employe, date_statut, id_statut) VALUES
(1, '2023-02-01 08:00:00', 1);

INSERT INTO grade_employe (id_employe, id_grade, date_grade) VALUES
(1, 3, '2023-02-01');

INSERT INTO presence (id_employe, date_presence, date_arrivee, est_present) VALUES
-- Semaine 1
(1, '2023-06-01', '2023-06-01 08:05:23', true),
(1, '2023-06-02', '2023-06-02 08:12:45', true),
(1, '2023-06-03', NULL, false), -- Week-end
(1, '2023-06-04', NULL, false), -- Week-end
(1, '2023-06-05', '2023-06-05 08:01:12', true),
(1, '2023-06-06', '2023-06-06 07:58:33', true),
(1, '2023-06-07', '2023-06-07 08:15:02', true),

-- Semaine 2
(1, '2023-06-08', '2023-06-08 08:22:11', true),
(1, '2023-06-09', '2023-06-09 08:05:44', true),
(1, '2023-06-10', NULL, false),
(1, '2023-06-11', NULL, false),
(1, '2023-06-12', NULL, false), -- Absence maladie
(1, '2023-06-13', '2023-06-13 08:10:33', true),
(1, '2023-06-14', '2023-06-14 07:55:12', true),

-- Semaine 3
(1, '2023-06-15', '2023-06-15 08:00:01', true),
(1, '2023-06-16', '2023-06-16 08:07:22', true),
(1, '2023-06-17', NULL, false),
(1, '2023-06-18', NULL, false),
(1, '2023-06-19', '2023-06-19 08:12:33', true),
(1, '2023-06-20', '2023-06-20 07:59:44', true),
(1, '2023-06-21', '2023-06-21 08:05:55', true),

-- Semaine 4
(1, '2023-06-22', '2023-06-22 08:10:11', true),
(1, '2023-06-23', '2023-06-23 08:15:22', true),
(1, '2023-06-24', NULL, false),
(1, '2023-06-25', NULL, false),
(1, '2023-06-26', '2023-06-26 08:02:33', true),
(1, '2023-06-27', '2023-06-27 07:58:44', true),
(1, '2023-06-28', '2023-06-28 08:20:55', true),
(1, '2023-06-29', '2023-06-29 08:05:11', true),
(1, '2023-06-30', '2023-06-30 08:10:22', true);

INSERT INTO jour_ferie (nom, date_ferie) VALUES
('Nouvel An', '2025-01-01'),
('Martyrs', '2025-03-29'),
('Pâques', '2025-04-09'),
('Fête du Travail', '2025-05-01'),
('Independance', '2025-06-26'),
('Assomption', '2025-08-15'),
('Toussaint', '2025-11-01'),
('Noël', '2025-12-25');

INSERT INTO conge (id_type_conge, date_debut, date_fin, duree, id_employe) VALUES
(2, '2025-06-12', '2025-06-12', 2, 1);

INSERT INTO avance (id_raison_avance, id_employe, date_avance, montant) VALUES
(1, 1, '2023-03-15', 100000);

INSERT INTO commission (id_raison_commission, id_employe, date_commission, montant) VALUES
(1, 1, '2023-04-01', 150000),
(4, 1, '2023-12-15', 200000);

INSERT INTO payement (id_employe, date_payement, montant, reference_payement, mois_reference) VALUES
(1, '2023-03-05', 1800000, 'PAY202303001', '2023-02-01'),
(1, '2023-04-05', 1800000, 'PAY202304001', '2023-03-01'),
(1, '2023-05-05', 1800000, 'PAY202305001', '2023-04-01'),
(1, '2023-06-05', 1800000, 'PAY202306001', '2023-05-01'),
(1, '2023-07-05', 2500000, 'PAY202307001', '2023-06-01'),
(1, '2023-08-05', 2500000, 'PAY202308001', '2023-07-01'),
(1, '2023-09-05', 2500000, 'PAY202309001', '2023-08-01');

