go
use GarajMasini
go

---Motor
INSERT INTO Motor(idMotor, capacitate, nr_cilindrii, cai_putere, combustibil)
VALUES (1, 2, 4, 103, 'diesel')

INSERT INTO Motor(idMotor, capacitate, nr_cilindrii, cai_putere, combustibil)
VALUES (2, 3, 6, 170, 'benzina')

INSERT INTO Motor(idMotor, capacitate, nr_cilindrii, cai_putere, combustibil)
VALUES (3, 5, 8, 500, 'electric')

INSERT INTO Motor(idMotor, capacitate, nr_cilindrii, cai_putere, combustibil)
VALUES (4, 4, 12, 650, 'benzina')


---Garaj
INSERT INTO Garaj(idGaraj, denumire, nrGaraje)
VALUES (1, 'PCGaraj', 3)

INSERT INTO Garaj(idGaraj, denumire, nrGaraje)
VALUES (2, 'WVGaraj', 5)

INSERT INTO Garaj(idGaraj, denumire, nrGaraje)
VALUES (3, 'BGaraj', 2)


---Adresa
INSERT INTO Adresa(idAdresa, judet, localitate, strada, numar)
VALUES (1, 'BN', 'Bistrita', 'Somesului', 10)

INSERT INTO Adresa(idAdresa, judet, localitate, strada, numar)
VALUES (2, 'CJ', 'Cluj-Napoca', 'Barbu Lautaru', 7)

INSERT INTO Adresa(idAdresa, judet, localitate, strada, numar)
VALUES (3, 'CJ', 'Cluj-Napoca', 'Nicolae Balcescu', 15)


---Manager
INSERT INTO Manager(idManager, nume, prenume, data_inceput)
VALUES (1, 'Popescu', 'Mihai', '06-21-2016')

INSERT INTO Manager(idManager, nume, prenume, data_inceput)
VALUES (2, 'Ion', 'Alex', '11-15-2019')

INSERT INTO Manager(idManager, nume, prenume, data_inceput)
VALUES (3, 'Makai', 'Catalin', '01-03-2017')


---Angajat
INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (1, 1, 'Ionasc', 'Rares', '01-21-2000')

INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (2, 1, 'Borz', 'Alex', '10-06-2000')

INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (3, 2, 'Negrusa', 'Catalin', '02-26-2000')

INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (4, 2, 'Ignat', 'Emanuel', '10-07-2000')

INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (5, 2, 'Zoltan', 'Razvan', '09-15-1999')

INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (6, 3, 'Fra', 'Catalin', '09-10-2002')

INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (7, 1, 'EM', 'Mihai', '07-13-1999')

INSERT INTO Angajat(idAngajat, idGaraj, nume, prenume, data_nasterii)
VALUES (8, 2, 'Alex', 'Alex', '10-06-2000')


---Marca
INSERT INTO Marca(idMarca, nume, tara)
VALUES (1, 'WV', 'Germania')

INSERT INTO Marca(idMarca, nume, tara)
VALUES (2, 'Citroen', 'Franta')

INSERT INTO Marca(idMarca, nume, tara)
VALUES (3, 'Ferarri', 'Italia')

INSERT INTO Marca(idMarca, nume, tara)
VALUES (4, 'Bentley', 'Anglia')

INSERT INTO Marca(idMarca, nume, tara)
VALUES (5, 'Tesla', 'SUA')


---Model 
INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (1, 1, 'Golf', 1980)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (2, 1, 'Passat', 1975)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (3, 3, 'Tributo', 2018)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (4, 5, 'Model 3', 2015)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (5, 5, 'Model S', 2016)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (6, 5, 'Model X', 2017)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (7, 5, 'Model Y', 2017)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (8, 1, 'Polo', 1985)

INSERT INTO Model(idModel, idMarca, nume, an)
VALUES (9, 1, 'GOLF', 1985)


---Caroserie
INSERT INTO Caserie(idCaserie, culoare, material, greutate)
VALUES (1, 'rosie', 'aliaj', 1800)

INSERT INTO Caserie(idCaserie, culoare, material, greutate)
VALUES (2, 'albastra', 'aluminiu', 1500)

INSERT INTO Caserie(idCaserie, culoare, material, greutate)
VALUES (3, 'gri', 'metal', 2200)

INSERT INTO Caserie(idCaserie, culoare, material, greutate)
VALUES (4, 'portocalie', 'policarbonat', 1000)


---Masina
INSERT INTO Masina(idMasina, idCaserie, idMotor, idMarca)
VALUES (1, 2, 1, 1)

INSERT INTO Masina(idMasina, idCaserie, idMotor, idMarca)
VALUES (2, 1, 3, 3)

INSERT INTO Masina(idMasina, idCaserie, idMotor, idMarca)
VALUES (3, 2, 4, 5)

INSERT INTO Masina(idMasina, idCaserie, idMotor, idMarca)
VALUES (4, 4, 4, 5)

INSERT INTO Masina(idMasina, idCaserie, idMotor, idMarca)
VALUES (5, 2, 2, 4)


---NumarInmatriculare
INSERT INTO NumarInmatriculare(idInmatriculare, judet, numar, nume)
VALUES (1, 'BN', 47, 'RZV')

INSERT INTO NumarInmatriculare(idInmatriculare, judet, numar, nume)
VALUES (2, 'CJ', 07, 'FRI')

INSERT INTO NumarInmatriculare(idInmatriculare, judet, numar, nume)
VALUES (3, 'BN', 15, 'MDX')

INSERT INTO NumarInmatriculare(idInmatriculare, judet, numar, nume)
VALUES (4, 'CJ', 02, 'MDS')

INSERT INTO NumarInmatriculare(idInmatriculare, judet, numar, nume)
VALUES (5, 'MS', 16, 'KSV')


---GarajMasina
INSERT INTO GarajMasina(idGaraj, idMasina)
VALUES (1, 1)

INSERT INTO GarajMasina(idGaraj, idMasina)
VALUES (1, 3)

INSERT INTO GarajMasina(idGaraj, idMasina)
VALUES (2, 2)

INSERT INTO GarajMasina(idGaraj, idMasina)
VALUES (2, 4)


---1. Afiseaza numarul de angajati de la garajele din Cluj 
SELECT g.denumire AS [Nume garaj], COUNT(a.idAngajat) AS [Numar angajati], adr.judet
FROM Garaj g, Angajat a, Adresa adr
WHERE a.idGaraj = g.idGaraj and g.idGaraj = adr.idAdresa
GROUP BY g.denumire, adr.judet
HAVING adr.judet = 'CJ'


---2. Afiseaza numarul de potrivri la prenumele angazatilor cu ale managerilor
SELECT COUNT(a.idAngajat) AS [Numar potriviri], a.prenume AS [Nume Comun]
FROM Garaj g, Manager m, Angajat a
WHERE a.idGaraj = g.idGaraj and g.idGaraj = m.idManager
GROUP BY a.prenume
HAVING COUNT(a.idAngajat) >= 2


---3. Numarul de modele dintr un garaj
SELECT COUNT(mo.idModel) AS [Numar de modele], g.denumire
FROM Model mo
INNER JOIN Marca ma ON mo.idMarca = ma.idMarca
INNER JOIN Masina m ON ma.idMarca = m.idMarca
INNER JOIN GarajMasina gm ON m.idMasina = gm.idMasina
INNER JOIN Garaj g ON gm.idGaraj = g.idGaraj
GROUP BY g.denumire
HAVING COUNT(mo.idModel) > 1


---4. Cate numere de inmatriculare se afla la o adresa din acelasi judet
SELECT COUNT(n.idInmatriculare) AS [Numar de potriviri], n.judet AS [Judet]
FROM NumarInmatriculare n
INNER JOIN Masina m ON n.idInmatriculare = m.idMasina
INNER JOIN GarajMasina gm ON m.idMasina = gm.idMasina
INNER JOIN Garaj g ON gm.idGaraj = g.idGaraj
INNER JOIN Adresa a ON g.idGaraj = a.idAdresa
WHERE n.judet = a.judet
GROUP BY n.judet


---5. Afiseaza toti angajatii care nu li se repeta prenumele si sunt din anul 2000
SELECT DISTINCT prenume
FROM Angajat 
WHERE DATEDIFF(year,data_nasterii, CURRENT_TIMESTAMP) = 20 


---6. Masinile din Cluj care au peste 150cp
SELECT n.judet, n.numar, n.nume
FROM NumarInmatriculare n
INNER JOIN Masina m ON n.idInmatriculare = m.idMasina
INNER JOIN Motor mo ON m.idMotor = mo.idMotor
WHERE mo.cai_putere > 150 and n.judet = 'CJ'


---7. Afiseaza toate marcile care contin litera a
SELECT DISTINCT nume AS [Marci diferite]
FROM Marca
WHERE nume LIKE '%e%'

---8. Afiseaza numele si prenumele managerilor de garaj, numele garajului si strada pe care se afla
SELECT m.nume AS [Nume manager], m.prenume AS [Prenume manager], g.denumire AS [Nume garaj], a.strada AS [Adresa]
FROM Manager m, Garaj g, Adresa a
WHERE m.idManager = g.idGaraj AND g.idGaraj = a.idAdresa


---9. Afiseaza toate informatiile despre masinile din baza de date
SELECT ma.nume AS [Marca], ma.tara AS [Tara], mo.nume AS [Model], mo.an AS [An], inm.judet AS [Judet], inm.numar AS [Numar], inm.nume AS [Nume inm]
FROM Model mo
INNER JOIN Marca ma ON mo.idMarca = ma.idMarca
INNER JOIN Masina m ON ma.idMarca = m.idMarca
INNER JOIN NumarInmatriculare inm ON m.idMasina = inm.idInmatriculare


---10. Toate garajele care au modele de masini mai noi de 2015
SELECT g.denumire AS [Nume garaj], mo.nume AS [Nume model]
FROM Model mo
INNER JOIN Marca ma ON mo.idModel = ma.idMarca
INNER JOIN Masina m ON ma.idMarca = m.idMarca
INNER JOIN GarajMasina gm ON m.idMasina = gm.idMasina
INNER JOIN Garaj g ON gm.idGaraj = g.idGaraj
WHERE mo.an > 2015