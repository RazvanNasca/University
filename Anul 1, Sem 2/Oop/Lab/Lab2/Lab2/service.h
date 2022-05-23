#pragma once

int serviceAdauga(char* nr_inmatriculare, char* model, char* categorie);

void serviceAfisare();

int serviceActualizare(char* nr_inmatr, char* model, char* categorie);

int serviceActiune(char* actiune, char* nr_inmatr);

int serviceCriteriu(char* criteriu, char* specific);

int serviceSortare(char* criteriu, char* ordine);
