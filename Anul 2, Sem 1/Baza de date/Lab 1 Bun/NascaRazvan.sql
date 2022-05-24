CREATE DATABASE GarajMasini;


go 
use GarajMasini
go

CREATE TABLE Garaj
(
	idGaraj INT PRIMARY KEY,
	denumire varchar(50),
	nrGaraje int
)

CREATE TABLE Adresa
(
	idAdresa INT FOREIGN KEY REFERENCES Garaj(idGaraj),
	judet VARCHAR(50) ,
	localitate VARCHAR(50),
	strada VARCHAR(100),
	numar INT,
	CONSTRAINT PK_Adresa PRIMARY KEY(idAdresa)
)

CREATE TABLE Angajat
(
	idAngajat int,
	CONSTRAINT PK_Angajat PRIMARY KEY(idAngajat),
	idGaraj int,
	CONSTRAINT FK_Garaj FOREIGN KEY(idGaraj) REFERENCES Garaj(idGaraj)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	nume varchar(50),
	prenume varchar(50),
	data_nasterii DATE
)


CREATE TABLE Manager
(
	idManager int,
	CONSTRAINT PK_Manager PRIMARY KEY(idManager),
	nume varchar(50),
	prenume varchar(50),
	data_inceput DATE,
	CONSTRAINT FK_Manager FOREIGN KEY(idManager) REFERENCES Garaj(idGaraj)
	ON DELETE CASCADE
	ON UPDATE CASCADE
)


CREATE TABLE Motor
(
	idMotor INT PRIMARY KEY,
	capacitate int,
	nr_cilindrii int,
	cai_putere int,
	combustibil varchar(10),
	CONSTRAINT ck_combustibil CHECK(combustibil = 'diesel' or combustibil = 'benzina' or combustibil = 'electric')
)

CREATE TABLE Caserie
(
	idCaserie INT PRIMARY KEY,
	culoare varchar,
	material varchar,
	greutate int
)

CREATE TABLE Model
(
	idModel INT PRIMARY KEY,
	idMarca int,
	nume varchar,
	an int
	CONSTRAINT FK_Marca FOREIGN KEY (idMarca) REFERENCES Marca(idMarca)
)

CREATE TABLE Marca
(
	idMarca INT PRIMARY KEY,
	nume varchar,
	tara varchar
)

CREATE TABLE Masina
(
	idMasina INT PRIMARY KEY,
	idCaserie INT FOREIGN KEY REFERENCES Caserie(idCaserie),
	idMotor INT FOREIGN KEY REFERENCES Motor(idMotor),
	idMarca INT FOREIGN KEY REFERENCES Marca(idMarca)
)

CREATE TABLE NumarInmatriculare
(
	idInmatriculare INT,
	CONSTRAINT FK_INM FOREIGN KEY (idInmatriculare) REFERENCES Masina(IdMasina),
	CONSTRAINT PK_INM PRIMARY KEY(idInmatriculare),
	judet varchar,
	numar int, 
	nume varchar,
	CONSTRAINT ck_numar CHECK(numar >= 1 and numar <=99)
)


CREATE TABLE GarajMasina
(
	idGaraj int,
	idMasina int,
	CONSTRAINT FK_GarajMasina1 FOREIGN KEY(idGaraj) REFERENCES Garaj(idGaraj),
	CONSTRAINT FK_GarajMasina2 FOREIGN KEY(idMasina) REFERENCES Masina(idMasina),
	CONSTRAINT PK_GarajMasina PRIMARY KEY(idGaraj, idMasina)
)
