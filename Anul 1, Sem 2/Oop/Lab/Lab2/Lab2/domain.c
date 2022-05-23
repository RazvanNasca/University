#include<stdio.h>
#include<string.h>
#include "domain.h"


void initializare()
{
	int i;
	nr_masini = 30;
	for (i = 1; i <= nr_masini; i++)
	{
		/*int j;
		for (j = 0; j < 30; j++)
			masina[i].categorie[j] = masina[i].marca[j] = masina[i].nr_inm[j] = '1';*/
		strcpy_s(masina[i].categorie, 29, "");
		strcpy_s(masina[i].marca, 29, "");
		strcpy_s(masina[i].nr_inm, 29, "");
		masina[i].ok = 0;
		masina[i].inchiriata = 0; /// toate masinile sunt disponibile
	}
}