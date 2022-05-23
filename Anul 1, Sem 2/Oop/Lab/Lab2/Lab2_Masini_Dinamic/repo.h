#pragma once
#include"domain.h"

typedef int(*FunctieComparareMarca)(Masina* m1, Masina* m2);

typedef int(*FunctieComparareCategorie)(Masina* m1, Masina* m2);

void adauga_masina(MyList* cars, Masina m);

void AfiseazaLista(MyList* allCars);

int cauta_masina(MyList* allCars, char* nr_inmatr);

int cauta_pozitie_masina(MyList* allCars, char* nr_inmatriculare);

void actualizeaza_masina(MyList* cars, Masina m, int i);

int Actiune(char* actiune, char* nr_inmatr, int  poz, MyList* cars);

int afiseaza_criteriu(char* specific, MyList* cars);

int sortare(char* criteriu, char* ordine, MyList* cars, FunctieComparareMarca cmpM, FunctieComparareCategorie cmpC);
