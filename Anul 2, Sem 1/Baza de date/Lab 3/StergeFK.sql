CREATE PROCEDURE StergeFK
AS
BEGIN
	ALTER TABLE Imprumut 
	DROP CONSTRAINT fk 

	UPDATE Versiune
	SET numar = 5;  

END

EXECUTE StergeFK