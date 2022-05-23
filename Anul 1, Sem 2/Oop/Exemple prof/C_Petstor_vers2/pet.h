#pragma once
typedef struct {
	char* type;
	char* species;
	float price;
} Pet;

/*
Create a new pet
*/
Pet createPet(char* type, char* species, float price);

/*
 Dealocate memory occupied by pet
*/
void destroyPet(Pet* p);

/*
Create a copy for the pet
Deep copy - copy all the sub-parts
*/
Pet copyPet(Pet* p);

/*
   Validate pet
   return !=0 if pet is invalid
*/
int validate(Pet p);

void testCreateDestroy();

