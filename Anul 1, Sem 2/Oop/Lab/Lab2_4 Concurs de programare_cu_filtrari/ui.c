#pragma once

#include "ui.h"


void ui_help()
{
	printf("Lista de comenzi:\n\n");
	printf("afiseaza\n\tAfiseaza lista de participanti, cu atributele fiecaruia <Nume, Prenume, Puncte, Scor>\n");
	printf("adauga (Nume) (Prenume) (Puncte)\n\tAdauga in lista un participant cu numele, prenumele si punctajele date\n\t<Puncte> este o lista de 10 numere cu valori intre 1-10 separate prin cate un spatiu\n\n");
	printf("actualizeaza (Nume) (Prenume) (Pozitie) (Punctaj)\n\tActualizeaza nota de pe pozitia <Pozitie> a participantului dat\n\n");
	printf("sterge (Nume) (Prenume)\n\tSterge participantul cu numele si prenumele date\n\n");
	printf("filtreaza (scor<= | scor>= | prenumeIncepeCu | nume | nume_scor) (Valoare)\n\tFiltreaza participantii care respecta o anumita conditie si ii afiseaza pe ecran\n\n");
	printf("ordoneaza (nume | scor) (crescator | descrescator)\n\tOrdoneaza lista de participanti dupa nume sau dupa scor, crescator sau descrescator\n\n");
	printf("iesire\n\tIese din program\n\n");
}


void ui_afiseaza(DynamicArray* arr)
{
	int i;
	char* s;
	for (i = 0; i < arr->size; i++)
	{
		s = ToString(arr->elements[i]);
		printf("%s\n", s);
		free(s);
	}

	if (arr->size == 0)
		printf("Nu exista niciun participant in lista!\n");
}


void ui_adauga(char** argv, int argc)
{
	if (argc != 12)
	{
		printf("Comanda are nevoie de 12 argumente! (Nume, Prenume, Puncte[10])\n");
		return;
	}

	for (int i = 0; i < 10; i++)
	{
		if (atoi(argv[2 + i]) < 1 || atoi(argv[2 + i]) > 10)
		{
			printf("Punctajele trebuie sa fie intre 1 si 10!\n");
			return;
		}
	}

	char* nume = malloc(strlen(argv[0]) + 1);
	char* prenume = malloc(strlen(argv[1]) + 1);
	if (nume != NULL && prenume != NULL)
	{
		strcpy(nume, argv[0]);
		strcpy(prenume, argv[1]);
	}
	else return;

	int puncte[10];
	for (int i = 0; i < 10; i++)
		puncte[i] = atoi(argv[2 + i]);

	if (Add(&participanti, nume, prenume, puncte) == 0)
		printf("Adaugare efectuata cu succes!\n");
	else
		printf("Exista deja un participant cu numele dat!\n");

	free(nume);
	free(prenume);
}


void ui_actualizeaza(char** argv, int argc)
{
	if (argc != 4)
	{
		printf("Comanda are nevoie de 4 argumente! (Nume, Prenume, Pozitie, Valoare Noua)\n");
		return;
	}

	char* nume = malloc(strlen(argv[0]) + 1);
	char* prenume = malloc(strlen(argv[1]) + 1);
	if (nume != NULL && prenume != NULL)
	{
		strcpy(nume, argv[0]);
		strcpy(prenume, argv[1]);
	}

	int pozitie = atoi(argv[2]);
	int valoare = atoi(argv[3]);

	if (pozitie < 1 || pozitie > 10)
	{
		printf("Pozitia trebuie sa fie intre 1 si 10!\n");
		free(nume);
		free(prenume);
		return;
	}
	if (valoare < 1 || valoare > 10)
	{
		printf("Valoarea noua trebuie sa fie intre 1 si 10!\n");
		free(nume);
		free(prenume);
		return;
	}

	if (UpdateGrade(&participanti, nume, prenume, pozitie, valoare) == 0)
		printf("Modificare efectuata cu succes!\n");
	else
		printf("Nu exista niciun participant cu numele dat!\n");

	free(nume);
	free(prenume);
}


void ui_sterge(char** argv, int argc)
{
	if (argc != 2)
	{
		printf("Comanda are nevoie de 2 argumente! (Nume, Prenume)\n");
		return;
	}

	char* nume = malloc(strlen(argv[0]) + 1);
	char* prenume = malloc(strlen(argv[1]) + 1);
	if (nume != NULL && prenume != NULL)
	{
		strcpy(nume, argv[0]);
		strcpy(prenume, argv[1]);
	}
	else return;

	if (Remove(&participanti, nume, prenume) == 0)
		printf("Stergere efectuata cu succes!\n");
	else
		printf("Nu exista niciun participant cu numele dat!\n");

	free(nume);
	free(prenume);
}


void ui_filtreaza(char** argv, int argc)
{
	if (argc != 2)
	{
		printf("Comanda are nevoie de 2 argumente! (Optiune, Valoare)\n");
		return;
	}

	char* optiune = malloc(strlen(argv[0]) + 1);
	if (optiune != NULL)
		strcpy(optiune, argv[0]);
	else return;
	
	char* valoare = malloc(strlen(argv[1]) + 1);
	if (valoare != NULL)
		strcpy(valoare, argv[1]);
	else return;

	DynamicArray* f;

	if (strcmp(optiune, "scor<=") == 0)
		f = FilterScoresLesserThan(&participanti, atoi(valoare));
	else if (strcmp(optiune, "scor>=") == 0)
		f = FilterScoresGreaterThan(&participanti, atoi(valoare));
	else if (strcmp(optiune, "prenumeIncepeCu") == 0)
	{
		if (strlen(valoare) != 1)
		{
			printf("Valoarea trebuie sa fie un singur caracter!\n");
			free(optiune);
			free(valoare);
			return;
		}

		f = FilterNamesStartingWith(&participanti, (char)toupper(valoare[0]));
	}
	else if (strcmp(optiune, "nume") == 0)
		f = FilterSurnames(&participanti, valoare);
	else 
		if (strcmp(optiune, "nume_scor") == 0)
		{
			printf("\nIntroduceti valoarea scorului de referinta: ");
			int valoare2 = 2;
			scanf_s("%d", &valoare2);

			f = FilterScoresGreaterThanAndSurname(&participanti, valoare, valoare2);
		}
		else
			if (strcmp(optiune, "nume_prenume") == 0)
				f = FilterNameAndSurname(&participanti, atoi(valoare));
	else
	{
		printf("Optiune invalida!");
		free(optiune);
		free(valoare);
		return;
	}

	if (f->size == 0)
	{
		printf("Nu s-a gasit niciun participant care sa respecte criteriile date!\n");
		return;
	}
	ui_afiseaza(f);

	destroyDynamicArray(f);

	free(optiune);
	free(valoare);
}


void ui_ordoneaza(char** argv, int argc)
{
	if (argc != 2)
	{
		printf("Comanda are nevoie de 2 argumente! (OrdonareDupa, crescator|descrescator)\n");
		return;
	}

	char* ordonare_dupa = malloc(strlen(argv[0]) + 1);
	char* crescDescresc = malloc(strlen(argv[1]) + 1);
	if (ordonare_dupa != NULL && crescDescresc != NULL)
	{
		strcpy(ordonare_dupa, argv[0]);
		strcpy(crescDescresc, argv[1]);

		int descrescator = 0;
		if (strcmp(crescDescresc, "crescator") == 0)
			descrescator = 0;
		else if (strcmp(crescDescresc, "descrescator") == 0)
			descrescator = 1;
		else
		{
			printf("Ordine invalida!");
			free(ordonare_dupa);
			free(crescDescresc);
			return;
		}

		if (strcmp(ordonare_dupa, "nume") == 0)
			SortByName(&participanti, descrescator);
		else if (strcmp(ordonare_dupa, "scor") == 0)
			SortByScore(&participanti, descrescator);
		else
		{
			printf("Optiune invalida!");
			free(ordonare_dupa);
			free(crescDescresc);
			return;
		}

		ui_afiseaza(participanti.allPart);

		free(ordonare_dupa);
	}
	else return;
}


int parseCommand(char* cmd)
{
	if (cmd == NULL)
		return -1;

	int i;
	// comenzile sunt cAsE iNsEnSiTiVe
	for (i = 0; i < strlen(cmd); i++)
		cmd[i] = (char)tolower(cmd[i]);

	if (strcmp(cmd, "help") == 0)
		return 1;
	if (strcmp(cmd, "afiseaza") == 0)
		return 2;
	if (strcmp(cmd, "adauga") == 0)
		return 3;
	if (strcmp(cmd, "actualizeaza") == 0)
		return 4;
	if (strcmp(cmd, "sterge") == 0)
		return 5;
	if (strcmp(cmd, "filtreaza") == 0)
		return 6;
	if (strcmp(cmd, "ordoneaza") == 0)
		return 7;
	if (strcmp(cmd, "undo") == 0)
		return 8;
	if (strcmp(cmd, "iesire") == 0)
		return 9;

	return 0;
}


void run()
{
	participanti = createConcurs();
	// nu se stie cu exactitate cat de lung va fi inputul, nici numarul de argumente
	char input[256], * cmd, *(argv[16]);
	int argc;

	printf("Lab2_4 Concurs de programare\n");
	printf("Tastati 'help' pentru a afisa lista de comenzi\n\n");

	for (;;)
	{
		printf("$: ");
		fgets(input, CMD_LEN, stdin);
		// fgets citeste pana la \n inclusiv, si trebuie eliminat \n pentru a evita probleme mai tarziu
		input[strlen(input) - 1] = '\0';

		// separa inputul in comanda si argumente
		cmd = strtok(input, " ");

		argc = 0;
		argv[argc] = strtok(NULL, " ");
		while (argv[argc] != NULL)
		{
			argv[++argc] = strtok(NULL, " ");
		}

		switch (parseCommand(cmd))
		{
		case 1:  // help
			ui_help();
			break;
		case 2:  // afiseaza
			ui_afiseaza(participanti.allPart);
			break;
		case 3:  // adauga
			ui_adauga(argv, argc);
			break;
		case 4:  // actualizeaza
			ui_actualizeaza(argv, argc);
			break;
		case 5:  // sterge
			ui_sterge(argv, argc);
			break;
		case 6: // filtreaza
			ui_filtreaza(argv, argc);
			break;
		case 7: // ordoneaza
			ui_ordoneaza(argv, argc);
			break;
		case 8: // undo
			if (undo(&participanti) != 0) {		
				printf("No more undo!!!\n");
			}
			break;
		case 9: // iesire
			destroyConcurs(&participanti);
			exit(0);
		default: // invalid
			printf("Comanda invalida!\n");
		}
	}
}
