CREATE PROCEDURE AdaugaFK
AS
BEGIN
	ALTER TABLE Imprumut 
	ADD CONSTRAINT fk FOREIGN KEY(idMasina) REFERENCES Masina(idMasina)

	UPDATE Versiune
	SET numar = 6;  

END

EXECUTE AdaugaFK