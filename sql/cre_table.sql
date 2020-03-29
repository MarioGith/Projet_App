CREATE DATABASE tst
CHARACTER SET utf8
COLLATE utf8_general_ci;
CREATE USER 'tst'@'localhost' IDENTIFIED BY 'tst';
GRANT ALL PRIVILEGES ON tst.* TO 'tst'@'localhost';
DROP TABLE tst.utilisateur;
DROP TABLE tst.evenement;
DROP TABLE tst.lien;
CREATE TABLE tst.utilisateur (
  idutilisateur int(11) NOT NULL AUTO_INCREMENT,
  nom varchar(20) NOT NULL,
  login varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  chambre varchar(10) NOT NULL,
  PRIMARY KEY (idutilisateur)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE tst.evenement (
    idevent int(11) NOT NULL AUTO_INCREMENT,
    organisateur varchar(20) NOT NULL,
    type_event varchar(20) NOT NULL,
    description varchar(200) NOT NULL,
    prix varchar(10) NOT NULL,
    PRIMARY KEY(idevent)
)
CREATE TABLE tst.lien (
    id int(11) NOT NULL AUTO_INCREMENT,
    idevent int(11) NOT NULL,
    idutilisateur int(11) NOT NULL,
    PRIMARY KEY(id)
)
