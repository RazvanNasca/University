#pragma once

typedef struct {
	
	char* marca;
	char* nr_inm;
	char* categorie;
	int inchiriata;

}Masina;

typedef struct {

	Masina* masina;
	int nr_masini;
	int capacitate;

}MyList;

MyList* allCars;

void initializare();

void distrugeStore();

void distrugeMasina(Masina* car);