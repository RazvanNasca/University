CREATE PROCEDURE ModificaTipInvers 
AS
BEGIN
	ALTER TABLE Model
	ALTER COLUMN an INT

	UPDATE Versiune
	SET numar = 1;  

END

EXECUTE ModificaTipInvers
