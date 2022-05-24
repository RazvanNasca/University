USE GarajMasini;

-- Dirty reads:
SELECT * FROM Marca

-- Non-repeatable reads:
SELECT * FROM Marca
UPDATE Marca SET tara = 'SUA' WHERE tara = 'AMERICA'

-- Phantom reads:
SELECT * FROM Marca
DELETE FROM Marca WHERE idMarca = 6

-- Deadlock:
SELECT * FROM Marca
SELECT * FROM Caserie
UPDATE Caserie SET culoare = 'rosu' WHERE idCaserie = 1
UPDATE Marca SET tara = 'SUA' where nume = 'Tesla'

-- Log table:
SELECT * FROM LogTable
DELETE FROM LogTable