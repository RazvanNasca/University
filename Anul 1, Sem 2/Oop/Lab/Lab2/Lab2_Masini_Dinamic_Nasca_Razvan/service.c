#include"service.h"
#include"domain.h"
#include"repo.h"
#include"validare.h"
#include<stdio.h>
#include<string.h>

int serviceAdaugare(char* nr_inmatr, char* model, char* categorie, MyList* cars)
{
	Masina m = creareMasina(nr_inmatr, model, categorie);
	int valid = validare(&m);
	if (valid == 0)
	{
		int cauta = cauta_masina(cars, nr_inmatr);
		if (cauta == 1)
		{
			distrugeMasina(&m);
			return 4;
		}

		adauga_masina(cars, m);
		//distrugeMasina(&m);
		return 0;
	}
	else
	{
		distrugeMasina(&m);
		return valid;
	}

}


void serviceAfisare(MyList* allCars)
{
	AfiseazaLista(allCars);
}

int serviceActualizare(char* nr_inmatr, char* model, char* categorie, MyList* cars)
{
	Masina m = creareMasina(nr_inmatr, model, categorie);
	int valid = validare(&m);

	if (valid == 0)
	{
		int cauta = cauta_pozitie_masina(cars, nr_inmatr);
		if (cauta == -1)
		{
			distrugeMasina(&m);
			return 4;
		}

		actualizeaza_masina(cars, m, cauta);
		distrugeMasina(&m);
		return 0;
	}
	else
	{
		distrugeMasina(&m);
		return valid;
	}
}

int serviceActiune(char* actiune, char* nr_inmatr, MyList* cars)
{
	int caut = cauta_pozitie_masina(cars, nr_inmatr);
	if (caut != -1)
	{
		int valid = validActiune(actiune);
		if (valid == 0)
		{
			int act = Actiune(actiune, caut, cars);
			return act;
		}
		else
			return 4;
	}
	else
		return 3;
}

int serviceCriteriu(char* criteriu, char* specific, MyList* cars)
{
	int valid = valideazaCriteriu(criteriu, specific);
	if (valid == 0)
	{
		int afis = afiseaza_criteriu(specific, cars);
		return afis;
	}
	return 1;
}

int cmpM(Masina* m1, Masina* m2) {
	return strcmp(m1->marca, m2->marca);
}

int cmpC(Masina* m1, Masina* m2) {
	return strcmp(m1->categorie, m2->categorie);
}

int serviceSortare(char* criteriu, char* ordine, MyList* cars)
{
	/*
		apeleaza functiile de validare si sortare dupa un criteriu
		returneaza coduri de erori specifice
	*/

	int valid = validareSortare(criteriu, ordine);
	if (valid == 0)
	{
		sortare(criteriu, ordine, cars,cmpM,cmpC);
		return 0;
	}
	return valid;
}
