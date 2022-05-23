#include "ui.h"
#include "service.h"
#include<stdio.h>

int interfata()
{
	while (1)
	{
		printf("1 - Adaugare de masini \n");
		//printf("2 - Actualizare masina existenta \n");
		//printf("3 - Inchiriere/Returnare masina \n");
		//printf("4 - Vizualizare masini dupa un criteru dat (categorie, model) \n");
		//printf("5 - Sorteaza masini dupa model/categorie (crescator/descrescator) \n");
		printf("6 - Exit\n\n");
		
		printf("Introduceti o comanda: ");
		int cmd = 6;
		scanf_s("%d", &cmd);

		switch (cmd)
		{
			case 1:
			{	// adaugare masini
				//char aux[2];
				char nr_inmatr[8];
				char model[10];
				char categorie[8];
				//gets(aux);
				//strcpy(aux, "");

				printf("Introduceti numarul de imatriculare: ");
				scanf_s("%s",nr_inmatr, 8);
				//printf("\n");

				printf("Introduceti modelul masinii: ");
				scanf_s("%s", model, 10);
				//printf("\n");

				printf("Introduceti categoria masinii: ");
				scanf_s("%s",categorie, 8);
				//printf("\n");
			
				int serv = serviceAdauga(nr_inmatr, model, categorie);

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
									printf("Masina existenta!");

				break;
			}

			case 6:
			{
				return 0;
			}

			case 7:
			{
				serviceAfisare();
				break;
			}

			default:
				printf("Eroare! Comanda inexistenta! Introduceti alta comanda!\n");


		}
		
		printf("\n");

	}
}