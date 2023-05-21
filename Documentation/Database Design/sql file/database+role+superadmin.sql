-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.18-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             12.2.0.6576
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for case_study_db
CREATE DATABASE IF NOT EXISTS `case_study_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `case_study_db`;

-- Dumping structure for table case_study_db.audit
CREATE TABLE IF NOT EXISTS `audit` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `payment_status` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `entry_date` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_audit_invoice` (`invoice_id`),
  CONSTRAINT `FK_audit_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.audit: ~0 rows (approximately)
DELETE FROM `audit`;

-- Dumping structure for table case_study_db.client
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `mobile_number` varchar(45) NOT NULL,
  `organization` varchar(45) NOT NULL,
  `email_id` varchar(45) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.client: ~0 rows (approximately)
DELETE FROM `client`;

-- Dumping structure for table case_study_db.client_organization
CREATE TABLE IF NOT EXISTS `client_organization` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_client_organization_client` (`client_id`),
  KEY `FK_client_organization_organization` (`organization_id`),
  CONSTRAINT `FK_client_organization_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_client_organization_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.client_organization: ~0 rows (approximately)
DELETE FROM `client_organization`;

-- Dumping structure for table case_study_db.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.hibernate_sequence: 1 rows
DELETE FROM `hibernate_sequence`;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(13);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table case_study_db.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int(11) NOT NULL,
  `date_placed` datetime NOT NULL,
  `amount` float NOT NULL,
  `client_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_recurring` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK6y01j0975eqwmnb0gckttrbj2` (`client_id`),
  KEY `FKjunvl5maki3unqdvljk31kns3` (`user_id`),
  CONSTRAINT `FK6y01j0975eqwmnb0gckttrbj2` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `FKjunvl5maki3unqdvljk31kns3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.invoice: ~0 rows (approximately)
DELETE FROM `invoice`;

-- Dumping structure for table case_study_db.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_amount` float NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKbu6tmpd0mtgu9wrw5bj5uv09v` (`invoice_id`),
  KEY `FK4985co9rso0r7m8qsemxsf60i` (`item_id`),
  CONSTRAINT `FK4985co9rso0r7m8qsemxsf60i` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `FKbu6tmpd0mtgu9wrw5bj5uv09v` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.invoice_item: ~0 rows (approximately)
DELETE FROM `invoice_item`;

-- Dumping structure for table case_study_db.invoice_organization
CREATE TABLE IF NOT EXISTS `invoice_organization` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_invoice_organization_invoice` (`invoice_id`),
  KEY `FK_invoice_organization_organization` (`organization_id`),
  CONSTRAINT `FK_invoice_organization_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_invoice_organization_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.invoice_organization: ~0 rows (approximately)
DELETE FROM `invoice_organization`;

-- Dumping structure for table case_study_db.item
CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `manufacturer` varchar(45) NOT NULL,
  `rate` float NOT NULL,
  `is_locked` varchar(45) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.item: ~0 rows (approximately)
DELETE FROM `item`;

-- Dumping structure for table case_study_db.item_organization
CREATE TABLE IF NOT EXISTS `item_organization` (
  `id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_item_organization_item` (`item_id`),
  KEY `FK_item_organization_organization` (`organization_id`),
  CONSTRAINT `FK_item_organization_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_item_organization_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.item_organization: ~0 rows (approximately)
DELETE FROM `item_organization`;

-- Dumping structure for table case_study_db.organization
CREATE TABLE IF NOT EXISTS `organization` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `date_format` varchar(50) NOT NULL DEFAULT '',
  `email_id` varchar(255) NOT NULL,
  `is_locked` bit(1) NOT NULL DEFAULT b'0',
  `logo` varchar(255) NOT NULL,
  `reminder_email` bit(1) NOT NULL,
  `thankyou_email` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.organization: ~0 rows (approximately)
DELETE FROM `organization`;

-- Dumping structure for table case_study_db.recurring_invoice
CREATE TABLE IF NOT EXISTS `recurring_invoice` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `due_date` datetime NOT NULL,
  `renew_date` datetime NOT NULL,
  `period` int(11) NOT NULL,
  `payment_status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_recurring_invoice_invoice` (`invoice_id`),
  CONSTRAINT `FK_recurring_invoice_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.recurring_invoice: ~0 rows (approximately)
DELETE FROM `recurring_invoice`;

-- Dumping structure for table case_study_db.regular_invoice
CREATE TABLE IF NOT EXISTS `regular_invoice` (
  `id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `payment_status` varchar(45) NOT NULL,
  `due_date` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_regular_invoice_invoice` (`invoice_id`),
  CONSTRAINT `FK_regular_invoice_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.regular_invoice: ~0 rows (approximately)
DELETE FROM `regular_invoice`;

-- Dumping structure for table case_study_db.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL,
  `designation` varchar(45) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_designation_UNIQUE` (`designation`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.role: ~3 rows (approximately)
DELETE FROM `role`;
INSERT INTO `role` (`id`, `designation`) VALUES
	(2, 'ROLE_ORGANIZATION_ADMIN'),
	(1, 'ROLE_SUPER_ADMIN'),
	(3, 'ROLE_USER');

-- Dumping structure for table case_study_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `mobile_number` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `is_locked` varchar(45) NOT NULL DEFAULT '0',
  `user_is_locked` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.user: ~2 rows (approximately)
DELETE FROM `user`;
INSERT INTO `user` (`id`, `username`, `first_name`, `last_name`, `mobile_number`, `password`, `is_locked`, `user_is_locked`) VALUES
	(4, 'superadmin@mtims.com', 'super', 'admin', '9999999999', 'superadmin123!@', '0', b'0');

-- Dumping structure for table case_study_db.user_organization
CREATE TABLE IF NOT EXISTS `user_organization` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_organization_user` (`user_id`),
  KEY `FK_user_organization_organization` (`organization_id`),
  CONSTRAINT `FK_user_organization_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_organization_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.user_organization: ~0 rows (approximately)
DELETE FROM `user_organization`;

-- Dumping structure for table case_study_db.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_role_user` (`user_id`),
  KEY `FK_user_role_role` (`role_id`),
  CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.user_role: ~1 rows (approximately)
DELETE FROM `user_role`;
INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES
	(0, 4, 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
