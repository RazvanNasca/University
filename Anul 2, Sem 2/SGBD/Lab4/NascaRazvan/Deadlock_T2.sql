USE GarajMasini;

GO
CREATE PROCEDURE Deadlock_T2 AS
BEGIN
	SET DEADLOCK_PRIORITY HIGH
	BEGIN TRAN
	BEGIN TRY
		UPDATE Caserie SET culoare = 'BLUE' WHERE idCaserie = 1
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Caserie', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		UPDATE Marca SET tara = 'AUSTRALIA' where nume = 'Tesla'
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Marca', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Deadlock_T2;

GO
CREATE PROCEDURE Deadlock_T2_C# AS
BEGIN
	SET DEADLOCK_PRIORITY HIGH
	BEGIN TRAN
	UPDATE Caserie SET culoare = 'BLUE' WHERE idCaserie = 1
		INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Caserie', CURRENT_TIMESTAMP)
	WAITFOR DELAY '00:00:05'
	UPDATE Marca SET tara = 'AUSTRALIA' where nume = 'Tesla'
		INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Marca', CURRENT_TIMESTAMP)
	COMMIT TRAN
	SELECT 'Transaction committed' AS [Message]
END