-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  jeu. 03 sep. 2020 à 17:35
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
  `id` varchar(36) NOT NULL,
  `iban` varchar(32) NOT NULL,
  `bank_name` varchar(32) NOT NULL,
  `owner_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `bankaccount`
--

INSERT INTO `bankaccount` (`id`, `iban`, `bank_name`, `owner_id`) VALUES
('46686e14-be32-4c84-a966-bfabee828d82', 'EN11119303912-394865-830134K', 'Bank of china', '9785e36f-ae2c-4953-bd22-f4e242012cff'),
('a952e49c-54bf-4e7d-84f4-4458c7432442', 'EN584944-494857-890000-72714S', 'SuperBank', 'b9a85f0e-8e27-4203-a717-e241ed4c8fb9'),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', 'FR1294585-945958-930283-84NF', 'Bank of america', 'bbcd4322-7c5a-445a-ae57-54c29e8d52b4'),
('e9e6f974-b304-4d61-be70-7b108f925207', 'FR8593859021-86948392-85937HG', 'Bank of america', 'e4af3ee4-db0f-49e8-b656-6f77854ebb10');

-- --------------------------------------------------------

--
-- Structure de la table `contact_user`
--

CREATE TABLE `user_contacts` (
  `user_id` varchar(36) NOT NULL,
  `contacts_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `contact_user`
--

INSERT INTO `user_contacts` (`user_id`, `contacts_id`) VALUES
('b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'bbcd4322-7c5a-445a-ae57-54c29e8d52b4'),
('b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'e4af3ee4-db0f-49e8-b656-6f77854ebb10'),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', '9785e36f-ae2c-4953-bd22-f4e242012cff'),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', 'b9a85f0e-8e27-4203-a717-e241ed4c8fb9'),
('9785e36f-ae2c-4953-bd22-f4e242012cff', 'bbcd4322-7c5a-445a-ae57-54c29e8d52b4');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `password` varchar(50) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `first_name` varchar(32) NOT NULL,
  `role` varchar(32) NOT NULL,
  `cash_amount` double NOT NULL,
  `bank_account_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `last_name`, `first_name`, `role`,`cash_amount`, `bank_account_id`) VALUES
('9785e36f-ae2c-4953-bd22-f4e242012cff', 'louise.holy@mail.com', 'hskser94JLn8:', 'Holy', 'Louise','USER', 294, '46686e14-be32-4c84-a966-bfabee828d82'),
('b9a85f0e-8e27-4203-a717-e241ed4c8fb9', 'denis.wilson@mail.com', 'passw0rd', 'Wilson', 'Denis', 'USER',500, 'a952e49c-54bf-4e7d-84f4-4458c7432442'),
('bbcd4322-7c5a-445a-ae57-54c29e8d52b4', 'clark.gaby@mail.com', 'wos87Hil*', 'Clark', 'Gaby', 'USER',2000, 'e4af3ee4-db0f-49e8-b656-6f77854ebb10'),
('e4af3ee4-db0f-49e8-b656-6f77854ebb10', 'luckyluc@mail.com', 'jdueLKDn872', 'Lucky', 'Luc', 'USER',499, 'e9e6f974-b304-4d61-be70-7b108f925207');

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
