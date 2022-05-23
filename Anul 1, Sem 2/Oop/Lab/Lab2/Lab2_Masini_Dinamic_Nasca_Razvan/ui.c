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
	printf("\n");
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
						printf("Masina inexistenta!\n");

}

void inchiriereMasina(MyList* allCars)
{

	printf("Introduceti o actiune( inchiriere / restituire ): ");
	char actiune[15];
	scanf_s("%s", actiune, 15);

	printf("Introduceti numarul de imatriculare: ");
	char nr_inmatr[8];
	scanf_s("%s", nr_inmatr, 8);

	int serv = serviceActiune(actiune, nr_inmatr, allCars);

	if (serv == 0)
		printf("Actiunea de %s a fost efectuata cu succes!", actiune);
	else
		if (serv == 1)
			printf("Masina deja inchiriata!");
		else
			if (serv == 2)
				printf("Masina deja resistuita!");
			else
				if (serv == 3)
					printf("Masina inexistenta!");
				else
					if (serv == 4)
						printf("Actiune invalida!");
	printf("\n");
}

void criteriuMasina(MyList* allCars)
{

	printf("Introduceti criteriul ( model, categorie ): ");
	char criteriu[15];
	scanf_s("%s", criteriu, 15);

	printf("Introduceti %s: ", criteriu);
	char specific[15];
	scanf_s("%s", specific, 15);

	int serv = serviceCriteriu(criteriu, specific, allCars);
	if (serv == 0)
		printf("Vizualizare reusita!\n");
	else
		if (serv == 1)
			printf("Criteriu gresit! Reincercati!\n");
		else
			if (serv == 2)
				printf("Nu exista masini din acest criteriu!\n");

	printf("\n");
}

void sorteazaMasini(MyList* allCars)
{

	printf("Introduceti criteriul ( model, categorie ): ");
	char criteriu[15];
	scanf_s("%s", criteriu, 15);

	printf("Introduceti ordinea de sortare ( crescator, descrescator ):");
	char ordine[15];
	scanf_s("%s", ordine, 15);

	int serv = serviceSortare(criteriu, ordine, allCars);
	if (serv == 0)
	{
		Afisare(allCars);
		printf("Sortare reusita!\n");
	}
	else
		if (serv == 1)
			printf("Criteriu incorect! Reintroduceti!\n");
		else
			if (serv == 2)
				printf("Ordine invalida!\n");
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
		printf("6 - Afiseaza Lista \n");
		printf("7 - Exit\n\n");

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

		case 3:
			inchiriereMasina(&allCars);
			break;

		case 4:
			criteriuMasina(&allCars);
			break;

		case 5:
			sorteazaMasini(&allCars);
			break;

		case 6:
			Afisare(&allCars);
			break;

		case 7:
			ruleaza = 0;
			break;


		default:
			printf("Comanda invalida!!!\n");
		}
	}
	distrugeStore(&allCars);

	return 0;
}