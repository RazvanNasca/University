#include "ui.h"
#include "service.h"
#include "domain.h"
#include<stdio.h>


void citireMasina(MyList* allCars)
{
	printf("Introduceti numarul de imatriculare: ");
	char nr_inmatr[8];
	scanf_s("%s", nr_inmatr, 8);

	printf("Introduceti modelul masinii: ");
	char model[10];
	scanf_s("%s", model, 10);

	printf("Introduceti categoria masinii: ");
	char categorie[8];
	scanf_s("%s", categorie, 8);


	///printf("%s %s %s\n", nr_inmatr, model, categorie);

	int serv = serviceAdaugare(nr_inmatr, model, categorie, allCars);

	if (serv == 0)
		printf("Masina a fost adaugata cu succes!\n");
	else
		if (serv == 1)
			printf("Numar de inmatriculare inavalid! Reintroduceti!\n");
		else
			if (serv == 2)
				printf("Modelul masinii esti invalid! Reintroduceti!\n");
			else
				if (serv == 3)
					printf("Categorie invalida! Reintoduceti!\n");
				else
					if (serv == 4)
						printf("Masina existenta!\n");

}

void Afisare(MyList* allCars)
{
	serviceAfisare(allCars);
}

void actualizareMasina(MyList* allCars)
{
	printf("Introduceti numarul de imatriculare: ");
	char nr_inmatr[8];
	scanf_s("%s", nr_inmatr, 8);

	printf("Introduceti modelul masinii: ");
	char model[10];
	scanf_s("%s", model, 10);

	printf("Introduceti categoria masinii: ");
	char categorie[8];
	scanf_s("%s", categorie, 8);

	int serv = serviceActualizare(nr_inmatr, model, categorie, allCars);

	if (serv == 0)
		printf("Masina a fost actualizata cu succes!\n");
	else
		if (serv == 1)
			printf("Numar de inmatriculare inavalid! Reintroduceti!\n");
		else
			if (serv == 2)
				printf("Modelul masinii esti invalid! Reintroduceti!\n");
			else
				if (serv == 3)
					printf("Categorie invalida! Reintoduceti!\n");
				else
					if (serv == 4)
						printf("Masina inexistenta!");

}


int interfata()
{
	MyList allCars = creareLista();
	int ruleaza = 1;
	while (ruleaza)
	{
		printf("1 - Adaugare de masini \n");
		printf("2 - Actualizare masina existenta \n");
		printf("3 - Inchiriere/Returnare masina \n");
		printf("4 - Vizualizare masini dupa un criteru dat (categorie, model) \n");
		printf("5 - Sorteaza masini dupa model/categorie (crescator/descrescator) \n");
		printf("6 - Exit\n\n");

		printf("Introduceti o comanda: ");
		int cmd = 0;
		scanf_s("%d", &cmd);

		switch (cmd) {
		case 1:
			citireMasina(&allCars);
			break;

		case 2:
			actualizareMasina(&allCars);
			break;
		
		case 6:
			ruleaza = 0;
			break;

		case 7:
			Afisare(&allCars);
			break;

		default:
			printf("Comanda invalida!!!\n");
		}
	}
	distrugeStore(&allCars);

	return 0;
}