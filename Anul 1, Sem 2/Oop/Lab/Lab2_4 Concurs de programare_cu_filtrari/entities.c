#include "entities.h"
#include "entities.h"
#include <stdio.h>

Participant* createParticipant(char* surname, char* name, int problem_grades[10])
{
	Participant* p = malloc(sizeof(Participant));
	if (p != NULL)
	{
		p->surname = malloc(strlen(surname) + 1);
		p->name = malloc(strlen(name) + 1);
		if (p->surname != NULL && p->name != NULL)
		{
			strcpy(p->surname, surname);
			strcpy(p->name, name);
		}
	}
	else return NULL;

	int i, sum = 0;
	if (problem_grades != NULL && p != NULL)
	{
		for (i = 0; i < 10; i++)
		{
			p->problem_grades[i] = problem_grades[i];
			sum += problem_grades[i];
		}
		p->score = sum;
	}

	return p;
}


int validateParticipant(Participant* p)
{
	if (p->surname == NULL || strcmp(p->surname, "") == 0)
		return 1;
	if (p->name == NULL || strcmp(p->name, "") == 0)
		return 2;

	int i;
	for (i = 0; i < 10; i++)
		if (p->problem_grades[i] < 1 || p->problem_grades[i] > 10)
			return 3;

	return 0;
}


int copyParticipant(Participant* dest, Participant* source)
{
	dest->surname = malloc(strlen(source->surname) + 1);
	dest->name = malloc(strlen(source->name) + 1);
	if (dest->surname != NULL && dest->name != NULL)
	{
		strcpy(dest->surname, source->surname);
		strcpy(dest->name, source->name);
	}
	else return 1;	// nu s-a putut aloca memorie pentru nume si prenume
	int i;
	for (i = 0; i < 10; i++)
		dest->problem_grades[i] = source->problem_grades[i];
	dest->score = source->score;
	return 0;
}


void destroyParticipant(Participant* p)
{
	free(p->surname);
	free(p->name);
	free(p);
}


char* toString(Participant* p)
{
	char* s = (char*)malloc(512);
	if (s != NULL && s != 0)
	{
		sprintf(s, "Nume: %s; Prenume: %s\nPuncte: [ ", p->surname, p->name);
		int i;
		for (i = 0; i < 10; i++)
		{
			char buffer[4];
			_itoa(p->problem_grades[i], buffer, 10);
			strcat(s, buffer);
			if (i < 9)
				strcat(s, ", ");
		}
		sprintf(s + strlen(s), " ]\nScor: %d\n", p->score);
		return s;
	}
	return NULL;
}


int equals(Participant* p1, Participant* p2)
{
	return strcmp(p1->surname, p2->surname) == 0 && strcmp(p1->name, p2->name) == 0;
}

Participant* copyParticip(Participant* p)
{
	return createParticipant(p->surname, p->name, p->problem_grades);
}

int getScore(Participant* p)
{
	return p->score;
}

int getProblem(Participant* p, int index)
{
	return p->problem_grades[index];
}

void setScore(Participant* p, int val)
{
	p->score = val;
}

void setProblem(Participant* p, int index, int val)
{
	p->problem_grades[index] = val;
}

char* getSurname(Participant* p)
{
	return p->surname;
}

char* getName(Participant* p)
{
	return p->name;
}