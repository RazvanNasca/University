CREATE PROCEDURE StergeTabela 
AS
BEGIN
	DROP TABLE Imprumut

	UPDATE Versiune
	SET numar = 3;  

END

EXECUTE StergeTabela