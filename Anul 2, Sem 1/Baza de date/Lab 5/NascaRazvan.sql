GO
CREATE FUNCTION validGM
(@idMasina INT, @idGaraj INT)
RETURNS VARCHAR(100)
AS
BEGIN

	DECLARE @eroare VARCHAR(100)
	SET @eroare = ''
	
	IF(NOT(EXISTS (SELECT idMasina FROM Masina WHERE idMasina = @idMasina)))
		SET @eroare = 'NU exista id-ul masinii!!'

	IF(NOT(EXISTS (SELECT idGaraj FROM Garaj WHERE idGaraj = @idGaraj)))
		SET @eroare =  'Nu exista id-ul garajului!!'

	IF(EXISTS (SELECT * FROM GarajMasina WHERE idGaraj = @idGaraj AND idMasina = @idMasina))
		SET @eroare =  'Perechea exista deja!!'


	RETURN @eroare

END

SELECT * FROM GarajMasina
SELECT * FROM Garaj
SELECT * FROM Masina
INSERT INTO GarajMasina (idMasina, idGaraj) VALUES (1, 2)
SELECT dbo.validGM(1,2)
DROP FUNCTION dbo.validGM



GO 
CREATE PROCEDURE CRUD_GarajMasina
(@idGaraj INT, @idMasina INT)
AS
BEGIN
	
	DECLARE @eroare VARCHAR(50)
	SET @eroare = dbo.validGM(@idMasina, @idGaraj)

	IF (@eroare != '')
	BEGIN 
		PRINT @eroare
		RETURN
	END

	--- CREATE
	INSERT INTO GarajMasina(idMasina, idGaraj) VALUES (@idMasina, @idGaraj)

	--- READ
	SELECT * FROM GarajMasina

	--- UPDATE
	----

	--- DELETE
	DELETE FROM GarajMasina WHERE idMasina = @idMasina AND idGaraj = @idGaraj
	SELECT * FROM GarajMasina

END

DROP PROCEDURE CRUD_GarajMasina

GO
EXECUTE CRUD_GarajMasina 200, 2
EXECUTE CRUD_GarajMasina 1000, 2
EXECUTE CRUD_GarajMasina 1, 20
EXECUTE CRUD_GarajMasina 1, 1


GO
CREATE FUNCTION validM
(@idMasina INT, @idCaserie INT, @idMotor INT, @idMarca INT)
RETURNS VARCHAR(100)
AS
BEGIN

	DECLARE @eroare VARCHAR(100)
	SET @eroare = ''
	
	IF(NOT(EXISTS (SELECT idCaserie FROM Caserie WHERE idCaserie = @idCaserie)))
		SET @eroare = 'NU exista id-ul caroseriei!!'

	IF(NOT(EXISTS (SELECT idMotor FROM Motor WHERE idMotor = @idMotor)))
		SET @eroare =  'Nu exista id-ul motorului!!'

	IF(NOT(EXISTS (SELECT idMarca FROM Marca WHERE idMarca = @idMarca)))
		SET @eroare =  'Nu exista id-ul marcii!!'

	IF(EXISTS (SELECT * FROM Masina WHERE idMasina = @idMasina))
		SET @eroare =  'Masina exista deja!!'


	RETURN @eroare

END

GO
SELECT * FROM Masina
SELECT * FROM Caserie
SELECT * FROM Motor
SELECT * FROM Marca
SELECT dbo.validM(1,1,1,1)
SELECT dbo.validM(10,10,1,1)
SELECT dbo.validM(10,10,10,1)
SELECT dbo.validM(10,10,10,10)
SELECT dbo.validM(10,1,1,1)
DROP FUNCTION dbo.validM

GO 
CREATE PROCEDURE CRUD_Masina
(@idMasina INT, @idCaserie INT, @idMotor INT, @idMarca INT)
AS
BEGIN
	
	DECLARE @eroare VARCHAR(50)
	SET @eroare = dbo.validM(@idMasina, @idCaserie, @idMotor, @idMarca)

	IF (@eroare != '')
	BEGIN 
		PRINT @eroare
		RETURN
	END

	--- CREATE
	INSERT INTO Masina(idMasina, idCaserie, idMotor, idMarca) VALUES (@idMasina, @idCaserie, @idMotor, @idMarca)

	--- READ
	SELECT * FROM Masina

	--- UPDATE
	UPDATE Masina SET idMotor = (SELECT MAX(idMotor) FROM Motor) WHERE idMasina = @idMasina
	SELECT * FROM Masina

	--- DELETE
	DELETE FROM Masina WHERE idMasina = @idMasina
	SELECT * FROM Masina

END

GO
DROP PROCEDURE CRUD_Masina
EXECUTE CRUD_Masina 10,1,1,1


GO 
CREATE FUNCTION validG
(@idGaraj INT, @denumire VARCHAR(50), @nrGaraje INT)
RETURNS VARCHAR(100)
AS
BEGIN

	DECLARE @eroare VARCHAR(100)
	SET @eroare = ''
	
	IF(EXISTS(SELECT * FROM Garaj WHERE idGaraj = @idGaraj))
		SET @eroare = 'Garaj existent!!'

	IF(LEN(@denumire) < 1)
		SET @eroare = 'Denumire invalida'

	IF(@nrGaraje < 1 OR @nrGaraje > 200)
		SET @eroare = 'Numarul de garaje este incorect!'

	RETURN @eroare

END

GO
DROP FUNCTION dbo.validG
SELECT * FROM Garaj
SELECT dbo.validG(100, 'Garaj', 10)
SELECT dbo.validG(1000, '', 10)
SELECT dbo.validG(1000, 'Garaj', -10)
SELECT dbo.validG(1000, 'Garaj', 10)


GO 
CREATE PROCEDURE CRUD_Garaj
(@idGaraj INT, @denumire VARCHAR(50), @nrGaraje INT)
AS
BEGIN
	
	DECLARE @eroare VARCHAR(100)
	SET @eroare = dbo.validG(@idGaraj, @denumire, @nrGaraje)

	IF (@eroare != '')
	BEGIN 
		PRINT @eroare
		RETURN
	END

	--- CREATE
	INSERT INTO Garaj(idGaraj, denumire, nrGaraje) VALUES (@idGaraj, @denumire, @nrGaraje)

	--- READ
	SELECT * FROM Garaj

	--- UPDATE
	UPDATE Garaj SET nrGaraje = (@nrGaraje * 10) WHERE idGaraj = @idGaraj
	SELECT * FROM Garaj

	--- DELETE
	DELETE FROM Garaj WHERE idGaraj = @idGaraj
	SELECT * FROM Garaj

END

GO
DROP PROCEDURE CRUD_Garaj
EXECUTE CRUD_Garaj 1000, 'GRJ', 15



SELECT * FROM Garaj
SELECT * FROM Masina
SELECT * FROM GarajMasina

--- View 1: Afiseaza denumirea si numarul de garaje care sunt mai putin de 10 si id-ul motorul este mai mare decat 1
GO
CREATE VIEW V1
AS
SELECT nrGaraje, denumire
FROM Garaj g
INNER JOIN GarajMasina gm ON g.idGaraj = gm.idGaraj
INNER JOIN Masina m ON gm.idMasina = m.idMasina
WHERE m.idMotor > 1 and g.nrGaraje < 10 

DROP VIEW V1

SELECT * FROM V1

--- View 2: Afiseaza id-ul marcii masinilor din garaje care au nume ce se termina cu 1
GO 
CREATE VIEW V2
AS
SELECT idMarca
FROM Masina m
INNER JOIN GarajMasina gm ON m.idMasina = gm.idMasina
INNER JOIN Garaj g ON gm.idGaraj = g.idGaraj
WHERE g.denumire LIKE '%1'

DROP VIEW V2

SELECT * FROM V2

CREATE NONCLUSTERED INDEX index_NrGaraje ON Garaj(nrGaraje);
CREATE NONCLUSTERED INDEX index_denumire ON Garaj(denumire);
CREATE NONCLUSTERED INDEX index_idMotor ON Masina(idMotor);
CREATE NONCLUSTERED INDEX index_idMasina ON Masina(idMasina);
CREATE NONCLUSTERED INDEX index_idMG ON GarajMasina(idMasina);
CREATE NONCLUSTERED INDEX index_idGM ON GarajMasina(idGaraj);

DROP INDEX index_NrGaraje ON Garaj;
DROP INDEX index_denumire ON Garaj;
DROP INDEX index_idMotor ON Masina;
DROP INDEX index_idMasina ON Masina;
DROP INDEX index_idMG ON GarajMasina;
DROP INDEX index_idGM ON GarajMasina;