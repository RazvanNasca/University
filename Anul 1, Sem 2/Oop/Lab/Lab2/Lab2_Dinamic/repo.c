#pragma once
#include"domain.h"
#include<stdlib.h>
#include<string.h>

void adauga_masina(MyList* cars, Masina m)
{
	if (cars->nr_masini == cars->capacitate)
	{
		int capacitate_noua = cars->capacitate * 2;
		MyList* new_cars = (MyList*)malloc(sizeof(MyList) * capacitate_noua);

		int i;
		for (i = 0; i < cars->nr_masini; i++)
			new_cars->masina[i] = cars->masina[i];

		free(cars->masina);
		cars = new_cars;
		cars->capacitate = capacitate_noua;
		cars->nr_masini = capacitate_noua / 2 - 1;
	}

	cars->masina[cars->nr_masini] = m;
	cars->nr_masini++;

}

int cauta_masina(MyList* allCars,char* nr_inmatriculare)
{

	int gasit = 0;
	int i;
	for (i = 0; i < allCars->nr_masini && gasit == 0; i++)
		if (strcmp(allCars->masina[i].nr_inm, nr_inmatriculare) == 0)
			gasit = 1;

	return gasit;
}

void AfiseazaLista(MyList* allCars)
{
	int i;
	for (i = 0; i < allCars->nr_masini; i++)
		printf("%s %s %s\n", allCars->masina[i].nr_inm, allCars->masina[i].marca, allCars->masina[i].categorie);
}

void actualizeaza_masina(MyList* cars, Masina m)
{
	int i;
	for (i = 1; i <= cars->nr_masini; i++)
		if (strcmp(cars->masina[i].nr_inm, m.nr_inm) == 0)
		{
			int j;
			for (j = 0; j < strlen(m.marca); j++)
				cars->masina[i].marca[j] = m.marca[j];
			cars->masina[i].marca[j] = '\0';

			for (j = 0; j < strlen(m.categorie); j++)
				cars->masina[i].categorie[j] = m.categorie[j];
			cars->masina[j].categorie[j] = '\0';
		}
}