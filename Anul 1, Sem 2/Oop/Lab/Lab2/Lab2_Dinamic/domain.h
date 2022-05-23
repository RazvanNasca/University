#pragma once

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

MyList creareLista();

Masina creareMasina(char* nr_inmatr, char* model, char* categorie);

void distrugeStore(MyList* l);

void distrugeMasina(Masina* car);