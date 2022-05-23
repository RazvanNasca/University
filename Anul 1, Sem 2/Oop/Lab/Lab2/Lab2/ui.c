#include "ui.h"
#include "service.h"
#include<stdio.h>

int interfata()
{
	while (1)
	{
		printf("1 - Adaugare de masini \n");
		printf("2 - Actualizare masina existenta \n");
		printf("3 - Inchiriere/Returnare masina \n");
		printf("4 - Vizualizare masini dupa un criteru dat (categorie, model) \n");
		printf("5 - Sorteaza masini dupa model/categorie (crescator/descrescator) \n");
		printf("6 - Exit\n\n");
		
		printf("Introduceti o comanda: ");
		int cmd = 6;
		scanf_s("%d", &cmd);

		switch (cmd)
		{
			case 1:
			{	// adaugare masini
				char aux[2];
				char nr_inmatr[8];
				char model[10];
				char categorie[8];
				gets(aux);
				strcpy(aux, "");

				printf("Introduceti numarul de imatriculare: ");
				gets(nr_inmatr);
				//printf("\n");

				printf("Introduceti modelul masinii: ");
				gets(model);
				//printf("\n");

				printf("Introduceti categoria masinii: ");
				gets(categorie);
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

			case 2:
			{
				/// actualizare masina
				char aux[2];
				char nr_inmatr[8];
				char model[10];
				char categorie[8];
				gets(aux);
				strcpy(aux, "");

				printf("Introduceti numarul de imatriculare: ");
				gets(nr_inmatr);

				printf("Introduceti modelul masinii: ");
				gets(model);

				printf("Introduceti categoria masinii: ");
				gets(categorie);

				int serv = serviceActualizare(nr_inmatr, model, categorie);

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

				break;
			}

			case 3:
			{
				/// inchiriere/returnare masina

				char aux[2];
				char nr_inmatr[8];
				char actiune[15];
				gets(aux);
				strcpy(aux, "");
				
				printf("Introduceti o actiune( inchiriere / restituire ): ");
				gets(actiune);

				printf("Introduceti numarul de imatriculare: ");	
				gets(nr_inmatr);

				int serv = serviceActiune(actiune, nr_inmatr);

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

				break;
			}

			case 4:
			{ 
				/// vizualizeaza dupa un criteriu
				char aux[2];
				char criteriu[15];
				char specific[15];

				gets(aux);
				strcpy(aux, "");

				printf("Introduceti criteriul ( model, categorie ): ");
				gets(criteriu);

				printf("Introduceti %s: ", criteriu);
				gets(specific);
				printf("\n");

				int serv = serviceCriteriu(criteriu, specific);
				if (serv == 0)
					printf("Vizualizare reusita!\n");
				else
					if(serv == 1)
						printf("Criteriu gresit! Reincercati!\n");
					else 
						if(serv == 2)
							printf("Nu exista masini din acest criteriu!\n");

				printf("\n");

				break;
			}

			case 5:
			{
				/// sortare
				char aux[2];
				char criteriu[15];
				char ordine[15];

				gets(aux);
				strcpy(aux, "");

				printf("Introduceti criteriul ( model, categorie ): ");
				gets(criteriu);

				printf("Introduceti ordinea de sortare ( crescator, descrescator ):");
				gets(ordine);
				printf("\n");

				int serv = serviceSortare(criteriu, ordine);
				if (serv == 0)
					printf("Sortare reusita!\n");
				else
					if (serv == 1)
						printf("Criteriu incorect! Reintroduceti!\n");
					else
						if (serv == 2)
							printf("Ordine invalida!\n");

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