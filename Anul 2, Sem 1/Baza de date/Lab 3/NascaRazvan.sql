--- Proceduri directe
--- Versiune 2
CREATE PROCEDURE ModificaTip 
AS
BEGIN
	ALTER TABLE Model
	ALTER COLUMN an BIGINT

	UPDATE Versiune
	SET numar = 2;  

END

EXECUTE ModificaTip


--- Versiune 3
CREATE PROCEDURE AdaugaImplicit
AS
BEGIN
	ALTER TABLE Angajat
	ADD CONSTRAINT df_nume DEFAULT 'Popescu' FOR nume 

	UPDATE Versiune
	SET numar = 3;  

END

EXECUTE AdaugaImplicit


--- Versiune 4
CREATE PROCEDURE CreeazaTabela 
AS
BEGIN
	CREATE TABLE Imprumut
	( 
		id INT PRIMARY KEY,
		nume VARCHAR(20),
		prenume VARCHAR(20),
		suma INT,
		idMasina INT
	);

	UPDATE Versiune
	SET numar = 4;  

END

EXECUTE CreeazaTabela


--- Versiune 5
CREATE PROCEDURE AdaugaCamp 
AS
BEGIN
	ALTER TABLE Angajat 
	ADD varsta int

	UPDATE Versiune
	SET numar = 5;  

END

EXECUTE AdaugaCamp


--- Versiunea 6
CREATE PROCEDURE AdaugaFK
AS
BEGIN
	ALTER TABLE Imprumut 
	ADD CONSTRAINT fk FOREIGN KEY(idMasina) REFERENCES Masina(idMasina)

	UPDATE Versiune
	SET numar = 6;  

END

EXECUTE AdaugaFK


--- Proceduri inverse
--- Versiune 1
CREATE PROCEDURE ModificaTipInvers 
AS
BEGIN
	ALTER TABLE Model
	ALTER COLUMN an INT

	UPDATE Versiune
	SET numar = 1;  

END

EXECUTE ModificaTipInvers


--- Versiune 2
CREATE PROCEDURE StergeImplicit
AS
BEGIN
	ALTER TABLE Angajat
	DROP CONSTRAINT df_nume 

	UPDATE Versiune
	SET numar = 2;  

END

EXECUTE StergeImplicit


--- Versiune 3
CREATE PROCEDURE StergeTabela 
AS
BEGIN
	DROP TABLE Imprumut

	UPDATE Versiune
	SET numar = 3;  

END

EXECUTE StergeTabela


--- Versiune 4
CREATE PROCEDURE StergeCamp 
AS
BEGIN
	ALTER TABLE Angajat 
	DROP COLUMN varsta

	UPDATE Versiune
	SET numar = 4;  

END

EXECUTE StergeCamp


--- Versiune 5
CREATE PROCEDURE StergeFK
AS
BEGIN
	ALTER TABLE Imprumut 
	DROP CONSTRAINT fk 

	UPDATE Versiune
	SET numar = 5;  

END

EXECUTE StergeFK


--- Main
CREATE PROCEDURE Main
@dateIntrare VARCHAR(10)
AS
BEGIN
	IF @dateIntrare NOT IN ('1', '2', '3', '4', '5', '6')
	BEGIN
		PRINT 'Date de intrare invalide!'
		RETURN
	END

	DECLARE @versiuneFinala INT
	SET @versiuneFinala = CONVERT(INT, @dateIntrare)
	DECLARE @versiuneCurenta INT
	SET @versiuneCurenta = (SELECT numar FROM Versiune)

	WHILE @versiuneFinala > @versiuneCurenta
	BEGIN
		IF @versiuneCurenta = 1
		BEGIN
			EXECUTE ModificaTip
			PRINT 'S-a executat ModificaTip'
		END
		ELSE
			IF @versiuneCurenta = 2
			BEGIN
				EXECUTE AdaugaImplicit
				PRINT 'S-a executat AdaugaImplicit'
			END
			ELSE
				IF @versiuneCurenta = 3
				BEGIN
					EXECUTE CreeazaTabela
					PRINT 'S-a executat CreeazaTabela'
				END
				ELSE
					IF @versiuneCurenta = 4
					BEGIN
						EXECUTE AdaugaCamp
						PRINT 'S-a executat AdaugaCamp'
					END
					ELSE
						IF @versiuneCurenta = 5
						BEGIN
							EXECUTE AdaugaFK
							PRINT 'S-a executat AdaugaFK'
						END

		SET @versiuneCurenta = (SELECT numar FROM Versiune)
		Print 'Versiunea curenta este: ' + CONVERT(varchar(10), @versiuneCurenta)
	END

	WHILE @versiuneFinala < @versiuneCurenta
	BEGIN
		IF @versiuneCurenta = 2
		BEGIN
			EXECUTE ModificaTipInvers
			PRINT 'S-a executat ModificaTipInvers'
		END
		ELSE
			IF @versiuneCurenta = 3
			BEGIN
				EXECUTE StergeImplicit
				PRINT 'S-a executat StergeImplicit'
			END
			ELSE
				IF @versiuneCurenta = 4
				BEGIN
					EXECUTE StergeTabela
					PRINT 'S-a executat StergeTabela'
				END
				ELSE
					IF @versiuneCurenta = 5
					BEGIN
						EXECUTE StergeCamp
						PRINT 'S-a executat StergeCamp'
					END
					ELSE
						IF @versiuneCurenta = 6
						BEGIN
							EXECUTE StergeFK
							PRINT 'S-a executat StergeFK'
						END

		SET @versiuneCurenta = (SELECT numar FROM Versiune)
		Print 'Versiunea curenta este: ' + CONVERT(varchar(10), @versiuneCurenta)
	END

	PRINT 'Versiunea a fost actualizata!'
END

EXECUTE Main 1

SELECT * FROM Versiune

