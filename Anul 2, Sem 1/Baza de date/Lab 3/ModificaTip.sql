CREATE PROCEDURE ModificaTip 
AS
BEGIN
	ALTER TABLE Model
	ALTER COLUMN an BIGINT

	UPDATE Versiune
	SET numar = 2;  

END

EXECUTE ModificaTip
