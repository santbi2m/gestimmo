--
-- Base de données :  gestimmo
--
-- Ce fichier sert à ajouter les requêtes sql d'insertion 
-- de jeux de données. 
-- --------------------------------------------------------


--
-- Contenu de la table `parametrage`
--

INSERT INTO `parametrage` (`id`, `type`, `valeur`, `description`) VALUES
(1, 'PENALITE', '100', 'Pourcentage de pénalité à appliquer lors d''une annulation tardive de réservation. Exemple 50 représente 50% de la première nuitée');

INSERT INTO `parametrage` (`id`, `type`, `valeur`, `description`) VALUES
(2, 'SEUIL_NON_PENALISABLE', '24', 'Délai limite (en heure) avant lequel l''annulation d''une réservation est totalement gratuite, et sans aucune pénalité. Ce délai représente l''écart entre la date d''annulation et la date de checkin prévue à la base.');



-- Jeu de données pour la table Adresse
insert into ADRESSE (id, adresse, complement_adresse, code_postal, ville, pays) 
	values (1, 'Avenue Antoine Becquerel', null, '33600', 'Pessac', 'France');
	
insert into ADRESSE (id, adresse, complement_adresse, code_postal, ville, pays) 
	values (2, 'rue russeil', null, '44000', 'Nantes', 'France');

-- Jeu de données pour la table  Bien
insert into BIEN (id, libelle, id_adresse) 
	values (1, 'Résidence Dabakh', 1);
	
insert into BIEN (id, libelle, id_adresse) 
	values (2, 'Malick Sy', 2);



--------------------------------------------
-- Jeu de données pour la table  APPARTEMENT
--------------------------------------------

insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) 
	values (9999, 'Résidence Kiné', 'T3', 80, 1);
	
insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) 
	values (1, 'Résidence Aicha', 'T2', 80, 2);
	
insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) 
	values (2, 'Résidence 3W', 'T3', 150, 2);
	
insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) 
	values (3, 'App Val d''Anfa', 'T4', 380, 2);
	
insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) 
	values (4, 'Résidence Aida', 'T5', 500, 2);
	
insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) 
	values (5, 'Résidence firdaws', 'T6', 800, 2);
	
insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) 
	values (6, 'Résidence le marin', 'T1', 250, 2);
	
	
-------------------------------------------
-- Jeu de données pour la table  Client	
-------------------------------------------
INSERT INTO client (id, nom, prenom, adresse_email, numero_piece, type_piece, telephone, id_adresse) VALUES
(1, 'Souané', 'Amadou', 'ama@gmail.com', '133658985668', 'Carte d''identité', '+221774586324', 1);


-------------------------------------------
-- Jeu de données pour la table  Facture	
-------------------------------------------
INSERT INTO facture (id, remise, numero_facture, date_creation, id_adresse_facturation, id_client) VALUES
(100, 5, 'AG2MGI2018F1','2018-06-01 12:00:00',1, 1);

--------------------------------------------
-- Jeu de données pour la table  Reservation
--------------------------------------------
INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(1, '2018-06-01 12:00:00', '2018-06-02 11:00:00', 'réduction de 10 euros supplémentaire avec l''accord de DG', 1, 'Enrégistrée', 435, '2018-05-02 00:00:00', NULL, 1, null);


INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(2, '2018-06-01 12:00:00', '2018-06-02 11:00:00', null, 0, 'Annulée et facturée', 415, '2018-06-05 00:00:00', NULL, 1, null);


INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(3, '2018-06-03 12:00:00', '2018-06-10 11:00:00', '15 euros supplémentaire pour notre partenanire Orange', 1, 'Confirmée', 700, '2018-06-05 00:00:00', NULL, 1, null);

INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(4, '2018-07-30 12:00:00', '2018-08-05 11:00:00', null, 1, 'Enrégistrée', 900, '2018-06-05 00:00:00', NULL, 1, null);


INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(5, '2018-08-01 12:00:00', '2018-08-02 11:00:00', null, 1, 'Enregistrée', 435, '2018-06-12 00:00:00', NULL, 1, null);


INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(6, '2019-01-01 12:00:00', '2019-02-28 11:00:00', null, 1, 'Enregistrée', 435, '2018-06-13 00:00:00', NULL, 1, null);


INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(7, '2019-01-08 12:00:00', '2019-01-10 11:00:00', null, 1, 'Enregistrée', 435, '2018-06-13 00:00:00', NULL, 1, null);

INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(8, '2019-01-09 12:00:00', '2019-01-09 17:00:00', null, 1, 'Enregistrée', 435, '2018-06-13 00:00:00', NULL, 1, null);

INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(9, '2019-01-09 12:00:00', '2019-01-11 11:00:00', null, 1, 'Enregistrée', 435, '2018-06-13 00:00:00', NULL, 1, null);

INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(10, '2019-01-15 17:00:00', '2019-01-15 22:00:00', null, 1, 'Enregistrée', 435, '2018-06-13 00:00:00', NULL, 1, null);

INSERT INTO reservation (id, date_checkin, date_checkout, note, avec_petit_dej, statut_reservation, prix, date_creation, date_annulation, id_client, id_facture) VALUES
(11, '2019-01-30 12:00:00', '2019-01-31 11:00:00', null, 1, 'Enregistrée', 435, '2018-06-13 00:00:00', NULL, 1, null);
--------------------------------------------------
-- Jeu de données pour la table  assoc_resa_appart
--------------------------------------------------
INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(9999, 1);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(9999, 2);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(9999, 3);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(9999, 4);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(9999, 5);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(1, 6);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(2, 7);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(2, 9);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(3, 8);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(4, 10);

INSERT INTO assoc_resa_appart (id_appartement, id_reservation) VALUES
(5, 11);



--INSERT INTO anomalie (id, titre, description, statut_anomalie, date_ouverture, date_traitement, id_appartement, commentaire) VALUES
--(1, 'Panne de clim', 'La climatisation de la chambre parente fait du bruit et ne refroidit plus la pièce', 'En traitement', '2018-05-11 00:00:00', '2018-05-11 00:00:00', 9999, 'Le technicien est en cours de réparation du problème'),

-- Client
insert into CLIENT (id, nom, prenom, adresse_email, numero_piece, type_piece, telephone, id_adresse) values (222, 'Gassama', 'Mamadou', 'mamadou@gmail.com', '111100AZ', 'CNI', '0011223344', 1);

--
-- Contenu de la table `taxe`
--

INSERT INTO REF_TAXE (id, tva, taxe_sejour, date_debut_validite, date_fin_valite) VALUES
(1, 20, 2, '2018-06-13 00:00:00', null);

INSERT INTO REF_TAXE (id, tva, taxe_sejour, date_debut_validite, date_fin_valite) VALUES
(2, 18, 5, '2018-01-01 00:00:00', '2018-06-12 23:59:59');

--
-- Contenu de la table `ref_remise`
--
INSERT INTO `ref_remise` (`id`, `remise`, `libelle`) VALUES
(1, 5, 'Bienvenue');

INSERT INTO `ref_remise` (`id`, `remise`, `libelle`) VALUES
(2, 15, 'Fidélité');

INSERT INTO `ref_remise` (`id`, `remise`, `libelle`) VALUES
(3, 10, 'Evènementielle');

INSERT INTO `ref_remise` (`id`, `remise`, `libelle`) VALUES
(4, 20, 'Quantitative');

--ANOMALIE
--insert into ANOMALIE (id, titre, description, status_anomalie, date_ouverture, date_traitement, id_appartement, commentaire) values (1234,'Cuvette non stable', 'Responsabilité réceptioniste', 'Déclarée', '2018-01-01 00:00:00','2018-06-01 00:00:00', 9999, 'A traiter au plus vite');

--INSERT INTO anomalie (id, titre, description, statut_anomalie, date_ouverture, date_traitement, id_appartement, commentaire) VALUES
--(1, 'Panne de clim', 'La climatisation de la chambre parente fait du bruit et ne refroidit plus la pièce', 'En traitement', '2018-05-11 00:00:00', NULL, 9999, 'Le technicien est en cours de réparation du problème');