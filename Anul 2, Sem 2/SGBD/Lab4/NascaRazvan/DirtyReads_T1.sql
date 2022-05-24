USE GarajMasini;
GO
CREATE PROCEDURE Dirty_Reads_T1 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		UPDATE Marca SET tara = 'Romania' WHERE nume = 'Tesla'
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Marca', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		ROLLBACK TRAN
		SELECT 'Transaction good!' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction bad!' AS [Message]
	END CATCH
END

EXECUTE Dirty_Reads_T1;

