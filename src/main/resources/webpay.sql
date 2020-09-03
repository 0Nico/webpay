-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  jeu. 03 sep. 2020 à 16:02
-- Version du serveur :  10.1.36-MariaDB
-- Version de PHP :  7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `webpay`
--

-- --------------------------------------------------------

--
-- Structure de la table `bankaccount`
--

CREATE TABLE `bankaccount` (
  `id` varchar(32) NOT NULL,
  `iban` varchar(32) NOT NULL,
  `bankName` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `contact_user`
--

CREATE TABLE `contact_user` (
  `user_id` varchar(32) NOT NULL,
  `contact_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `id` varchar(32) NOT NULL,
  `cashAmount` double NOT NULL,
  `date` date NOT NULL,
  `currency` varchar(10) NOT NULL,
  `description` text NOT NULL,
  `senderUser_id` varchar(32) NOT NULL,
  `beneficiaryUser_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `email` varchar(32) NOT NULL,
  `password` varchar(50) NOT NULL,
  `lastName` varchar(32) NOT NULL,
  `firstName` varchar(32) NOT NULL,
  `cashAmount` double NOT NULL,
  `bankAccount_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bankaccount`
--
ALTER TABLE `bankaccount`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
