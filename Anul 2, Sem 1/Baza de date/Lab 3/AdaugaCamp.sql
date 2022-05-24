CREATE PROCEDURE AdaugaCamp 
AS
BEGIN
	ALTER TABLE Angajat 
	ADD varsta int

	UPDATE Versiune
	SET numar = 5;  

END

EXECUTE AdaugaCamp