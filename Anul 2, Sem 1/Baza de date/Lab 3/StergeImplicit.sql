CREATE PROCEDURE StergeImplicit
AS
BEGIN
	ALTER TABLE Angajat
	DROP CONSTRAINT df_nume 

	UPDATE Versiune
	SET numar = 2;  

END

EXECUTE StergeImplicit