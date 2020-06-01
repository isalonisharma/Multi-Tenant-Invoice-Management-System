-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.18-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for case_study_db
CREATE DATABASE IF NOT EXISTS `case_study_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `case_study_db`;

-- Dumping structure for table case_study_db.audit_table
CREATE TABLE IF NOT EXISTS `audit_table` (
  `audit_table_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `payment_status` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `entry_date` datetime NOT NULL,
  PRIMARY KEY (`audit_table_id`),
  KEY `FKsiu67rntlj85vdsuskewoprve` (`invoice_id`),
  CONSTRAINT `FKsiu67rntlj85vdsuskewoprve` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.audit_table: ~0 rows (approximately)
/*!40000 ALTER TABLE `audit_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_table` ENABLE KEYS */;

-- Dumping structure for table case_study_db.client
CREATE TABLE IF NOT EXISTS `client` (
  `client_id` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `mobile_number` varchar(45) NOT NULL,
  `organization` varchar(45) NOT NULL,
  `email_id` varchar(45) NOT NULL,
  `client_is_locked` bit(1) NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.client: ~0 rows (approximately)
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;

-- Dumping structure for table case_study_db.client_organization
CREATE TABLE IF NOT EXISTS `client_organization` (
  `client_organization_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`client_organization_id`),
  KEY `client_organization_fk_1_idx` (`client_id`),
  KEY `client_organization_fk_2_idx` (`organization_id`),
  CONSTRAINT `client_organization_fk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `client_organization_fk_2` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`organization_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.client_organization: ~0 rows (approximately)
/*!40000 ALTER TABLE `client_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_organization` ENABLE KEYS */;

-- Dumping structure for table case_study_db.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.hibernate_sequence: 1 rows
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(6);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table case_study_db.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` int(11) NOT NULL,
  `date_placed` datetime NOT NULL,
  `amount` float NOT NULL,
  `client_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `invoice_is_recurring` bit(1) NOT NULL,
  `invoice_is_locked` bit(1) NOT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `idUsers_idx` (`user_id`),
  KEY `idClients_idx` (`client_id`),
  CONSTRAINT `invoice_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_fk_2` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.invoice: ~0 rows (approximately)
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;

-- Dumping structure for table case_study_db.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `invoice_item_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `item_amount_total` float NOT NULL,
  PRIMARY KEY (`invoice_item_id`),
  KEY `idInvoice_idx` (`invoice_id`),
  KEY `idItem_idx` (`item_id`),
  CONSTRAINT `invoice_item_fk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_item_fk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.invoice_item: ~0 rows (approximately)
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;

-- Dumping structure for table case_study_db.invoice_organization
CREATE TABLE IF NOT EXISTS `invoice_organization` (
  `invoice_organization_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`invoice_organization_id`),
  KEY `invoice_organization_fk_1_idx` (`invoice_id`),
  KEY `invoice_organization_fk_2_idx` (`organization_id`),
  CONSTRAINT `invoice_organization_fk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `invoice_organization_fk_2` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`organization_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.invoice_organization: ~0 rows (approximately)
/*!40000 ALTER TABLE `invoice_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_organization` ENABLE KEYS */;

-- Dumping structure for table case_study_db.item
CREATE TABLE IF NOT EXISTS `item` (
  `item_id` int(11) NOT NULL,
  `item_name` varchar(45) NOT NULL,
  `item_manufacturer` varchar(45) NOT NULL,
  `item_rate` float NOT NULL,
  `item_is_locked` varchar(45) NOT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.item: ~0 rows (approximately)
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;

-- Dumping structure for table case_study_db.item_organization
CREATE TABLE IF NOT EXISTS `item_organization` (
  `item_organization_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`item_organization_id`),
  KEY `item_organization_fk_1_idx` (`item_id`),
  KEY `item_organization_fk_2_idx` (`organization_id`),
  CONSTRAINT `item_organization_fk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_organization_fk_2` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`organization_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.item_organization: ~0 rows (approximately)
/*!40000 ALTER TABLE `item_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_organization` ENABLE KEYS */;

-- Dumping structure for table case_study_db.organization
CREATE TABLE IF NOT EXISTS `organization` (
  `organization_id` int(11) NOT NULL,
  `organization_name` varchar(45) NOT NULL,
  `organization_currency` varchar(255) NOT NULL,
  `organization_date` varchar(255) NOT NULL,
  `organization_email_id` varchar(255) NOT NULL,
  `organization_is_locked` bit(1) NOT NULL,
  `organization_logo` varchar(255) NOT NULL,
  `organization_reminder_email` bit(1) NOT NULL,
  `organization_thankyou_email` bit(1) NOT NULL,
  PRIMARY KEY (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.organization: ~0 rows (approximately)
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;

-- Dumping structure for table case_study_db.recurring_invoice
CREATE TABLE IF NOT EXISTS `recurring_invoice` (
  `recurring_invoice_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `due_date` datetime NOT NULL,
  `renew_date` datetime NOT NULL,
  `recurring_period` int(11) NOT NULL,
  `payment_status` varchar(45) NOT NULL,
  PRIMARY KEY (`recurring_invoice_id`),
  KEY `recurring_invoice_fk_1_idx` (`invoice_id`),
  CONSTRAINT `recurring_invoice_fk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.recurring_invoice: ~0 rows (approximately)
/*!40000 ALTER TABLE `recurring_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `recurring_invoice` ENABLE KEYS */;

-- Dumping structure for table case_study_db.regular_invoice
CREATE TABLE IF NOT EXISTS `regular_invoice` (
  `regular_invoice_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `payment_status` varchar(45) NOT NULL,
  `due_date` datetime NOT NULL,
  PRIMARY KEY (`regular_invoice_id`),
  KEY `regular_invoice_fk_1_idx` (`invoice_id`),
  CONSTRAINT `regular_invoice_fk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.regular_invoice: ~0 rows (approximately)
/*!40000 ALTER TABLE `regular_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `regular_invoice` ENABLE KEYS */;

-- Dumping structure for table case_study_db.role
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.role: ~3 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`role_id`, `role_name`) VALUES
	(2, 'ROLE_ORGANIZATION_ADMIN'),
	(1, 'ROLE_SUPER_ADMIN'),
	(3, 'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping structure for table case_study_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `mobile_number` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `user_is_locked` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.user: ~1 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `username`, `first_name`, `last_name`, `mobile_number`, `password`, `user_is_locked`) VALUES
	(4, 'superadmin@mtims.com', 'super', 'admin', '9999999999', 'superadmin123!@', '0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table case_study_db.user_organization
CREATE TABLE IF NOT EXISTS `user_organization` (
  `user_organization_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`user_organization_id`),
  KEY `userorgfor1_idx` (`organization_id`),
  KEY `userorgfor2_idx` (`user_id`),
  CONSTRAINT `user_organization_fk_1` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`organization_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_organization_fk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.user_organization: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_organization` ENABLE KEYS */;

-- Dumping structure for table case_study_db.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `idRole_idx` (`role_id`),
  KEY `idUser_idx` (`user_id`),
  CONSTRAINT `user_role_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_role_fk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table case_study_db.user_role: ~1 rows (approximately)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_role_id`, `user_id`, `role_id`) VALUES
	(5, 4, 1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
