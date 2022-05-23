#pragma once
#include"domain.h"

int serviceAdaugare(char* nr_inmatr, char* model, char* categorie, MyList* cars);

void serviceAfisare(MyList* allCars);

int serviceActualizare(char* nr_inmatr, char* model, char* categorie, MyList* cars);