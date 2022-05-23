#pragma once
#include "service.h"
#include <assert.h>

class Teste {

public:

	/// testeaza adaugarea
	void testAdaugare();

	/// testeaza modificarea de stare
	void testModifica();

	/// testeaza cautarea dupa ID
	void testCautaID();

	/// testeaza cautare in functie de programator
	void testCautaProg();

	/// testeaza stocarea
	void testStocare();

	/// porneste testele
	void run();
};
