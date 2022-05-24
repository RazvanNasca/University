CREATE PROCEDURE AdaugaImplicit
AS
BEGIN
	ALTER TABLE Angajat
	ADD CONSTRAINT df_nume DEFAULT 'Popescu' FOR nume 

	UPDATE Versiune
	SET numar = 3;  

END

EXECUTE AdaugaImplicit