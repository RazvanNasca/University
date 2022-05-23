#include"validare.h"
#include<string.h>

int validare(Masina* m)
{
	///printf("%s %s %s\n", m->nr_inm, m->marca, m->categorie);
	if (strlen(m->nr_inm) != 7)
		return 1;

	if (strlen(m->marca) < 1)
		return 2;

	int i;
	for (i = 0; i < strlen(m->marca); i++)
		if (strchr("0123456789", m->marca[i]) != 0)
			return 2;

	if (strcmp(m->categorie, "suv") != 0 && strcmp(m->categorie, "sport") != 0 && strcmp(m->categorie, "mini") != 0)
		return 3;

	return 0;
}