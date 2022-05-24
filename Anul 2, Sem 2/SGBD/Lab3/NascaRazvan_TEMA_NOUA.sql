use GarajMasini
GO

GO
CREATE FUNCTION validMasina
(@idCaserie INT, @idMotor INT, @idMarca INT)
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

	RETURN @eroare

END

GO 
CREATE FUNCTION validGaraj
(@denumire VARCHAR(50), @nrGaraje INT)
RETURNS VARCHAR(100)
AS
BEGIN

	DECLARE @eroare VARCHAR(100)
	SET @eroare = ''

	IF(LEN(@denumire) < 1)
		SET @eroare = 'Denumire invalida'

	IF(@nrGaraje < 1 OR @nrGaraje > 200)
		SET @eroare = 'Numarul de garaje este incorect!'

	RETURN @eroare

END

GO
CREATE TABLE LogTable
(
	idLog INT IDENTITY PRIMARY KEY,
	operatia VARCHAR(20),
	tabel VARCHAR(20),
	timpul DATETIME
)

GO
CREATE PROCEDURE AddGarajMasina @idCaserie INT, @idMotor INT, @idMarca INT, @denumire VARCHAR(30), @nrGaraje INT AS
BEGIN
	BEGIN TRAN
	BEGIN TRY

		DECLARE @eroare VARCHAR(100)
		SET @eroare = dbo.validMasina(@idCaserie, @idMotor, @idMarca)
		SET @eroare = dbo.validGaraj(@denumire, @nrGaraje)
		IF(@eroare <> '')
		BEGIN
			RAISERROR(@eroare, 14, 1)
		END

		DECLARE @maxMasina INT
		SELECT @maxMasina = MAX(idMasina) FROM Masina
		SET @maxMasina = @maxMasina + 1

		DECLARE @maxGaraj INT
		SELECT @maxGaraj = MAX(idGaraj) FROM Garaj
		SET @maxGaraj = @maxGaraj + 1

		INSERT INTO Masina (idMasina, idCaserie, idMotor, idMarca) VALUES (@maxMasina, @idCaserie, @idMotor, @idMarca)
		INSERT INTO Garaj (idGaraj, denumire, nrGaraje) VALUES (@maxGaraj, @denumire, @nrGaraje)
		INSERT INTO GarajMasina (idGaraj, idMasina) VALUES (@maxGaraj, @maxMasina)

		COMMIT TRAN
		SELECT 'Transaction committed'

	END TRY
	BEGIN CATCH

		ROLLBACK TRAN
		SELECT 'Transaction rollbacked'
	END CATCH

END


SELECT * FROM Caserie;
SELECT * FROM Motor;
SELECT * FROM Marca;
SELECT * FROM Masina;
SELECT * FROM Garaj;
SELECT * FROM GarajMasina

--- succes
EXEC AddGarajMasina 1, 2, 3, 'TEST', 10

--- rollaback
EXEC AddGarajMasina 10, 2, 3, 'TEST', 10
EXEC AddGarajMasina 1, 20, 3, 'TEST', 10
EXEC AddGarajMasina 1, 2, 30, 'TEST', 10
EXEC AddGarajMasina 1, 2, 3, '', 10
EXEC AddGarajMasina 1, 2, 3, 'TEST', 1000


GO
CREATE PROCEDURE AddGarajMasina2 @idCaserie INT, @idMotor INT, @idMarca INT, @denumire VARCHAR(30), @nrGaraje INT AS
BEGIN
	
	DECLARE @inserareOkMasina INT
	SET @inserareOkMasina = 0

	DECLARE @maxMasina INT
	DECLARE @maxGaraj INT
	
	BEGIN TRAN
	BEGIN TRY

		DECLARE @eroare VARCHAR(100)
		SET @eroare = dbo.validMasina(@idCaserie, @idMotor, @idMarca)
		IF(@eroare <> '')
		BEGIN
			RAISERROR(@eroare, 14, 1)
		END

		SELECT @maxMasina = MAX(idMasina) FROM Masina
		SET @maxMasina = @maxMasina + 1

		INSERT INTO Masina (idMasina, idCaserie, idMotor, idMarca) VALUES (@maxMasina, @idCaserie, @idMotor, @idMarca)
		
		COMMIT TRAN
		SELECT 'Transaction MASINA committed'
		SET @inserareOkMasina = 1

	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction MASINA rollbacked'
	END CATCH

	IF (@inserareOkMasina <> 0)
	BEGIN
		DECLARE @inserareOkGaraj INT
		SET @inserareOkGaraj = 0

		BEGIN TRAN
		BEGIN TRY

			DECLARE @eroare2 VARCHAR(100)
			SET @eroare2 = dbo.validGaraj(@denumire, @nrGaraje)
			IF(@eroare2 <> '')
			BEGIN
				RAISERROR(@eroare2, 14, 1)
			END

			SELECT @maxGaraj = MAX(idGaraj) FROM Garaj
			SET @maxGaraj = @maxGaraj + 1

			INSERT INTO Garaj (idGaraj, denumire, nrGaraje) VALUES (@maxGaraj, @denumire, @nrGaraje)
		
			COMMIT TRAN
			SELECT 'Transaction GARAJ committed'
			SET @inserareOkGaraj = 1

		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'Transaction GARAJ rollbacked'
		END CATCH

	END

	IF(@inserareOkMasina <> 0 AND @inserareOkGaraj <> 0)
	BEGIN
		
		BEGIN TRAN
		BEGIN TRY
			INSERT INTO GarajMasina (idGaraj, idMasina) VALUES (@maxGaraj, @maxMasina)

			COMMIT TRAN
			SELECT 'Transaction GarajMasina committed'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'Transaction GarajMasina rollbacked'
		END CATCH

	ENd

END

SELECT * FROM Caserie;
SELECT * FROM Motor;
SELECT * FROM Marca;
SELECT * FROM Masina;
SELECT * FROM Garaj;
SELECT * FROM GarajMasina

--- succes 
EXEC AddGarajMasina2 1, 2, 3, 'TEST', 10

--- rollback la masina
EXEC AddGarajMasina2 10, 2, 3, 'TEST', 10
EXEC AddGarajMasina2 1, 20, 3, 'TEST', 10
EXEC AddGarajMasina2 1, 2, 30, 'TEST', 10

--- rollback la garaj
EXEC AddGarajMasina2 1, 2, 3, '', 10
EXEC AddGarajMasina2 1, 2, 3, 'TEST', 1000