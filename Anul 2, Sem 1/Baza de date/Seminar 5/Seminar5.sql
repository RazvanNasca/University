CREATE DATABASE SEMINAR5
GO
USE SEMINAR5

CREATE TABLE SpeciiDeFlori
(
	cod_f INT NOT NULL,
	denumire VARCHAR(100),
	culoare_principala VARCHAR(100),
	denumire_populara VARCHAR(100)
);


INSERT INTO SpeciiDeFlori (cod_f, denumire, culoare_principala, denumire_populara)
VALUES
(100, 'Floare de colt', 'alb', '?'),
(5, 'Trandafir', 'rosu', 'Rose'),
(-235, 'Viorele', 'mov', 'Viorea'),
(44, 'Liliac', 'mov', 'Malin');


SELECT * FROM SpeciiDeFlori

ALTER TABLE SpeciiDeFlori
ADD CONSTRAINT pk_SpeciiDeFlori PRIMARY KEY (cod_f);

INSERT INTO SpeciiDeFlori (cod_f, denumire, culoare_principala, denumire_populara)
VALUES
(-400, 'Orhideea', 'roz', 'no idea');