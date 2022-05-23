#include"validare.h"
#include<string.h>

int validare(Masina* m)
{
	///printf("%s %s %s\n", m->nr_inm, m->marca, m->categorie);
	if (strlen(m->nr_inm) != 7)
		return 1;

	if (strlen(m->marca) < 1)
		return 2;

	unsigned int i;
	for (i = 0; i < strlen(m->marca); i++)
		if (strchr("0123456789", m->marca[i]) != 0)
			return 2;

	if (strcmp(m->categorie, "suv") != 0 && strcmp(m->categorie, "sport") != 0 && strcmp(m->categorie, "mini") != 0)
		return 3;

	return 0;
}

int validActiune(char* actiune)
{
	/*  valideaza datele primite
		input: nr_inmatriculare, model, categorie -> date care urmeaza sa fie validate
		output: coduri de eroare specifice -> 0 - toate datele sunt valide;
		1 - actiune invalida
	*/

	if (strcmp(actiune, "inchiriere") == 0 || strcmp(actiune, "restituire") == 0)
		return 0;
	return 1;
}

int valideazaCriteriu(char* criteriu, char* specific)
{
	/*  valideaza datele primite
		input: nr_inmatriculare, model, categorie -> date care urmeaza sa fie validate
		output: coduri de eroare specifice -> 0 - toate datele sunt valide;
		1 - date invalide
	*/
	if (strcmp(criteriu, "model") != 0 && strcmp(criteriu, "categorie") != 0)
		return 1;

	if (strcmp(criteriu, "model") == 0)
	{
		unsigned int i;
		for (i = 0; i < strlen(specific); i++)
			if (strchr("0123456789", specific[i]) != NULL)
				return 1;
	}
	else
		if (strcmp(specific, "suv") != 0 && strcmp(specific, "sport") != 0 && strcmp(specific, "mini") != 0)
			return 1;

	return 0;
}

int validareSortare(char* criteriu, char* ordine)
{
	/*  valideaza datele primite
		input: nr_inmatriculare, model, categorie -> date care urmeaza sa fie validate
		output: coduri de eroare specifice -> 0 - toate datele sunt valide;
		1 - criteriu invalid
		2 - ordine invalida
	*/

	if (strcmp(criteriu, "model") != 0 && strcmp(criteriu, "categorie") != 0)
		return 1;

	if (strcmp(ordine, "crescator") != 0 && strcmp(ordine, "descrescator") != 0)
		return 2;

	return 0;
}