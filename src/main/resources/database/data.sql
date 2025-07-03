INSERT INTO administratif(nom, mot_de_passe)
VALUES ('admin', 'admin');

INSERT INTO categorie_unite (nom, norme)
VALUES ('boisson', 'gobelet de 45cl'),
       ('consommable', 'unite'),
       ('masse', 'kg'),
       ('volume', 'L'),
       ('temps', 'j');
-- exemple cookie

INSERT INTO unite (nom, categorie_unite_id, valeur_pr_norme)
VALUES ('gobelet', 1, '1'),
       ('kg', 3, 1),
       ('L', 4, 1),
       ('sachet', 2, 1),
       ('boîte', 2, 1),
       ('g', 3, 0.001),
       ('mg', 3, 0.000001),
       ('cl', 4, 0.01),
       ('unite', 2, 1);

-- Genres
INSERT INTO genre(id, nom)
VALUES (1, 'Homme'),
       (2, 'Femme');

-- Tiers (clients et employé)
INSERT INTO tiers(nom, prenom, id_genre, contact, email, image)
VALUES ('Client', 'Un', 1, '0123456789', 'client1@mail.com', NULL),
       ('Client', 'Deux', 2, '0987654321', 'client2@mail.com', NULL),
       ('Employe', 'Test', 1, '0112233445', 'employe@mail.com', NULL);

-- Clients
INSERT INTO client(id, id_tiers, date_adhesion, date_naissance)
VALUES (1, 1, '2024-01-01', '2000-01-01'),
       (2, 2, '2024-01-02', '2001-02-02');

-- Employé
INSERT INTO employe(id, id_tiers, date_naissance, date_recrutement, date_demission, reference_cv)
VALUES (1, 3, '1990-01-01', '2023-01-01', NULL, NULL);

-- Package (obligatoire pour produit)
INSERT INTO package(id, nom, cout)
VALUES (1, 'sachet plastique', 200.0);

-- Produits
INSERT INTO produit(nom, description, stock, image, delai_peremption, id_unite, id_package)
VALUES ('Café Noir', 'Café noir classique', 100, NULL, 30, 1, 1),
       ('Cappuccino', 'Café avec lait mousseux', 50, NULL, 20, 1, 1),
       ('Expresso', 'Petit café serré', 80, NULL, 15, 1, 1),
       ('Thé Chai', 'Thé épicé', 60, NULL, 25, 1, 1),
       ('Chocolat Chaud', 'Boisson chaude au chocolat', 70, NULL, 20, 1, 1),
       ('Latte', 'Café au lait doux', 90, NULL, 18, 1, 1),
       ('Thé Vert', 'Thé vert nature', 55, NULL, 30, 1, 1),
       ('Moka', 'Café moka chocolaté', 65, NULL, 22, 1, 1),
       ('Macchiato', 'Café macchiato', 40, NULL, 15, 1, 1),
       ('Americano', 'Café allongé', 75, NULL, 20, 1, 1);

-- Prix de vente des produits
INSERT INTO prix_vente_produit(id_produit, prix_vente, date_application)
VALUES (1, 2000, '2024-01-01'),
       (2, 2500, '2024-01-01'),
       (1, 2100, '2024-06-01'), -- test: nouveau prix pour Café Noir
       (2, 2600, '2024-06-01');
-- test: nouveau prix pour Cappuccino

-- Ventes
INSERT INTO vente(date_vente, id_client, id_employe)
VALUES ('2025-06-23 10:00:00', 1, 1),
       ('2025-06-24 15:30:00', 2, 1),
       ('2025-05-15 09:00:00', 1, 1),
       ('2025-04-10 11:00:00', 2, 1),
       ('2025-03-05 14:00:00', 1, 1),
       ('2024-12-20 16:00:00', 2, 1),
       ('2024-11-18 08:30:00', 1, 1),
       ('2024-10-12 13:45:00', 2, 1),
       ('2024-07-07 17:00:00', 1, 1),
       ('2023-08-15 10:30:00', 2, 1),
       ('2023-02-20 12:00:00', 1, 1),
       ('2022-12-25 09:15:00', 2, 1),
       ('2024-06-25 09:00:00', 1, 1), -- test: nouvelle vente
       ('2024-06-25 10:00:00', 2, 1);
-- test: nouvelle vente

-- Détails des ventes (produit, quantité, prix unitaire, montant)
INSERT INTO details_vente(id_vente, id_produit, quantite, prix_unitaire, montant)
VALUES (1, 1, 2, 2000, 4000),  -- 2 Café Noir
       (1, 2, 1, 2500, 2500),  -- 1 Cappuccino
       (2, 1, 1, 2000, 2000),  -- 1 Café Noir
       (2, 2, 3, 2500, 7500),  -- 3 Cappuccino
       (3, 1, 4, 2000, 8000),
       (3, 2, 2, 2500, 5000),
       (4, 1, 3, 2000, 6000),
       (4, 2, 1, 2500, 2500),
       (5, 1, 2, 2000, 4000),
       (5, 2, 2, 2500, 5000),
       (6, 1, 1, 2000, 2000),
       (6, 2, 4, 2500, 10000),
       (7, 1, 5, 2000, 10000),
       (7, 2, 1, 2500, 2500),
       (8, 1, 2, 2000, 4000),
       (8, 2, 3, 2500, 7500),
       (9, 1, 1, 2000, 2000),
       (9, 2, 2, 2500, 5000),
       (10, 1, 3, 2000, 6000),
       (10, 2, 2, 2500, 5000),
       (11, 1, 2, 2000, 4000),
       (11, 2, 1, 2500, 2500),
       (12, 1, 1, 2000, 2000),
       (12, 2, 2, 2500, 5000),
       (13, 1, 1, 2100, 2100), -- test: vente Café Noir avec nouveau prix
       (13, 2, 2, 2600, 5200), -- test: vente Cappuccino avec nouveau prix
       (14, 1, 3, 2100, 6300), -- test: vente Café Noir avec nouveau prix
       (14, 2, 1, 2600, 2600);
-- test: vente Cappuccino avec nouveau prix

-- Données test supplémentaires pour produit
INSERT INTO produit(nom, description, stock, image, delai_peremption, id_unite, id_package)
VALUES ('Expresso', 'Petit café serré', 80, NULL, 15, 1, 1),
       ('Thé Chai', 'Thé épicé', 60, NULL, 25, 1, 1);

-- Données test pour prix_vente_produit
INSERT INTO prix_vente_produit(id_produit, prix_vente, date_application)
VALUES (3, 1800, '2024-06-01'),
       (4, 2200, '2024-06-01');

-- Données test pour ventes et détails
INSERT INTO vente(date_vente, id_client, id_employe)
VALUES ('2024-06-26 11:00:00', 1, 1);

INSERT INTO details_vente(id_vente, id_produit, quantite, prix_unitaire, montant)
VALUES (15, 3, 2, 1800, 3600), -- 2 Expresso
       (15, 4, 1, 2200, 2200);
-- 1 Thé Chai

-- Matières premières (avec unités cohérentes)
INSERT INTO matiere_premiere(nom, id_unite, id_categorie_unite_id, stock, image)
VALUES ('Café vert', 2, 3, 100, '/images/mp-cafe-vert.jpg'), -- kg
       ('Sucre', 2, 3, 50, '/images/mp-sucre.jpg'),          -- kg
       ('Lait', 3, 4, 30, '/images/mp-lait.jpg'),            -- L
       ('Chocolat', 4, 2, 40, '/images/mp-chocolat.jpg'),    -- sachet
       ('Thé vert', 4, 2, 25, '/images/mp-the-vert.jpg'),    -- sachet
       ('Crème', 3, 4, 15, '/images/mp-creme.jpg'),          -- L
       ('Miel', 3, 4, 10, '/images/mp-miel.jpg'),            -- L
       ('Cannelle', 5, 1, 8, '/images/mp-cannelle.jpg'),     -- boîte
       ('Vanille', 5, 3, 5, '/images/mp-vanille.jpg');

-- Fournisseurs
INSERT INTO fournisseur(nom, contact, frais, email)
VALUES ('Fournisseur A', '0123456789', 1000, 'fournisseurA@mail.com'),
       ('Fournisseur B', '0987654321', 1500, 'fournisseurB@mail.com');

-- Prix par fournisseur (detail_fournisseur) - cohérent avec les matières premières et unités
INSERT INTO detail_fournisseur(id_fournisseur, id_matiere_premiere, quantite, id_unite, prix, date_modification)
VALUES
    -- Café vert (kg)
    (1, 1, 50, 2, 12000, '2024-06-01'),
    (2, 1, 100, 2, 23000, '2024-06-10'),
    -- Sucre (kg)
    (1, 2, 25, 2, 4000, '2024-06-05'),
    (2, 2, 50, 2, 7900, '2024-06-12'),
    -- Lait (L)
    (1, 3, 20, 3, 9000, '2024-06-08'),
    (2, 3, 30, 3, 13500, '2024-06-15'),
    -- Chocolat (sachet)
    (1, 4, 10, 4, 2500, '2024-06-09'),
    (2, 4, 20, 4, 4800, '2024-06-16'),
    -- Thé vert (sachet)
    (1, 5, 15, 4, 1800, '2024-06-11'),
    (2, 5, 25, 4, 2950, '2024-06-18'),
    -- Crème (L)
    (1, 6, 10, 3, 3500, '2024-06-13'),
    (2, 6, 15, 3, 5100, '2024-06-20'),
    -- Miel (L)
    (1, 7, 5, 3, 4000, '2024-06-14'),
    (2, 7, 10, 3, 7900, '2024-06-21'),
    -- Cannelle (boîte)
    (1, 8, 2, 5, 1200, '2024-06-17'),
    (2, 8, 4, 5, 2300, '2024-06-22'),
    -- Vanille (boîte)
    (1, 9, 1, 5, 900, '2024-06-19'),
    (2, 9, 2, 5, 1700, '2024-06-23');

-- Données test pour historique_estimation des prix des matières premières
INSERT INTO historique_estimation(id_matiere_premiere, prix, quatite, id_unite, date_application)
VALUES (1, 12000, 50, 2, '2024-06-01'), -- Café vert, 50kg à 12000, unité kg
       (1, 12500, 60, 2, '2024-06-15'), -- Café vert, 60kg à 12500, unité kg
       (2, 4000, 25, 2, '2024-06-05'),  -- Sucre, 25kg à 4000, unité kg
       (2, 4200, 30, 2, '2024-06-20'),  -- Sucre, 30kg à 4200, unité kg
       (3, 9000, 20, 3, '2024-06-08'),  -- Lait, 20L à 9000, unité L
       (3, 9500, 25, 3, '2024-06-22'),  -- Lait, 25L à 9500, unité L
       (4, 2500, 10, 4, '2024-06-09'),  -- Chocolat, 10 sachets à 2500, unité sachet
       (5, 1800, 15, 4, '2024-06-11'),  -- Thé vert, 15 sachets à 1800, unité sachet
       (6, 3500, 10, 3, '2024-06-13'),  -- Crème, 10L à 3500, unité L
       (7, 4000, 5, 3, '2024-06-14'),   -- Miel, 5L à 4000, unité L
       (8, 1200, 2, 5, '2024-06-17'),   -- Cannelle, 2 boîtes à 1200, unité boîte
       (9, 900, 1, 5, '2024-06-19'); -- Vanille, 1 boîte à 900, unité boîte