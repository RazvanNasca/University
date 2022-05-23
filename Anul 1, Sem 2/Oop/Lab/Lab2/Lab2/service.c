#include"service.h"
#include"validare.h"
#include"repo.h"
#include<stdio.h>

int serviceAdauga(char* nr_inmatriculare, char* model, char* categorie)
{
	/*
		apeleaza functiile de validare si adaugare
		returneaza coduri de erori specifice
	*/

	int valid = validare(nr_inmatriculare, model, categorie);
	if (valid == 0)
	{
		int cauta = cauta_masina(nr_inmatriculare);
		if (cauta == 1)
			return 4;

		adauga_masina(nr_inmatriculare, model, categorie);
		return 0;
	}
	else
		return valid;
}

void serviceAfisare()
{
	/// afiseaza lista de masini

	afisare_masini();
}

int serviceActualizare(char* nr_inmatriculare, char* model, char* categorie)
{
	/*
		apeleaza functiile de validare si actualizare
		returneaza coduri de erori specifice
	*/

	int valid = validare(nr_inmatriculare, model, categorie);
	if (valid == 0)
	{
		int cauta = cauta_masina(nr_inmatriculare);
		if (cauta == 0)
			return 4;
		actualizeaza_masina(nr_inmatriculare, model, categorie);
		return 0;
	}
	else
		return valid;
}

int serviceActiune(char* actiune, char* nr_inmatriculare)
{
	/*
		apeleaza functiile de validare si actionare
		returneaza coduri de erori specifice
	*/

	int caut = cauta_masina(nr_inmatriculare);
	if (caut == 1)
	{
		int valid = validActiune(actiune);
		if (valid == 0)
		{
			int act = Actiune(actiune, nr_inmatriculare);
			return act;
		}
		else
			return 4;
	}
	else
		return 3;
}

int serviceCriteriu(char* criteriu, char* specific)
{
	/*
		apeleaza functiile de validare si afisare dupa criteriu
		returneaza coduri de erori specifice
	*/

	int valid = valideazaCriteriu(criteriu, specific);
	if (valid == 0)
	{
		int afis = afiseaza_criteriu(specific);
		return afis;
	}
	return 1;
}

int serviceSortare(char* criteriu, char* ordine)
{
	/*
		apeleaza functiile de validare si sortare dupa un criteriu
		returneaza coduri de erori specifice
	*/

	int valid = validareSortare(criteriu, ordine);
	if (valid == 0)
	{
		sortare(criteriu, ordine);
		return 0;
	}
	return valid;
}