--
-- Base de données :  `gestimmo`
--
-- Ce fichier sert à ajouter les requêtes sql d'insertion 
-- de jeux de données. 
-- --------------------------------------------------------


-- Adresse
insert into ADRESSE (id, adresse, complement_adresse, code_postal, ville, pays) values (1, 'Avenue Antoine Becquerel', null, '33600', 'Pessac', 'France');

-- Bien
insert into BIEN (id, libelle, id_adresse) values (1, 'Résidence Dabakh', 1);


-- APPARTEMENT
insert into APPARTEMENT (id, libelle, type_appart, prix, id_bien) values (9999, 'Résidence Kiné', 'T3', 80, 1);

