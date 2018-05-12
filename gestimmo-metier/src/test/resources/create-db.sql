--
-- Base de données :  `gestimmo`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

CREATE TABLE IF NOT EXISTS `adresse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adresse` varchar(38) NOT NULL,
  `complement_adresse` varchar(38) DEFAULT NULL,
  `code_postal` int(5) NOT NULL,
  `ville` varchar(38) NOT NULL,
  `pays` varchar(38) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_adresse` (`id`));


--
-- Structure de la table `client`
--
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(38) NOT NULL,
  `prenom` varchar(38) NOT NULL,
  `adresse_email` varchar(70) NOT NULL,
  `numero_piece` varchar(38) NOT NULL,
  `type_piece` varchar(38) NOT NULL,
  `telephone` varchar(38) NOT NULL,
  `id_adresse` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_client` (`id`)
);

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE IF NOT EXISTS `facture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `remise` double NOT NULL,
  `id_adresse_facturation` bigint(20) NOT NULL,
  `id_client` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_facture` (`id`)
);


--
-- Structure de la table `anomalie`
--
CREATE TABLE IF NOT EXISTS anomalie (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  titre varchar(70) NOT NULL,
  description text NOT NULL,
  statut_anomalie varchar(38) NOT NULL,
  date_ouverture datetime NOT NULL,
  date_traitement datetime NOT NULL,
  id_appartement bigint(20) NOT NULL,
  commentaire text,
  PRIMARY KEY (id),
  UNIQUE KEY id_anomalie (id)
);


--
-- Structure de la table `appartement`
--

CREATE TABLE IF NOT EXISTS appartement (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  libelle varchar(38) NOT NULL,
  type_appart varchar(38) NOT NULL,
  prix double NOT NULL,
  id_bien bigint(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_app (id),
  UNIQUE KEY id_2 (id)
);


--
-- Structure de la table `assoc_resa_appart`
--

CREATE TABLE IF NOT EXISTS `assoc_resa_appart` (
  `id_appartement` bigint(20) NOT NULL,
  `id_reservation` bigint(20) NOT NULL
);


--
-- Structure de la table `bien`
--

CREATE TABLE IF NOT EXISTS `bien` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(38) NOT NULL,
  `id_adresse` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `bien_index` (`id`),
  UNIQUE KEY `id` (`id`)
);


--
-- Structure de la table `reservation`
--

CREATE TABLE IF NOT EXISTS `reservation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_checkin` datetime NOT NULL,
  `date_checkout` datetime NOT NULL,
  `note` text,
  `avec_petit_dej` tinyint(1) NOT NULL,
  `statut_reservation` varchar(38) NOT NULL,
  `prix` double NOT NULL,
  `date_creation` datetime NOT NULL,
  `date_annulation` datetime DEFAULT NULL,
  `id_client` bigint(20) NOT NULL,
  `id_facture` bigint(20),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_resa` (`id`)
);

--
-- Structure de la table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(70) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_role` (`id`)
);


--
-- Structure de la table `utilisateurs`
--

CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(70) NOT NULL,
  `password` varchar(70) NOT NULL,
  `nom` varchar(38) NOT NULL,
  `prenom` varchar(38) NOT NULL,
  `adresse_email` varchar(38) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `id_user` (`id`)
);

--
-- Contraintes pour les tables
--

--
-- Contraintes pour la table `anomalie`
--
ALTER TABLE `anomalie`
  ADD CONSTRAINT `anomalie_ibfk_1` FOREIGN KEY (`id_appartement`) REFERENCES `appartement` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `appartement`
--
ALTER TABLE `appartement`
  ADD CONSTRAINT `FK_ID_BIEN` FOREIGN KEY (`id_bien`) REFERENCES `bien` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `roles`
--
ALTER TABLE `roles`
  ADD CONSTRAINT `FK_USERNAME` FOREIGN KEY (`username`) REFERENCES `utilisateurs` (`username`) ON DELETE CASCADE;
  
--
-- Contraintes pour la table `assoc_resa_appart`
--
ALTER TABLE `assoc_resa_appart`
  ADD CONSTRAINT `assoc_resa_appart_ibfk_1` FOREIGN KEY (`id_appartement`) REFERENCES `appartement` (`id`) ON DELETE CASCADE;
ALTER TABLE `assoc_resa_appart`
  ADD CONSTRAINT `assoc_resa_appart_ibfk_2` FOREIGN KEY (`id_reservation`) REFERENCES `reservation` (`id`) ON DELETE CASCADE;
  
  --
-- Contraintes pour la table `bien`
--
ALTER TABLE `bien`
  ADD CONSTRAINT `bien_ibfk_1` FOREIGN KEY (`id_adresse`) REFERENCES `adresse` (`id`) ON DELETE CASCADE;
  
  --
-- Contraintes pour la table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`id_adresse`) REFERENCES `adresse` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_2` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE;
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`id_adresse_facturation`) REFERENCES `adresse` (`id`) ON DELETE CASCADE;

 --
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`id_facture`) REFERENCES `facture` (`id`) ON DELETE CASCADE;
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE
