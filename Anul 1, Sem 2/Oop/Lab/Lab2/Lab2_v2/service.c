#include"service.h"
#include"validare.h"
#include"repo.h"
#include"domain.h"
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

		//Masina* m;
		//createCar(nr_inmatriculare, model, categorie, &m);
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

