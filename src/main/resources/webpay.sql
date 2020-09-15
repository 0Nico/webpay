-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  lun. 07 sep. 2020 à 22:49
-- Version du serveur :  10.1.36-MariaDB
-- Version de PHP :  7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;


--
-- Base de données :  `webpay`
--

-- --------------------------------------------------------

--
-- Structure de la table `bankaccount`
--

CREATE TABLE `bankaccount` (
  `id` varchar(36) NOT NULL,
  `iban` varchar(32) NOT NULL,
  `bank_name` varchar(32) NOT NULL,
  `owner_id` varchar(36) NOT NULL
);

--
-- Déchargement des données de la table `bankaccount`
--

INSERT INTO `bankaccount` (`id`, `iban`, `bank_name`, `owner_id`) VALUES
('46686e14-be32-4c84-a966-bfabee828d82', 'EN11119303912-394865-830134K', 'Bank of china', '9785e36f-ae2c-4953-bd22-f4e242012cff'),
('a952e49c-54bf-4e7d-84f4-4458c7432442', 'EN584944-494857-890000-72714S', 'SuperBank', 'b9a85f0e-8e27-4203-a717-e241ed4c8fb9'),
('3333449c-54bf-4e7d-84f4-445898362442', 'Z0393JZK-494857-890000-72714S', 'SuperBank', 'b9a85f0e-8e27-4203-a717-e241ed4c8fb9'),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', 'FR1294585-945958-930283-84NF', 'Bank of america', 'bbcd4322-7c5a-445a-ae57-54c29e8d52b4'),
('e9e6f974-b304-4d61-be70-7b108f925207', 'FR8593859021-86948392-85937HG', 'Bank of america', 'e4af3ee4-db0f-49e8-b656-6f77854ebb10');

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `id` varchar(36) NOT NULL,
  `cash_amount` double NOT NULL,
  `date` datetime NOT NULL,
  `currency` varchar(10) NOT NULL,
  `description` text NOT NULL,
  `sender_user_id` varchar(36) NOT NULL,
  `beneficiary_user_id` varchar(36) NOT NULL
) ;

--
-- Déchargement des données de la table `transaction`
--

INSERT INTO `transaction` (`id`, `cash_amount`, `date`, `currency`, `description`, `sender_user_id`, `beneficiary_user_id`) VALUES
('725e6436-b38b-4062-a191-f7d4dde4131a', 50, '2020-09-01 15:20:27', 'Dollar', 'Money for your birthday', 'b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'bbcd4322-7c5a-445a-ae57-54c29e8d52b4'),
('8b2ed43b-26d8-41f9-b76d-5696489910bf', 120, '2020-09-02 20:35:05', 'Dollar', 'Thanks for the credits buddy', 'b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'e4af3ee4-db0f-49e8-b656-6f77854ebb10');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `email` varchar(32) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `first_name` varchar(32) NOT NULL,
  `role` varchar(32) NOT NULL,
  `cash_amount` double NOT NULL
) ;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `last_name`, `first_name`, `role`, `cash_amount`) VALUES
('9785e36f-ae2c-4953-bd22-f4e242012cff', 'louise.holy@mail.com', 'hskser94JLn8:', 'Holy', 'Louise', 'USER', 294),
('b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'denis.wilson@mail.com', '$2y$10$y/inWi329OXGFPAxJY0/iec.1rm3q9uBsolgLpwbBf2ulIFRbG1BK', 'Wilson', 'Denis', 'USER', 500),
('bbcd4322-7c5a-445a-ae57-54c29e8d52b4', 'clark.gaby@mail.com', 'wos87Hil*', 'Clark', 'Gaby', 'USER', 2000),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', 'luckyluc@mail.com', 'jdueLKDn872', 'Lucky', 'Luc', 'USER', 499),
('6d2232e7-ad4b-430d-9628-c584fcfd5925', 'janet.jackson@mail.com', 'OHZPFZJobdl', 'Janet', 'Jackson', 'USER', 60000.0),
('cb6432e7-a88b-430d-0128-c58a74b35925', 'webpay.facturation@mail.com', 'PPJNODZ872Jobdl', 'Webpay', 'Facturation', 'ADMIN', 0.0);

-- --------------------------------------------------------

--
-- Structure de la table `user_contacts`
--

CREATE TABLE `user_contacts` (
  `user_id` varchar(36) NOT NULL,
  `contacts_id` varchar(36) NOT NULL
); 

--
-- Déchargement des données de la table `user_contacts`
--

INSERT INTO `user_contacts` (`user_id`, `contacts_id`) VALUES
('9785e36f-ae2c-4953-bd22-f4e242012cff', 'bbcd4322-7c5a-445a-ae57-54c29e8d52b4'),
('b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'bbcd4322-7c5a-445a-ae57-54c29e8d52b4'),
('b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'e4af3ee4-db0f-49e8-b656-6f77854ebb10'),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', '9785e36f-ae2c-4953-bd22-f4e242012cff'),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', 'b9a85f0e-8e27-4203-a717-e241ed4c8fb9');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bankaccount`
--
ALTER TABLE `bankaccount`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsg5o16w88it70ytteyvnvp10h` (`owner_id`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4d8wlywu82opx1il4ryoufxmh` (`beneficiary_user_id`),
  ADD KEY `FKqwxwt3juxdqvyegh166y0ca5v` (`sender_user_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user_contacts`
--
ALTER TABLE `user_contacts`
  ADD PRIMARY KEY (`user_id`,`contacts_id`),
  ADD KEY `FKbgjq1pj3f4kamou79l7cl87ne` (`contacts_id`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bankaccount`
--
ALTER TABLE `bankaccount`
  ADD CONSTRAINT `FKsg5o16w88it70ytteyvnvp10h` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `bankaccount_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `FK4d8wlywu82opx1il4ryoufxmh` FOREIGN KEY (`beneficiary_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKqwxwt3juxdqvyegh166y0ca5v` FOREIGN KEY (`sender_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`sender_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`beneficiary_user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `user_contacts`
--
ALTER TABLE `user_contacts`
  ADD CONSTRAINT `FKbgjq1pj3f4kamou79l7cl87ne` FOREIGN KEY (`contacts_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKmo0c5ro6kunnfq71x4bcwb9eh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `user_contacts_ibfk_1` FOREIGN KEY (`contacts_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
