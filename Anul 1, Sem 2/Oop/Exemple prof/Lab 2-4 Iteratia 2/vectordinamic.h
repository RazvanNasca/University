#pragma once
#include "participant.h"

typedef struct
{
	Participant* participanti;
	int lungime;
	int capacitate;
}Vector;

Vector* creareVector();

void distrugeVector(Vector* v);