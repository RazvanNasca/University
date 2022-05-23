#pragma once

typedef struct {

	char marca[31];
	char nr_inm[31];
	char categorie[31];
	int ok;  /// locul este disponibil
	int inchiriata;

}MyList;

MyList masina[31];

int nr_masini;

void initializare();