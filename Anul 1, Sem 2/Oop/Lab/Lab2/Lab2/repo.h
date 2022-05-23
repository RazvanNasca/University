#pragma once

void adauga_masina(char* nr_inmatriculare, char* model, char* categorie);

int cauta_masina(char* nr_inmatriculare);

void afisare_masini();

void actualizeaza_masina(char* nr_inmatriculare, char* model, char* categorie);

int Actiune(char* actiune, char* nr_inmatriculare);

int afiseaza_criteriu(char* criteriu);

void sortare(char* criteriu, char* ordine);