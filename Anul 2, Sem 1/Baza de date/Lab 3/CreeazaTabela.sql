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