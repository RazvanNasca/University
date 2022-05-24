CREATE PROCEDURE StergeCamp 
AS
BEGIN
	ALTER TABLE Angajat 
	DROP COLUMN varsta

	UPDATE Versiune
	SET numar = 4;  

END

EXECUTE StergeCamp