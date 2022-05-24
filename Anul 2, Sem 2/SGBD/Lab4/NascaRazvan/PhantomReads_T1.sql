USE GarajMasini;

GO
CREATE PROCEDURE Phantom_Reads_T1 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		WAITFOR DELAY '00:00:05'
		INSERT INTO Marca(idMarca, nume, tara) VALUES (6, 'Dacia', 'Romania');
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('INSERT', 'Marca', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Phantom_Reads_T1;