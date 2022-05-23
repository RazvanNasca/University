#include "TestScurt.h"
#include <assert.h>
#include "Matrice.h"
#include <iostream>

using namespace std;

void testAll() { //apelam fiecare functie sa vedem daca exista
	Matrice m(4, 4);

	assert(m.sumaSubDiagonalaPrincipala() == 0);

	assert(m.nrLinii() == 4);
	assert(m.nrColoane() == 4);
	//adaug niste elemente
	m.modifica(1, 1, 5);
	assert(m.element(1, 1) == 5);
	m.modifica(1, 1, 6);
	assert(m.element(1, 2) == NULL_TELEMENT);


	m.modifica(1, 2, 6);
	m.modifica(1, 3, 6);
	m.modifica(2, 2, 6);
	m.modifica(2, 3, 6);
	m.modifica(2, 1, 6);
	m.modifica(3, 1, 6);

	assert(m.sumaSubDiagonalaPrincipala() == 12);

	m.modifica(3, 1, 0);
	assert(m.modifica(3, 1, 2) == 0);
}