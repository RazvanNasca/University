#pragma once
#include"domain.h"

void adauga_masina(MyList* cars, Masina m);

void AfiseazaLista(MyList* allCars);

int cauta_masina(MyList* allCars, char* nr_inmatr);

void actualizeaza_masina(MyList* cars, Masina m);
