Create Database Seminar6;
go
use Seminar6;


Create table Tipuri_tren
(
idTip int primary key identity,
descriere varchar(20)
);


create table Trenuri
(
idTren int primary key identity,
nume varchar(20),
idTip int foreign key references Tipuri_tren(idTip)
);


create table Rute
(
idR int primary key identity,
nume varchar(20),
idTren int foreign key references Trenuri(idTren),
);



create table Statii
(
idS int primary key identity,
nume varchar(20)
);



create table Rute_statii
(
idR int foreign key references Rute(idR),
idS int foreign key references Statii(ids),
ora_sosirii time,
ora_plecarii time,
constraint pk_rute_statii primary key (idR, idS)
);



CREATE PROCEDURE InsertTren
(
	
)


--- Cerinta 7
GO

SELECT * FROM Statii
SELECT * FROM Rute_statii
SELECT * FROM Rute

EXEC InsertRuta 'Cluj-Mangalia', 1
EXEC UpsertStatii 1002, 3, '14:22', '14:35'
EXEC UpsertStatii 1002, 1, '4:02', '4:05'
EXEC UpsertStatii 1002, 2, '10:12', '12:10'
EXEC UpsertStatii 1002, 5, '18:36', '18:45'
EXEC UpsertStatii 1002, 3, '14:22', '14:35'
EXEC UpsertStatii 1002, 6, '23:12', '23:40'
EXEC UpsertStatii 1002, 7, '08:04', '08:35'
EXEC UpsertStatii 1002, 4, '18:04', '19:35'

GO 
CREATE VIEW NumeRute
AS
SELECT nume FROM Rute
INNER JOIN  Rute_statii ON Rute.idR = Rute_statii.idR
GROUP BY Rute.idR, Rute.nume 
HAVING COUNT(*) = (SELECT COUNT (*) FROM STATII)

GO 
SELECT * FROM NumeRute