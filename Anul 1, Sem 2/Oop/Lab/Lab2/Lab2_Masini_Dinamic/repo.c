#pragma once
#include"domain.h"
#include"repo.h"
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

int cauta_masina(MyList* allCars, char* nr_inmatriculare)
{

	int gasit = 0;
	int i;
	for (i = 0; i < allCars->nr_masini && gasit == 0; i++)
		if (strcmp(allCars->masina[i].nr_inm, nr_inmatriculare) == 0)
			gasit = 1;

	return gasit;
}

int cauta_pozitie_masina(MyList* allCars, char* nr_inmatriculare)
{

	int i;
	for (i = 0; i < allCars->nr_masini; i++)
		if (strcmp(allCars->masina[i].nr_inm, nr_inmatriculare) == 0)
			return i;

	return -1;
}

void AfiseazaLista(MyList* allCars)
{
	int i;
	for (i = 0; i < allCars->nr_masini; i++)
		if(allCars->masina[i].inchiriata == 0)
			printf("%s %s %s %s\n", allCars->masina[i].nr_inm, allCars->masina[i].marca, allCars->masina[i].categorie, "restituita");
		else
			printf("%s %s %s %s\n", allCars->masina[i].nr_inm, allCars->masina[i].marca, allCars->masina[i].categorie, "inchiriata");
}

void actualizeaza_masina(MyList* cars, Masina m, int i)
{	
	strcpy_s(cars->masina[i].marca, strlen(m.marca) + 1, m.marca);
	strcpy_s(cars->masina[i].categorie, strlen(m.categorie) + 1, m.categorie);
}

int Actiune(char* actiune, char* nr_inmatr, int  poz, MyList* cars)
{
	
	if (strcmp(actiune, "inchiriere") == 0)
		if (cars->masina[poz].inchiriata == 0)
		{
			cars->masina[poz].inchiriata = 1;
			return 0;
		}
		else
			return 1;
	else
		if (cars->masina[poz].inchiriata == 1) //masina este in momentul asta inchiriata
		{
			cars->masina[poz].inchiriata = 0;	//masina este restituita
			return 0;
		}
		else
			return 2;
}

int afiseaza_criteriu(char* criteriu, MyList* cars)
{
	/*
		afisarea listei care respecta un criteriu dat
	*/

	int i, gasit = 0;

	for (i = 0; i < cars->nr_masini; i++)
		if (strcmp(cars->masina[i].categorie, criteriu) == 0 || strcmp(cars->masina[i].marca, criteriu) == 0)
		{
			if (cars->masina[i].inchiriata == 0)
				printf("%s %s %s %s\n", cars->masina[i].nr_inm, cars->masina[i].marca, cars->masina[i].categorie, "restituita");
			else
				printf("%s %s %s %s\n", cars->masina[i].nr_inm, cars->masina[i].marca, cars->masina[i].categorie, "inchiriata");
			gasit = 1;
		}

	printf("\n");

	if (gasit == 0)
		return 2;
	return 0;
}

int sortare(char* criteriu, char* ordine, MyList* cars, FunctieComparareMarca cmpM, FunctieComparareCategorie cmpC)
{
	/*
		sorteaza lista data dupa o ordine precizata
		input: criteriu - criteriul dupa care sortam,  ordine - ordinea dupa care se sorteaza
		output: lista sortata
	*/
	if (strcmp(ordine, "crescator") == 0)
	{
		if (strcmp(criteriu, "model") == 0)
		{
			int i, j;
			for (i = 0; i < cars->nr_masini - 1; i++)
				for (j = i + 1; j < cars->nr_masini; j++)
					if (cmpM(&cars->masina[i], &cars->masina[j]) > 0)
					{
						Masina aux = cars->masina[i];
						cars->masina[i] = cars->masina[j];
						cars->masina[j] = aux;
					}
		}
		else
		{
			int i, j;
			for (i = 0; i < cars->nr_masini - 1; i++)
				for (j = i + 1; j < cars->nr_masini; j++)
					if (cmpC(&cars->masina[i], &cars->masina[j]) > 0)
					{
						Masina aux = cars->masina[i];
						cars->masina[i] = cars->masina[j];
						cars->masina[j] = aux;
					}
		}
	}
	else
	{
		if (strcmp(criteriu, "model") == 0)
		{
			int i, j;
			for (i = 0; i < cars->nr_masini - 1; i++)
				for (j = i + 1; j < cars->nr_masini; j++)
					if (cmpM(&cars->masina[i], &cars->masina[j]) < 0)
					{
						Masina aux = cars->masina[i];
						cars->masina[i] = cars->masina[j];
						cars->masina[j] = aux;
					}
		}
		else
		{
			int i, j;
			for (i = 0; i < cars->nr_masini - 1; i++)
				for (j = i + 1; j < cars->nr_masini; j++)
					if (cmpC(&cars->masina[i], &cars->masina[j]) < 0)
					{
						Masina aux = cars->masina[i];
						cars->masina[i] = cars->masina[j];
						cars->masina[j] = aux;
					}
		}
	}
}