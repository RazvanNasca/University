#include "petstoreservice.h"

#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "mysort.h"
/*
Add a pet to the store
*/
int addPet(PetStore* store, char* type, char* species, float price) {
	Pet p = createPet(type, species, price);
	int errorCode = validate(p);
	if (errorCode!=0) {		
		destroyPet(&p);
		return errorCode;
	}
	add(&store->allPets, p);
	return 0; // all ok
}

/*
Filter pets in the store
typeSubstring - cstring
return all pets where typeSubstring is a substring of the type
*/
MyList getAllPet(PetStore* store, char* typeSubstring) {
	if (typeSubstring == NULL || strlen(typeSubstring) == 0) {
		return copyList(&store->allPets);
	}
	MyList rez = createEmpty();
	for (int i = 0; i < size(&store->allPets); i++) {
		Pet p = get(&store->allPets, i);
		if (strstr(p.type, typeSubstring) != NULL) {
			add(&rez,copyPet (&p));
		}
	}
	return rez;
}

int cmpType(Pet* p1, Pet* p2) {
	return strcmp(p1->type, p2->type);
}

int cmpSpe(Pet* p1, Pet* p2) {
	return strcmp(p1->species, p2->species);
}

/*
 Sort ascending by type
*/
MyList sortByType(PetStore* store) {
	MyList l = copyList(&store->allPets);
	sort(&l, cmpType);
	return l;
}

/*
Sort ascending by species
*/
MyList sortBySpecies(PetStore* store) {
	MyList l = copyList(&store->allPets);
	sort(&l, cmpSpe);
	return l;
}

PetStore createPetStore()
{
	PetStore rez;
	rez.allPets = createEmpty();
	return rez;
}

void destroyStore(PetStore * store)
{
	destroy(&store->allPets);
}

void testAddPet() {
	PetStore store = createPetStore();
	addPet(&store, "a", "b", 10);
	addPet(&store, "a2", "b2", 20);
	MyList filtered = getAllPet(&store, NULL);
	assert(size(&filtered) == 2);
	destroy(&filtered);

	filtered = getAllPet(&store, "a2");
	assert(size(&filtered) == 1);
	destroy(&filtered);

	filtered = getAllPet(&store, "2");
	assert(size(&filtered) == 1);
	destroy(&filtered);

	filtered = getAllPet(&store, "a");
	assert(size(&filtered) == 2);
	destroy(&filtered);

	destroyStore(&store);
}

void testAddInvalidPet() {
	PetStore store = createPetStore();
	int errorCode = addPet(&store, "", "b", 10);
	assert(errorCode != 0);

	errorCode = addPet(&store, "a2", "", 20);
	assert(errorCode != 0);

	errorCode = addPet(&store, "a2", "v", -20);
	assert(errorCode != 0);

	MyList filtered = getAllPet(&store, NULL);
	assert(size(&filtered) == 0);
	destroy(&filtered);

	destroyStore(&store);
}

void testSorts() {
	PetStore store = createPetStore();
	addPet(&store, "a", "c", 10);
	addPet(&store, "c", "b", 20);
	addPet(&store, "b", "a", 20);
	MyList l = sortByType(&store);
	assert(strcmp(get(&l, 1).type,"b") == 0);
	destroy(&l);
	l = sortBySpecies(&store);
	assert(strcmp(get(&l, 0).species, "a") == 0);
	assert(strcmp(get(&l, 1).species, "b") == 0);
	destroy(&l);
	destroyStore(&store);
}