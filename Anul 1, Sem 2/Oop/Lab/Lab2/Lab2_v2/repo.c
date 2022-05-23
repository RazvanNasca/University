#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "repo.h"
#include "domain.h"

void afisare_masini()
{
	/*
		afiseaza lista de masini
	*/

	int i;
	for (i = 0; i < allCars->nr_masini; i++)
		printf("%s %s %s\n", allCars->masina[i].nr_inm, allCars->masina[i].marca, allCars->masina[i].categorie);
}

/*void createCar(char* nr_inmatriculare, char* model, char* categorie, Masina* rez)
{
	size_t nrCaract = strlen(nr_inmatriculare) + 1;
	rez->nr_inm = malloc(sizeof(char) * nrCaract);
	strcpy_s(rez->nr_inm, nrCaract, nr_inmatriculare);

	nrCaract = strlen(model) + 1;
	rez->marca = malloc(sizeof(char) * nrCaract);
	strcpy_s(rez->marca, nrCaract, model);

	nrCaract = strlen(categorie) + 1;
	rez->marca = malloc(sizeof(char) * nrCaract);
	strcpy_s(rez->categorie, nrCaract, categorie);
	
	rez->inchiriata = 0;

}*/

int cauta_masina(char* nr_inmatriculare)
{
	/*
		cauta masini dupa numarul de inmatriculare
		input: nr_inmatriculare - unic pentru fiecare masina
		output: 1 - masina e in lista; 0 - masina nu e in lista
	*/

	int gasit = 0;
	int i;
	for (i = 0; i < allCars->nr_masini && gasit == 0; i++)
		if (strcmp(allCars->masina[i].nr_inm, nr_inmatriculare) == 0)
			gasit = 1;

	return gasit;
}

void adauga_masina(char* nr_inmatriculare, char* model, char* categorie)
{
	/*
		adauga o masina in lista
		input: nr_inmatriculare - numarul de inmatriculare; model - modelul masinii; categorie - categoria masinii
		output: modifica lista
	*/


	if (allCars->nr_masini == allCars->capacitate)
	{
		/// alocam mai multa memorie
		int capacitate_noua = allCars->capacitate + 4;
		Masina* new_cars = malloc(sizeof(Masina) * capacitate_noua);

		/// copiem elementele
		int i;
		for (i = 0; i < allCars->nr_masini; i++)
			new_cars[i] = allCars->masina[i];

		/// eliberam vecgiul vector
		free(allCars->masina);

		/// refacem vectorul vechi
		allCars->masina = new_cars;
		allCars->capacitate = capacitate_noua;
	}

	/*for (i = 0; i < strlen(nr_inmatriculare); i++)
		masina[poz].nr_inm[i] = nr_inmatriculare[i];
	masina[poz].nr_inm[i] = '\0';

	for (i = 0; i < strlen(model); i++)
		masina[poz].marca[i] = model[i];
	masina[poz].marca[i] = '\0';

	for (i = 0; i < strlen(categorie); i++)
		masina[poz].categorie[i] = categorie[i];
	masina[poz].categorie[i] = '\0';*/

	int i = allCars->nr_masini;
	size_t nrCaract = strlen(nr_inmatriculare) + 1;
	allCars->masina[i].nr_inm = (char*)malloc(sizeof(char) * nrCaract);
	strcpy_s(allCars->masina[i].nr_inm, strlen(nr_inmatriculare) * sizeof(char), nr_inmatriculare);

	nrCaract = strlen(model) + 1;
	allCars->masina[i].marca = (char*)malloc(sizeof(char) * nrCaract);
	strcpy_s(allCars->masina[i].marca, strlen(model) * sizeof(char), model);

	nrCaract = strlen(categorie) + 1;
	allCars->masina[i].marca = malloc(sizeof(char) * nrCaract);
	strcpy_s(allCars->masina[i].categorie, strlen(categorie) * sizeof(char), categorie);

	allCars->masina[i].inchiriata = 0;

	/*int i = allCars->nr_masini;

	strcpy_s(allCars->masina[i].nr_inm, strlen(m->nr_inm) * sizeof(char), m->nr_inm);
	strcpy_s(allCars->masina[i].marca, strlen(m->marca) * sizeof(char), m->marca);
	strcpy_s(allCars->masina[i].categorie, strlen(m->categorie) * sizeof(char), m->categorie);

	allCars->nr_masini++;*/

	//printf("%s %s %s %d\n", masina[i].nr_inm, masina[i].marca, masina[i].categorie, masina[i].ok);
}
