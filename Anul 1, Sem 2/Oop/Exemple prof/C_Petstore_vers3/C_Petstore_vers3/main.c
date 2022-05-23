//added for memory leak detection
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include "pet.h"
#include "MyList.h"
#include "petstoreservice.h"

#include <stdio.h>

void testAll() {
	testCreateDestroy();
	testCreateList();
	testIterateList();
	testCopyList();
	testAddPet();
	testResize();
	testSorts();
	testListOfLists();
	testListOfInts();
	testRemoveLast();
	testUndo();
}
/*
  Read pet from standard input and add to pet store
*/
void readPet(PetStore* store) {
	printf("Type:");
	char tip[30];
	scanf_s("%s", tip, 30);
	printf("Species:");
	char spe[30];
	scanf_s("%s", spe, 30);
	printf("Price:");
	float p;
	scanf_s("%f", &p);
	addPet(store, tip, spe, p);
	printf("Pet added.\n");
}

void printAllPets(MyList* l) {
	printf("Pets:\n");
	for (int i = 0; i < size(l); i++) {
		Pet* p = get(l, i);
		printf("Type:%s Species:%s Price:%f\n", p->type, p->species, p->price);	/// NOU sageti in loc de puncte
	}
}

void filterPets(PetStore* store) {
	printf("Type filter substring:");
	char filterStr[30];
	scanf_s("%s", filterStr, 30);	
	MyList* filteredL = getAllPet(store, filterStr);	/// NOU are steluta
	printAllPets(filteredL);							/// NOU nu are ampersant
	destroyList(filteredL);								/// NOU nu are ampersant
}

void showAll(PetStore* store) {
	MyList* allPets = getAllPet(store, NULL);			/// NOU steluta
	printAllPets(allPets);								/// NOU nu are ampersant
	destroyList(allPets);
}

void sortBType(PetStore* store) {					/// NOU la fel la toate
	MyList* allPets = sortByType(store);
	printAllPets(allPets);
	destroyList(allPets);
}

void sortBSpec(PetStore* store) {
	MyList* allPets = sortBySpecies(store);
	printAllPets(allPets);
	destroyList(allPets);
}

void run() {
	PetStore store = createPetStore();
	int ruleaza = 1;
	while (ruleaza) {
		printf("1 Add\n2 Filter\n3 All\n4 Sort by type\n5 Sort by species\n6 Undo\n0 Exit\nCommand:");
		int cmd = 0;
		scanf_s("%d", &cmd);
		switch (cmd) {
		case 1:
			readPet(&store);
			break;
		case 2:	
			filterPets(&store);			
			break;
		case 3:
			showAll(&store);
			break;
		case 4:
			sortBType(&store);
			break;
		case 5:
			sortBSpec(&store);
			break;
		case 6:
			if (undo(&store) != 0) {			/// NOU asta trebe facuta
				printf("No more undo!!!\n");
			}
			break;
		case 0:
			ruleaza = 0;		
			break;
		default:
			printf("Comanda invalida!!!\n");
		}
	}
	destroyStore(&store);
}

int main() {
	testAll();
	run();
	_CrtDumpMemoryLeaks();//print memory leak
	return 0;
}