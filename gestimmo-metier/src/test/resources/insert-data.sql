--
-- Base de données :  `gestimmo`
--
-- Ce fichier sert à ajouter les requêtes sql d'insertion 
-- de jeux de données. 
-- --------------------------------------------------------



-- Ajout d'un exemple de Bien en BDD histoire de tester 
-- que la conf fonctionne.
-- A supprimer lors de l'alimentation de ce fichier.

-- Adresse
insert into ADRESSE (id, adresse, complement_adresse, code_postal, ville, pays) values (1, 'Avenue Antoine Becquerel', null, '33600', 'Pessac', 'France');
insert into BIEN (libelle, id_adresse) values ('Résidence Dabakh', 1);

