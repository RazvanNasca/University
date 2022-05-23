#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include"domain.h"

MyList creareLista()
{
	MyList rez;
	rez.nr_masini = 0;
	rez.capacitate = 4;
	rez.masina = (Masina*)malloc(sizeof(Masina) * rez.capacitate);
	return rez;
}

Masina creareMasina(char* nr_inmatr, char* model, char* categorie)
{
	Masina rez;
	size_t nrC = strlen(nr_inmatr) + 1;
	rez.nr_inm = (char*)malloc(sizeof(char) * nrC);
	if(rez.nr_inm)
		strcpy_s(rez.nr_inm, nrC, nr_inmatr);

	nrC = strlen(model) + 1;
	rez.marca = (char*)malloc(sizeof(char) * nrC);
	if(rez.marca)
		strcpy_s(rez.marca, nrC, model);

	nrC = strlen(categorie) + 1;
	rez.categorie = (char*)malloc(sizeof(char) * nrC);
	if(rez.categorie)
		strcpy_s(rez.categorie, nrC, categorie);

	rez.inchiriata = 0;
	return rez;
}


void distrugeStore(MyList* l)
{
	for (int i = 0; i < l->nr_masini; i++)
		distrugeMasina(&l->masina[i]);

	free(l->masina);
	l->masina = NULL;
	l->nr_masini = 0;
}

void distrugeMasina(Masina* car)
{
	free(car->categorie);
	free(car->marca);
	free(car->nr_inm);

	car->categorie = NULL;
	car->marca = NULL;
	car->nr_inm = NULL;

	car->inchiriata = 0;
}