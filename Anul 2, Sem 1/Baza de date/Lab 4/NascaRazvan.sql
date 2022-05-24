use GarajMasini

GO
INSERT INTO TABLES(Name) VALUES ('GarajMasini');
INSERT INTO TABLES(Name) VALUES ('Garaj');
INSERT INTO TABLES(Name) VALUES ('Angajat');

GO
CREATE VIEW View1 
AS
SELECT * FROM Garaj

GO
CREATE VIEW View2
AS
SELECT nume, prenume, denumire
FROM Angajat a 
INNER JOIN Garaj g ON a.idGaraj = g.idGaraj;


GO
CREATE VIEW View3
AS
SELECT nrGaraje
FROM Garaj g
INNER JOIN GarajMasina gm ON g.idGaraj = gm.idGaraj
GROUP BY nrGaraje

GO
INSERT INTO Views(Name) VALUES ('View1');
INSERT INTO Views(Name) VALUES ('View2');
INSERT INTO Views(Name) VALUES ('View3');

DELETE FROM Views

GO
INSERT INTO Tests(Name) VALUES ('deleteTable');
INSERT INTO Tests(Name) VALUES ('insertTable');
INSERT INTO Tests(Name) VALUES ('selectView');

DELETE FROM Tests

SELECT * FROM Tests
SELECT * FROM Views

GO
INSERT INTO TestViews(TestID, ViewID) VALUES (6, 6);
INSERT INTO TestViews(TestID, ViewID) VALUES (6, 7);
INSERT INTO TestViews(TestID, ViewID) VALUES (6, 8);

SELECT * FROM Tests
SELECT * FROM Tables

--- deletes
GO
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (4, 1, 100, 1);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (4, 2, 100, 3);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (4, 3, 100, 2);

--- inserts
GO
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (5, 1, 100, 3);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (5, 2, 100, 1);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (5, 3, 100, 2);

SELECT MAX(idGaraj) FROM Garaj
SELECT * FROM GarajMasina
SELECT MAX(idGaraj) FROM Angajat

SELECT * FROM GarajMasina
SELECT * FROM Angajat
SELECT * FROM Garaj


--- delete function
GO
CREATE PROCEDURE deleteTable
(@noOfrows INT, @position INT)
AS
BEGIN
	
	DECLARE @max_idGaraj INT
	DECLARE @max_idGarajMasina INT
	DECLARE @max_idAngajat INT
	DECLARE @index INT

	SELECT @max_idGaraj = MAX(idGaraj) FROM Garaj
	SELECT @max_idGarajMasina = MAX(idGaraj) FROM GarajMasina
	SELECT @max_idAngajat = MAX(idAngajat) FROM Angajat
	SET @index = 1

	
	WHILE @index <= @noOfrows
	BEGIN

		IF @position = 1
		BEGIN
			DELETE FROM GarajMasina WHERE idGaraj = @max_idGarajMasina
			SELECT @max_idGarajMasina = MAX(idGaraj) FROM GarajMasina
		END
		ELSE 

			IF @position = 3 
			BEGIN
				DELETE FROM Angajat WHERE idAngajat = @max_idAngajat
				SELECT @max_idAngajat = MAX(idAngajat) FROM Angajat
			END
			ELSE

				IF @position = 2
				BEGIN
					DELETE FROM GarajMasina WHERE idGaraj = @max_idGaraj
					DELETE FROM Angajat WHERE idGaraj = @max_idGaraj
					DELETE FROM Garaj WHERE idGaraj = @max_idGaraj
					SELECT @max_idGaraj = MAX(idGaraj) FROM Garaj
				END

		SET @index = @index + 1
	END
END

GO 
EXEC deleteTable 4, 2
DROP PROCEDURE deleteTable

--- insert function
GO
CREATE PROCEDURE insertTable
(@noOfRow INT, @position INT)
AS
BEGIN
	
	DECLARE @max_idGaraj INT
	DECLARE @min_idMasina INT
	DECLARE @max_idAngajat INT
	DECLARE @index INT
	DECLARE @nrGaraje INT
	DECLARE @nrAngajati INT

	SELECT @max_idGaraj = MAX(idGaraj) FROM Garaj
	SELECT @min_idMasina = MIN(idMasina) FROM Masina
	SELECT @max_idAngajat = MAX(idAngajat) FROM Angajat
	SET @index = 1
	SELECT @nrGaraje = count(*) from Garaj 
	SELECT @nrAngajati = count(*) from Angajat 

	IF @nrGaraje = 0
	BEGIN 
		SET @max_idGaraj = 0
		SET @nrGaraje = 1
	END

	IF @nrAngajati = 0
	BEGIN 
		SET @max_idAngajat = 0
	END

	SET @max_idGaraj = @max_idGaraj + 1
	SET @max_idAngajat = @max_idAngajat + 1


	IF @position = 2
	BEGIN 
		
		WHILE @index <= @noOfRow
		BEGIN
			INSERT INTO Garaj(idGaraj, denumire, nrGaraje) VALUES (@max_idGaraj, 'Garaj' + CONVERT(VARCHAR(10),@nrGaraje), @nrGaraje)
			SELECT CONVERT(INT, @nrGaraje)
			SET @nrGaraje = @nrGaraje +1
			SET @index = @index + 1
			SET @max_idGaraj = @max_idGaraj + 1
		END
	END
	ELSE
		IF @position = 3
		BEGIN
			
			WHILE @index <= @noOfRow
			BEGIN
				INSERT INTO Garaj(idGaraj, denumire, nrGaraje) VALUES (@max_idGaraj, 'Garaj' + CONVERT(VARCHAR(10),@nrGaraje),  @nrGaraje)
				SELECT CONVERT(INT, @nrGaraje)
				INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii) VALUES (@max_idAngajat, @max_idGaraj, 'Nume' + CONVERT(VARCHAR(10),@max_idAngajat), 'Prenume' + CONVERT(VARCHAR(10),@max_idAngajat), GETDATE())
				SELECT CONVERT(INT, @max_idAngajat)
				SET @nrGaraje = @nrGaraje +1
				SET @index = @index + 1
				SET @max_idGaraj = @max_idGaraj + 1
				SET @max_idAngajat = @max_idAngajat + 1
			END
		END
		ELSE 
			IF @position = 1
			BEGIN

				WHILE @index <= @noOfRow
				BEGIN
					INSERT INTO Garaj(idGaraj, denumire, nrGaraje) VALUES (@max_idGaraj, 'Garaj' + CONVERT(VARCHAR(10),@nrGaraje), @nrGaraje)
					SELECT CONVERT(INT, @nrGaraje)
					INSERT INTO GarajMasina(idGaraj, idMasina) VALUES (@max_idGaraj, @min_idMasina)
					SET @nrGaraje = @nrGaraje +1
					SET @index = @index + 1
					SET @max_idGaraj = @max_idGaraj + 1
				END
			END
END

DROP PROCEDURE insertTable

SELECT * FROM GarajMasina
SELECT * FROM Angajat
SELECT * FROM Garaj

DELETE FROM Garaj
DELETE FROM GarajMasina
DELETE FROM Angajat

GO
EXEC insertTable 5, 3


---  View Tables
GO
SELECT * FROM Views

GO
CREATE PROCEDURE selectView
(@idView INT)
AS
BEGIN
	IF @idView = 6
		SELECT * FROM View1
	ELSE
		IF @idView = 7 
			SELECT * FROM View2
		else 
			IF @idView = 8
				SELECT * FROM View3
END

DROP PROCEDURE selectView

EXEC insertTable 10, 1
EXEC insertTable 10, 2
EXEC insertTable 10, 3

EXEC selectView 6
EXEC selectView 7
EXEC selectView 8

SELECT * FROM GarajMasina
SELECT * FROM Angajat
SELECT * FROM Garaj

DELETE FROM Garaj
DELETE FROM GarajMasina
DELETE FROM Angajat


--- Main
SELECT * FROM TestTables
SELECT * FROM Tests
SELECT * FROM TestViews
SELECT * FROM Views
SELECT * FROM Tables
DELETE FROM Tests WHERE TestID = 9
DELETE FROM TestViews
UPDATE Views SET ViewID = 1 WHERE ViewID = 6
INSERT INTO Tables (Name) Values ('dada'), ('dada'), ('GarajMasini'), ('Garaj'), ('Angajat')
DELETE FROM Tables WHERE TableID <= 11
DELETE FROM Views WHERE ViewID <= 8
INSERT INTO TestViews (TestID, ViewID) VALUES (6, 12), (6,13), (6,14)
--- deletes
GO
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (4, 12, 100, 1);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (4, 13, 100, 3);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (4, 14, 100, 2);

--- inserts
GO
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (5, 12, 100, 3);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (5, 13, 100, 1);
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES (5, 14, 100, 2);

GO
CREATE PROCEDURE MyMain
(@id VARCHAR(20))
AS
BEGIN
	
	DECLARE @start DATETIME
	DECLARE @inter DATETIME
	DECLARE @finish DATETIME
	DECLARE @tableID INT
	DECLARE @testID INT
	DECLARE @viewID INT
	DECLARE @noOfRows INT
	DECLARE @position INT
	DECLARE @testName VARCHAR(30)
	DECLARE @tableName VARCHAR(30)

	SET @id = CONVERT(INT, @id)
	SELECT @tableName = Name FROM Tables WHERE TableID = @id

	--- start test
	SET @start = GETDATE()

	--- delete test
	SELECT @testName = Name FROM Tests t INNER JOIN TestTables tt ON t.TestID = tt.TestID WHERE tt.TableID = @id and tt.TestID = 4
	SELECT @noOfRows = NoOfRows, @position = Position FROM TestTables tt WHERE tt.TestID = 4 and tt.TableID = @id
	EXEC @testName @noOfRows, @position

	--- insert test
	SELECT @testName = Name FROM Tests t INNER JOIN TestTables tt ON t.TestID = tt.TestID WHERE tt.TableID = @id and tt.TestID = 5
	SELECT @noOfRows = NoOfRows, @position = Position FROM TestTables tt WHERE tt.TestID = 5 and tt.TableID = @id
	EXEC @testName @noOfRows, @position

	set @inter = GETDATE()

	--- view test
	SELECT @testName = Name FROM Tests t INNER JOIN TestViews tv ON t.TestID = tv.TestID WHERE tv.ViewID = @id AND tv.TestID = 6
	exec @testName @id

	set @finish = GETDATE()
	--- fin test

	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES('Table ' + @tableName + ' ' + convert(varchar, @noOfRows) + ' modified rows', @start, @finish)
	SELECT @testID = MAX(TestRunID) FROM TestRuns

	INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES(@testID, @id, @start, @inter)
	INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES(@testID, @id, @inter, @finish)

END

GO
DROP PROCEDURE MyMain
EXEC MyMain 12

SELECT * from TestRuns
SELECT * from TestRunViews
SELECT * from TestRunTables

DELETE FROM Angajat
DELETE FROM GarajMasina
DELETE FROM Garaj

DELETE FROM TestRunTables
DELETE FROM TestRunViews
DELETE FROM TestRuns