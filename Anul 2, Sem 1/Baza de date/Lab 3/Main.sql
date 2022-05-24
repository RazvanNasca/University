CREATE PROCEDURE Main
@dateIntrare VARCHAR(10)
AS
BEGIN
	IF @dateIntrare NOT IN ('1', '2', '3', '4', '5', '6')
	BEGIN
		PRINT 'Date de intrare invalide!'
		RETURN
	END

	DECLARE @versiuneFinala INT
	SET @versiuneFinala = CONVERT(INT, @dateIntrare)
	DECLARE @versiuneCurenta INT
	SET @versiuneCurenta = (SELECT numar FROM Versiune)

	WHILE @versiuneFinala > @versiuneCurenta
	BEGIN
		IF @versiuneCurenta = 1
		BEGIN
			EXECUTE ModificaTip
			PRINT 'S-a executat ModificaTip'
		END
		ELSE
			IF @versiuneCurenta = 2
			BEGIN
				EXECUTE AdaugaImplicit
				PRINT 'S-a executat AdaugaImplicit'
			END
			ELSE
				IF @versiuneCurenta = 3
				BEGIN
					EXECUTE CreeazaTabela
					PRINT 'S-a executat CreeazaTabela'
				END
				ELSE
					IF @versiuneCurenta = 4
					BEGIN
						EXECUTE AdaugaCamp
						PRINT 'S-a executat AdaugaCamp'
					END
					ELSE
						IF @versiuneCurenta = 5
						BEGIN
							EXECUTE AdaugaFK
							PRINT 'S-a executat AdaugaFK'
						END

		SET @versiuneCurenta = (SELECT numar FROM Versiune)
		Print 'Versiunea curenta este: ' + CONVERT(varchar(10), @versiuneCurenta)
	END

	WHILE @versiuneFinala < @versiuneCurenta
	BEGIN
		IF @versiuneCurenta = 2
		BEGIN
			EXECUTE ModificaTipInvers
			PRINT 'S-a executat ModificaTipInvers'
		END
		ELSE
			IF @versiuneCurenta = 3
			BEGIN
				EXECUTE StergeImplicit
				PRINT 'S-a executat StergeImplicit'
			END
			ELSE
				IF @versiuneCurenta = 4
				BEGIN
					EXECUTE StergeTabela
					PRINT 'S-a executat StergeTabela'
				END
				ELSE
					IF @versiuneCurenta = 5
					BEGIN
						EXECUTE StergeCamp
						PRINT 'S-a executat StergeCamp'
					END
					ELSE
						IF @versiuneCurenta = 6
						BEGIN
							EXECUTE StergeFK
							PRINT 'S-a executat StergeFK'
						END

		SET @versiuneCurenta = (SELECT numar FROM Versiune)
		Print 'Versiunea curenta este: ' + CONVERT(varchar(10), @versiuneCurenta)
	END

	PRINT 'Versiunea a fost actualizata!'
END

EXECUTE Main 1

SELECT * FROM Versiune
