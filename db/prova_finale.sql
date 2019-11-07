-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              10.1.40-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dump della struttura del database prova_finale
CREATE DATABASE IF NOT EXISTS `prova_finale` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `prova_finale`;

-- Dump della struttura di tabella prova_finale.bevande
CREATE TABLE IF NOT EXISTS `bevande` (
  `id` int(2) NOT NULL,
  `prezzo` double NOT NULL,
  `costo` double NOT NULL,
  `descrizione` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella prova_finale.bevande: ~15 rows (circa)
/*!40000 ALTER TABLE `bevande` DISABLE KEYS */;
INSERT INTO `bevande` (`id`, `prezzo`, `costo`, `descrizione`) VALUES
	(0, 2.5, 1, 'Vino rosso - Barbera'),
	(1, 2.5, 1, 'Vino rosso - Dolcetto'),
	(2, 5, 2, 'Vino rosso - Nebbiolo'),
	(3, 2.5, 1, 'Vino bianco - Arneis'),
	(4, 2.5, 1, 'Vino bianco - Chardonnay'),
	(5, 1, 0.5, 'Acqua Naturale'),
	(6, 1, 0.5, 'Acqua frizzante'),
	(7, 2, 1, 'Coca Cola'),
	(8, 2, 1, 'Fanta'),
	(9, 2, 1, 'Sprite'),
	(10, 2, 1, 'The pesca'),
	(11, 2, 1, 'The limone'),
	(12, 2.5, 1, 'Birra'),
	(13, 5, 2, 'Birra artigianale'),
	(14, 3, 1, 'Sangria');
/*!40000 ALTER TABLE `bevande` ENABLE KEYS */;

-- Dump della struttura di tabella prova_finale.evento
CREATE TABLE IF NOT EXISTS `evento` (
  `id` int(2) NOT NULL,
  `costo` double NOT NULL,
  `budget_medio` double NOT NULL,
  `partecipanti` int(5) NOT NULL,
  `descrizione` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella prova_finale.evento: ~7 rows (circa)
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` (`id`, `costo`, `budget_medio`, `partecipanti`, `descrizione`) VALUES
	(0, 10000, 35, 1200, 'Concerto band rock'),
	(1, 200, 22, 100, 'Evento culturale'),
	(2, 5000, 40, 600, 'Raduno motociclisti'),
	(3, 800, 20, 250, 'Spettacolo per famiglie'),
	(4, 1000, 30, 400, 'Evento enogastronomico'),
	(5, 100, 28, 300, 'Cena tematica'),
	(6, 300, 15, 200, 'Cinema all\'aperto');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;

-- Dump della struttura di tabella prova_finale.piatti
CREATE TABLE IF NOT EXISTS `piatti` (
  `id` int(3) NOT NULL,
  `costo` double NOT NULL,
  `prezzo` double NOT NULL,
  `quantita` int(3) unsigned zerofill NOT NULL,
  `tempo_prep` int(3) NOT NULL,
  `descrizione` varchar(50) NOT NULL,
  `genere` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dump dei dati della tabella prova_finale.piatti: ~26 rows (circa)
/*!40000 ALTER TABLE `piatti` DISABLE KEYS */;
INSERT INTO `piatti` (`id`, `costo`, `prezzo`, `quantita`, `tempo_prep`, `descrizione`, `genere`) VALUES
	(0, 2.5, 6, 000, 15, 'Tagliere di salumi', 'antipasto'),
	(1, 1.5, 4, 000, 15, 'Carne cruda', 'antipasto'),
	(2, 0.5, 2, 000, 5, 'Empanadas', 'antipasto'),
	(3, 0.5, 2, 000, 20, 'Patatine Fritte', 'contorno'),
	(4, 0.3, 1, 000, 15, 'Focaccia', 'antipasto'),
	(5, 2.5, 6, 150, 70, 'Panino pulled pork', 'principale'),
	(6, 2, 5, 150, 60, 'Panino porchetta', 'principale'),
	(7, 1.5, 4.5, 000, 120, 'Panino Hamburger', 'principale'),
	(8, 1.5, 4.5, 000, 120, 'Panino salsiccia', 'principale'),
	(9, 2, 5, 100, 60, 'Panino con bollito', 'principale'),
	(10, 5, 10, 000, 200, 'Grigliata di suino', 'principale'),
	(11, 6, 12, 000, 170, 'Grigliata di manzo', 'principale'),
	(12, 8, 15, 000, 200, 'Grigliata mista con patate', 'principale'),
	(13, 5, 10, 000, 100, 'Porchetta con patate', 'principale'),
	(14, 4, 8, 000, 100, 'Alette di pollo', 'principale'),
	(15, 2, 5, 000, 100, 'Ravioli del plin', 'principale'),
	(16, 2, 5, 000, 80, 'Tagliatelle al ragu', 'principale'),
	(17, 3, 6, 000, 60, 'Tagliere di robiole', 'formaggio'),
	(18, 4, 9, 000, 60, 'Antipasto misto piemontese', 'antipasto'),
	(19, 1, 2, 000, 15, 'Fagioli alla texana', 'contorno'),
	(20, 1.5, 3, 000, 15, 'Dolce della casa', 'dolce'),
	(21, 1, 3, 000, 30, 'Gelato artigianale', 'dolce'),
	(22, 5, 12, 080, 60, 'Bollito misto con bagnet', 'principale'),
	(23, 1, 2.5, 000, 25, 'Peperonata', 'contorno'),
	(24, 6, 18, 000, 150, 'Tagliata 0,80kg', 'principale'),
	(25, 1, 3, 000, 30, 'Panino salumi', 'antipasto');
/*!40000 ALTER TABLE `piatti` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
