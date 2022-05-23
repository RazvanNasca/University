#pragma once

#include "MyList.h"

typedef struct {
	MyList* allPets;
	MyList* undoList;//list of list of pets     /// NOU nu era
} PetStore;

/*
  create a petstore
*/
PetStore createPetStore();

void destroyStore(PetStore* store);
/*
Add a pet to the store
*/
int addPet(PetStore* store, char* type, char* species, float price);

/*
  Filter pets in the store 
  typeSubstring - cstring
  return all pets where typeSubstring is a substring of the type
*/
MyList* getAllPet(PetStore* store, char* typeSubstring);        /// NOU steluta nu era

/*
Sort ascending by type
*/
MyList* sortByType(PetStore* store);                             /// NOU steluta nu era

/*
Sort ascending by species
*/
MyList* sortBySpecies(PetStore* store);                          /// NOU steluta nu era

/*
Restore previous pet list
return 0 on ok, 1 if there is no more available undo
*/
int undo(PetStore* store);                                      /// NOU  nu era


void testAddPet();
void testSorts();
void testUndo();
