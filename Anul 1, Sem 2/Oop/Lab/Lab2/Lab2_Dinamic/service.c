#include"service.h"
#include"domain.h"
#include"repo.h"
#include"validare.h"
#include<stdio.h>

int serviceAdaugare(char* nr_inmatr, char* model, char* categorie, MyList* cars)
{
	Masina m = creareMasina(nr_inmatr, model, categorie);
	int valid = validare(&m);
	if (valid == 0)
	{
		int cauta = cauta_masina(cars,nr_inmatr);
		if (cauta == 1)
		{
			distrugeMasina(&m);
			return 4;
		}

		adauga_masina(cars,m);
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

int serviceActualizare(char* nr_inmatr,char* model,char* categorie, MyList* cars)
{
	Masina m = creareMasina(nr_inmatr, model, categorie);
	int valid = validare(&m);

	if (valid == 0)
	{
		int cauta = cauta_masina(cars, nr_inmatr);
		if (cauta == 0)
		{
			distrugeMasina(&m);
			return 4;
		}

		actualizeaza_masina(cars, m);
		return 0;
	}
	else
	{
		distrugeMasina(&m);
		return valid;
	}
}