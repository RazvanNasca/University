#define _CRTDBG_MAP_ALLOC  
#include <stdlib.h>  
#include <crtdbg.h>  

#include "console.h"
#include "PetStore.h"
#include "petrepo.h"
#include "validator.h"

void adaugaCateva(PetStore& ctr) {
	ctr.addPet("a", "zz", 10);
	ctr.addPet("b", "aa", 100);
	ctr.addPet("z", "cc", 50);
	ctr.addPet("c", "bb", 70);
}

void testAll() {
	testValidator();
	testeRepo();
	testCtr();
}

int main() {
	{
	testAll();

	PetRepo rep;
	PetValidator val;
	PetStore ctr{ rep,val };
	adaugaCateva(ctr);//adaug cateva animale
	ConsolUI ui{ ctr };
	ui.start();
	}
	_CrtDumpMemoryLeaks();
}