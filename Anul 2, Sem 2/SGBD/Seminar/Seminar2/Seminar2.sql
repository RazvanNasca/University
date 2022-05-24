CREATE DATABASE SEMINAR2225SGBD
GO
use SEMINAR2225SGBD

go

CREATE TABLE Companii
(
	cod INT PRIMARY KEY IDENTITY,
	nume VARCHAR(30),
	tara VARCHAR(30),
	anInfintare INT,
	COO VARCHAR(30)
)

CREATE TABLE JOCURI
(
	codJoc INT PRIMARY KEY IDENTITY,
	nume VARCHAR(30),
	tip VARCHAR(30),
	anLansare INT,
	dimensiune INT,
	multiplayer BIT,
	rating INT,
	codCompanie INT FOREIGN KEY REFERENCES Companii(cod)
)

INSERT INTO Companii(nume, tara, anInfintare, COO) VALUES ('Blizzard', 'SUA', 1991, 'Bobby Kotick'), ('RIOT', 'SUA', 2006, 'Nicolo Laurent'), 
('Valve', 'SUA', 1996, 'Gaben'), ('Maxies', 'SUA', 1987, 'Jeff Brown')

INSERT INTO JOCURI(nume, tip, anLansare, dimensiune, multiplayer, rating, codCompanie) VALUES 
('The Sims', 'simulator', 2000, 10, 0, 12, 4),
('LOL', 'moba', 2009, 15, 1, 12, 2),
('Starcraft 2', 'real-time strategy', 2010, 20, 1, 12, 1),
('WOW', 'RPG', 2004, 50, 1, 12, 1),
('CSGO', 'FPS', 2013, 80, 1, 12, 3)