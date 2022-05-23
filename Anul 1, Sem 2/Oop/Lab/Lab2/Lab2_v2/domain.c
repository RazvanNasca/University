#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include "domain.h"


void initializare()
{
	allCars = (MyList*)malloc(sizeof(MyList)*2);
	allCars->nr_masini = 0;
	allCars->capacitate = 2;
	allCars->masina = (Masina*)malloc(sizeof(Masina) * allCars->capacitate);
}

void distrugeStore()
{
	int i;
	for (i = 0; i < allCars->nr_masini; i++)
		distrugeMasina(&allCars->masina[i]);
	free(allCars->masina);
	allCars->masina = NULL;
	allCars->nr_masini = 0;

}

void distrugeMasina(Masina* car)
{
	free(car->categorie);
	free(car->nr_inm);
	free(car->marca);

	car->categorie = NULL;
	car->nr_inm = NULL;
	car->marca = NULL;
	
	car->inchiriata = 0;
}