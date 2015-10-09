-- MySQL Script generated by MySQL Workbench
-- pon, 7 wrz 2015, 14:17:54
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema persest
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `persest` ;

-- -----------------------------------------------------
-- Schema persest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `persest` DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci ;
USE `persest` ;

-- -----------------------------------------------------
-- Table `persest`.`kategorie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `persest`.`kategorie` ;

CREATE TABLE IF NOT EXISTS `persest`.`kategorie` (
  `id_kategorii` BIGINT NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(128) NOT NULL,
  `kod` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`id_kategorii`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `persest`.`produkty`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `persest`.`produkty` ;

CREATE TABLE IF NOT EXISTS `persest`.`produkty` (
  `id_produkt` BIGINT NOT NULL AUTO_INCREMENT,
  `id_kategorii` BIGINT NOT NULL,
  `nazwa` VARCHAR(255) NOT NULL,
  `jednostka` BIGINT NOT NULL,
  PRIMARY KEY (`id_produkt`),
  INDEX `fk_prod_kat_idx` (`id_kategorii` ASC),
  CONSTRAINT `fk_prod_kat`
    FOREIGN KEY (`id_kategorii`)
    REFERENCES `persest`.`kategorie` (`id_kategorii`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `persest`.`sklepy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `persest`.`sklepy` ;

CREATE TABLE IF NOT EXISTS `persest`.`sklepy` (
  `id_sklepu` BIGINT NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(80) NOT NULL,
  `adres_miasto` VARCHAR(50) NULL,
  `adres_ulica` VARCHAR(50) NULL,
  `adres_nr` VARCHAR(10) NULL,
  PRIMARY KEY (`id_sklepu`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `persest`.`jednostki_produktu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `persest`.`jednostki_produktu` ;

CREATE TABLE IF NOT EXISTS `persest`.`jednostki_produktu` (
  `id_jednostki` BIGINT NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(45) NOT NULL,
  `kod` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`id_jednostki`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `persest`.`zakupione_produkty`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `persest`.`zakupione_produkty` ;

CREATE TABLE IF NOT EXISTS `persest`.`zakupione_produkty` (
  `id_zakupu` BIGINT NOT NULL AUTO_INCREMENT,
  `id_produkt` BIGINT NOT NULL,
  `id_sklepu` BIGINT NOT NULL,
  `cena_calk` INT(11) NOT NULL,
  `cena_ulamk` SMALLINT(6) NOT NULL,
  `uwagi` VARCHAR(128) NULL,
  `data` DATE NOT NULL,
  PRIMARY KEY (`id_zakupu`),
  INDEX `fk_zakup_prod_idx` (`id_produkt` ASC),
  INDEX `fk_zakup_sklep_idx` (`id_sklepu` ASC),
  CONSTRAINT `fk_zakup_prod`
    FOREIGN KEY (`id_produkt`)
    REFERENCES `persest`.`produkty` (`id_produkt`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_zakup_sklep`
    FOREIGN KEY (`id_sklepu`)
    REFERENCES `persest`.`sklepy` (`id_sklepu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `persest`.`konsumpcja_realna`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `persest`.`konsumpcja_realna` ;

CREATE TABLE IF NOT EXISTS `persest`.`konsumpcja_realna` (
  `id_konsumpcji_realnej` BIGINT NOT NULL AUTO_INCREMENT,
  `id_zakupu` BIGINT NOT NULL,
  `data` DATE NOT NULL,
  `licznik_skonsumowanej_czesci` INT(11) NOT NULL,
  `mianownik_skonsumowanej_czesci` INT(11) NOT NULL,
  `uwagi` VARCHAR(128) NULL,
  PRIMARY KEY (`id_konsumpcji_realnej`),
  INDEX `fk_konsr_zakup_idx` (`id_zakupu` ASC),
  CONSTRAINT `fk_konsr_zakup`
    FOREIGN KEY (`id_zakupu`)
    REFERENCES `persest`.`zakupione_produkty` (`id_zakupu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `persest`.`konsumpcja_wirtualna`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `persest`.`konsumpcja_wirtualna` ;

CREATE TABLE IF NOT EXISTS `persest`.`konsumpcja_wirtualna` (
  `id_konsumpcji_wirtualnej` BIGINT NOT NULL AUTO_INCREMENT,
  `id_produkt` BIGINT NOT NULL,
  `cena_calk` INT(11) NOT NULL,
  `cena_ulamk` TINYINT(4) NOT NULL,
  `licznik_skonsumowanej_czesci` INT(11) NOT NULL,
  `mianownik_skonsumowanej_czesci` INT(11) NOT NULL,
  `data` DATE NOT NULL,
  `uwagi` VARCHAR(128) NULL,
  PRIMARY KEY (`id_konsumpcji_wirtualnej`),
  INDEX `fk_konsw_prod_idx` (`id_produkt` ASC),
  CONSTRAINT `fk_konsw_prod`
    FOREIGN KEY (`id_produkt`)
    REFERENCES `persest`.`produkty` (`id_produkt`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;