#pragma once

#include "MyList.h"

typedef struct {
	MyList allPets;
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
MyList getAllPet(PetStore* store, char* typeSubstring);

/*
Sort ascending by type
*/
MyList sortByType(PetStore* store);

/*
Sort ascending by species
*/
MyList sortBySpecies(PetStore* store);


void testAddPet();
void testAddInvalidPet();
void testSorts();