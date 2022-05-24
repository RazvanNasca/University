USE GarajMasini;

GO
CREATE PROCEDURE Deadlock_T1 AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		UPDATE Marca SET tara = 'AUSTRALIA' where nume = 'Tesla'
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Marca', CURRENT_TIMESTAMP)
		WAITFOR DELAY '00:00:05'
		UPDATE Caserie SET culoare = 'BLUE' WHERE idCaserie = 1
			INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Caserie', CURRENT_TIMESTAMP)
		COMMIT TRAN
		SELECT 'Transaction committed' AS [Message]
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked' AS [Message]
	END CATCH
END

EXECUTE Deadlock_T1;

GO
CREATE PROCEDURE Deadlock_T1_C# AS
BEGIN
	BEGIN TRAN
	UPDATE Marca SET tara = 'AUSTRALIA' where nume = 'Tesla'
		INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Marca', CURRENT_TIMESTAMP)
	WAITFOR DELAY '00:00:05'
	UPDATE Caserie SET culoare = 'BLUE' WHERE idCaserie = 1
		INSERT INTO LogTable(operatia, tabel, timpul) VALUES ('UPDATE', 'Caserie', CURRENT_TIMESTAMP)
	COMMIT TRAN
	SELECT 'Transaction committed' AS [Message]
END