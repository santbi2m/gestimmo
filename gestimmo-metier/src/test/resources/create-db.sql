--
-- Base de données :  `gestimmo`
--

-- --------------------------------------------------------

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
  UNIQUE KEY id (id)
);


--
-- Structure de la table `appartement`
--

CREATE TABLE IF NOT EXISTS appartement (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  libelle varchar(38) NOT NULL,
  type_appart varchar(38) NOT NULL,
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
  `adresse` varchar(38) NOT NULL,
  `complement_adresse` varchar(38) DEFAULT NULL,
  `code_postal` int(5) NOT NULL,
  `ville` varchar(38) NOT NULL,
  `pays` varchar(38) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `bien_index` (`id`),
  UNIQUE KEY `id_Bien` (`id`)
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_resa1` (`id`)
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
