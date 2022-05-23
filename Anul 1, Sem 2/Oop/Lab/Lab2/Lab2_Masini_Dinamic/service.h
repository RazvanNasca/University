#pragma once
#include"domain.h"

int serviceAdaugare(char* nr_inmatr, char* model, char* categorie, MyList* cars);

void serviceAfisare(MyList* allCars);

int serviceActualizare(char* nr_inmatr, char* model, char* categorie, MyList* cars);

int serviceActiune(char* actiune, char* nr_inmatr, MyList* cars);

int serviceCriteriu(char* criteriu, char* specific, MyList* cars);

int serviceSortare(char* criteriu, char* ordine, MyList* cars);

