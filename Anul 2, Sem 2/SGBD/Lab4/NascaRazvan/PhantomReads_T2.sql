USE GarajMasini;

GO
CREATE PROCEDURE Phantom_Reads_T2 AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL REPEATABLE READ		-- !!!!!!!!
	BEGIN TRAN
	BEGIN TRY
		SELECT * FROM Marca
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('SELECT', 'Marca', CURRENT_TIMESTAMP)
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

EXECUTE Phantom_Reads_T2;

-- Solution:
go
CREATE PROCEDURE Phantom_Reads_T2_Solution AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
	BEGIN TRAN
	BEGIN TRY
		SELECT * FROM Marca
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('SELECT', 'Marca', CURRENT_TIMESTAMP)
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

EXECUTE Phantom_Reads_T2_Solution;