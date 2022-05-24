USE GarajMasini;

GO
CREATE PROCEDURE Non_Repeatable_Reads_T1 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		WAITFOR DELAY '00:00:05'
		UPDATE Marca SET tara = 'AMERICA' WHERE tara = 'SUA'
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Marca', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Non_Repeatable_Reads_T1;