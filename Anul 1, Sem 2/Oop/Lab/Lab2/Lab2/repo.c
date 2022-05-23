#include <stdio.h>
#include <string.h>
#include "repo.h"
#include "domain.h"

void afisare_masini()
{
	/*
		afiseaza lista de masini
	*/

	int i;
	for (i = 1; i <= nr_masini; i++)
		if (masina[i].ok == 1)
			printf("%s %s %s\n", masina[i].nr_inm, masina[i].marca, masina[i].categorie);
}

int cauta_masina(char* nr_inmatriculare)
{
	/*
		cauta masini dupa numarul de inmatriculare
		input: nr_inmatriculare - unic pentru fiecare masina
		output: 1 - masina e in lista; 0 - masina nu e in lista
	*/

	int gasit = 0;
	int i;
	for (i = 1; i <= nr_masini && gasit == 0; i++)
		if (strcmp(masina[i].nr_inm, nr_inmatriculare) == 0)
			gasit = 1;

	return gasit;
}

void adauga_masina(char* nr_inmatriculare, char* model, char* categorie)
{
	/*
		adauga o masina in lista
		input: nr_inmatriculare - numarul de inmatriculare; model - modelul masinii; categorie - categoria masinii
		output: modifica lista
	*/

	int poz = 0;
	int i;
	for (i = 1; i <= nr_masini && poz == 0; i++)
		if (masina[i].ok == 0)
			poz = i;
	
	for (i = 0; i < strlen(nr_inmatriculare); i++)
		masina[poz].nr_inm[i] = nr_inmatriculare[i];
	masina[poz].nr_inm[i] = '\0';

	for (i = 0; i < strlen(model); i++)
		masina[poz].marca[i] = model[i];
	masina[poz].marca[i] = '\0';

	for (i = 0; i < strlen(categorie); i++)
		masina[poz].categorie[i] = categorie[i];
	masina[poz].categorie[i] = '\0';

	/*strcpy_s(masina[i].nr_inm, strlen(nr_inmatriculare) * sizeof(char), nr_inmatriculare);
	strcpy_s(masina[i].marca, strlen(model) * sizeof(char), model);
	strcpy_s(masina[i].categorie, strlen(categorie) * sizeof(char), categorie);*/

	masina[poz].ok = 1;
	//i = poz;

	//printf("%s %s %s %d\n", masina[i].nr_inm, masina[i].marca, masina[i].categorie, masina[i].ok);
}

void actualizeaza_masina(char* nr_inmatriculare, char* model, char* categorie)
{
	/*
		actuaizeaza datele unei masini
		input: nr_inmatriculare - numarul de inmatriculare; model - modelul masinii; categorie - categoria masinii
		output: actualizeaza lista
	*/

	int i;
	for(i = 1; i <= nr_masini; i++)
		if (strcmp(masina[i].nr_inm, nr_inmatriculare) == 0)
		{
			int j;
			for (j = 0; j < strlen(model); j++)
				masina[i].marca[j] = model[j];
			masina[i].marca[j] = '\0';

			for (j = 0; j < strlen(categorie); j++)
				masina[i].categorie[j] = categorie[j];
			masina[j].categorie[j] = '\0';
		}
}

int Actiune(char* actiune, char* nr_inmatriculare)
{
	/*
		aplica actiunea primita unei masini din lista
		input: actiune - operatia care urmeaza sa fie facuta, nr_inmatriculare - numarul de inmatriculare a masinii asupra careia urmeaza sa se faca operatia
		output: efectuarea actiunii
	*/

	int i, poz = 0;
	for (i = 1; i <= nr_masini && poz == 0; i++)
		if (strcmp(nr_inmatriculare, masina[i].nr_inm) == 0)
			poz = i;

	if (strcmp(actiune, "inchiriere") == 0)
		if (masina[poz].inchiriata == 0)
		{
			masina[poz].inchiriata = 1;
			return 0;
		}
		else
			return 1;
	else
		if (masina[poz].inchiriata == 1) //masina este in momentul asta inchiriata
		{
			masina[poz].inchiriata = 0;	//masina este restituita
			return 0;
		}
		else
			return 2;
}

int afiseaza_criteriu(char* criteriu)
{
	/*
		afisarea listei care respecta un criteriu dat
	*/

	int i, gasit = 0;
	
	for (i = 1; i <= nr_masini; i++)
		if (strcmp(masina[i].categorie, criteriu) == 0 || strcmp(masina[i].marca, criteriu) == 0)
			printf("%s %s %s\n", masina[i].nr_inm, masina[i].marca, masina[i].categorie), gasit = 1;

	printf("\n");
	 
	if (gasit == 0)
		return 2;
	return 0;

}

void sortare(char* criteriu, char* ordine)
{
	/*
		sorteaza lista data dupa o ordine precizata
		input: criteriu - criteriul dupa care sortam,  ordine - ordinea dupa care se sorteaza
		output: lista sortata
	*/
	if (strcmp(ordine, "crescator") == 0)
	{
		if (strcmp(criteriu, "model") == 0)
		{
			int i, j;
			for(i = 1; i < nr_masini; i++)
				for(j = i + 1; j <= nr_masini; j++)
					if (strcmp(masina[i].marca, masina[j].marca) > 0)
					{
						MyList aux = masina[i];
						masina[i] = masina[j];
						masina[j] = aux;
					}
		}
		else
		{
			int i, j;
			for (i = 1; i < nr_masini; i++)
				for (j = i + 1; j <= nr_masini; j++)
					if (strcmp(masina[i].categorie, masina[j].categorie) > 0)
					{
						MyList aux = masina[i];
						masina[i] = masina[j];
						masina[j] = aux;
					}
		}
	}
	else
	{
		if (strcmp(criteriu, "model") == 0)
		{
			int i, j;
			for (i = 1; i < nr_masini; i++)
				for (j = i + 1; j <= nr_masini; j++)
					if (strcmp(masina[i].marca, masina[j].marca) < 0)
					{
						MyList aux = masina[i];
						masina[i] = masina[j];
						masina[j] = aux;
					}
		}
		else
		{
			int i, j;
			for (i = 1; i < nr_masini; i++)
				for (j = i + 1; j <= nr_masini; j++)
					if (strcmp(masina[i].categorie, masina[j].categorie) < 0)
					{
						MyList aux = masina[i];
						masina[i] = masina[j];
						masina[j] = aux;
					}
		}
	}

	afisare_masini();
}