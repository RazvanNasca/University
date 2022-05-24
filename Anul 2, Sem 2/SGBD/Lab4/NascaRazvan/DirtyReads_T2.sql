USE GarajMasini;

GO
CREATE PROCEDURE Dirty_Reads_T2 AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	BEGIN TRAN
	BEGIN TRY
		SELECT * FROM Marca
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('SEELCT', 'Marca', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:10'
		SELECT * FROM Marca
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('SELECT', 'Marca', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Dirty_Reads_T2;

-- Solution:
GO
CREATE PROCEDURE Dirty_Reads_T2_Solution AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED
	BEGIN TRAN
	BEGIN TRY
		SELECT * FROM Marca
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('SEELCT', 'Marca', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:10'
		SELECT * FROM Marca
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('SELECT', 'Marca', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Dirty_Reads_T2_Solution;