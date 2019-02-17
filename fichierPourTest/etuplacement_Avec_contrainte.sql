-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le :  Dim 17 fév. 2019 à 20:41
-- Version du serveur :  5.7.19
-- Version de PHP :  7.1.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `etuplacement`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `idCategorie` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`idCategorie`, `nom`) VALUES
(1, 'Annee 1'),
(2, 'Nom de la nouvelle catégorie');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

CREATE TABLE `etudiant` (
  `idEtu` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `prenom` varchar(40) NOT NULL,
  `email` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `etudiantgroupe`
--

CREATE TABLE `etudiantgroupe` (
  `idEtu` int(11) NOT NULL,
  `idGroupe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

CREATE TABLE `groupe` (
  `idGroupe` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `groupecategorie`
--

CREATE TABLE `groupecategorie` (
  `idGroupe` int(11) NOT NULL,
  `idCategorie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `particularite`
--

CREATE TABLE `particularite` (
  `idParticularite` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `PrendreEnComptePlacement` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `particulariteetudiant`
--

CREATE TABLE `particulariteetudiant` (
  `idParticularite` int(11) NOT NULL,
  `idEtu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `place`
--

CREATE TABLE `place` (
  `idPlace` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `IdTypePlace` int(1) NOT NULL,
  `i` int(11) NOT NULL,
  `Disponnible` int(1) NOT NULL,
  `j` int(11) NOT NULL,
  `idSalle` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `typeplace`
--

CREATE TABLE `typeplace` (
  `idTypePlace` int(1) NOT NULL,
  `Disponnible` int(1) NOT NULL,
  `nom` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`idCategorie`);

--
-- Index pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`idEtu`);

--
-- Index pour la table `etudiantgroupe`
--
ALTER TABLE `etudiantgroupe`
  ADD PRIMARY KEY (`idEtu`,`idGroupe`),
  ADD KEY `idGroupeE` (`idGroupe`);

--
-- Index pour la table `groupe`
--
ALTER TABLE `groupe`
  ADD PRIMARY KEY (`idGroupe`);

--
-- Index pour la table `groupecategorie`
--
ALTER TABLE `groupecategorie`
  ADD PRIMARY KEY (`idGroupe`,`idCategorie`),
  ADD KEY `idCategorieG` (`idCategorie`);

--
-- Index pour la table `particularite`
--
ALTER TABLE `particularite`
  ADD PRIMARY KEY (`idParticularite`);

--
-- Index pour la table `particulariteetudiant`
--
ALTER TABLE `particulariteetudiant`
  ADD PRIMARY KEY (`idParticularite`,`idEtu`),
  ADD KEY `idEtuP` (`idEtu`);

--
-- Index pour la table `place`
--
ALTER TABLE `place`
  ADD PRIMARY KEY (`idPlace`);

--
-- Index pour la table `typeplace`
--
ALTER TABLE `typeplace`
  ADD PRIMARY KEY (`idTypePlace`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `idCategorie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `idEtu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT pour la table `groupe`
--
ALTER TABLE `groupe`
  MODIFY `idGroupe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `particularite`
--
ALTER TABLE `particularite`
  MODIFY `idParticularite` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `place`
--
ALTER TABLE `place`
  MODIFY `idPlace` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT pour la table `typeplace`
--
ALTER TABLE `typeplace`
  MODIFY `idTypePlace` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `etudiantgroupe`
--
ALTER TABLE `etudiantgroupe`
  ADD CONSTRAINT `idEtuG` FOREIGN KEY (`idEtu`) REFERENCES `etudiant` (`idEtu`) ON DELETE CASCADE,
  ADD CONSTRAINT `idGroupeE` FOREIGN KEY (`idGroupe`) REFERENCES `groupe` (`idGroupe`) ON DELETE CASCADE;

--
-- Contraintes pour la table `groupecategorie`
--
ALTER TABLE `groupecategorie`
  ADD CONSTRAINT `idCategorieG` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`) ON DELETE CASCADE,
  ADD CONSTRAINT `idGroupeC` FOREIGN KEY (`idGroupe`) REFERENCES `groupe` (`idGroupe`) ON DELETE CASCADE;

--
-- Contraintes pour la table `particulariteetudiant`
--
ALTER TABLE `particulariteetudiant`
  ADD CONSTRAINT `idEtuP` FOREIGN KEY (`idEtu`) REFERENCES `etudiant` (`idEtu`) ON DELETE CASCADE,
  ADD CONSTRAINT `idParticulariteE` FOREIGN KEY (`idParticularite`) REFERENCES `particularite` (`idParticularite`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
