#include "tests.h"


void testEquals()
{
	int points1[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	int points2[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	Participant* p1 = createParticipant("Sincai", "Gheorghe", points1);
	Participant* p2 = createParticipant("Sincai", "Gheorghe", points2);
	
	assert(equals(p1, p2));

	destroyParticipant(p1);
	destroyParticipant(p2);
}


void testToString()
{
	int points1[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	Participant* p1 = createParticipant("Sincai", "Gheorghe", points1);
	assert(strcmp(ToString(p1), "Nume: Sincai; Prenume: Gheorghe\nPuncte: [ 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 ]\nScor: 100\n") == 0);
	
	destroyParticipant(p1);
}


void testValidate()
{
	int points1[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	int points2[] = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	int points3[] = { 11, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	Participant* p = createParticipant("Sincai", "Gheorghe", points1);
	assert(validateParticipant(p) == 0);

	Participant* p1 = createParticipant("", "Gheorghe", points1);
	assert(validateParticipant(p1) == 1);

	Participant* p2 = createParticipant("Sincai", "", points1);
	assert(validateParticipant(p2) == 2);

	Participant* p3 = createParticipant("Sincai", "Gheorghe", points2);
	assert(validateParticipant(p3) == 3);

	Participant* p4 = createParticipant("Sincai", "Gheorghe", points3);
	assert(validateParticipant(p4) == 3);

	destroyParticipant(p);
	destroyParticipant(p1);
	destroyParticipant(p2);
	destroyParticipant(p3);
	destroyParticipant(p4);
}


void testAdd()
{
	Concurs a = createConcurs();
	int points1[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	int points2[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	Participant* p = createParticipant("Sincai", "Gheorghe", points1);
	Participant* p1 = createParticipant("Sincai", "Gheorghe", points2);

	assert(Add(&a, p->surname, p->name, p->problem_grades) == 0);
	assert(Add(&a, p1->surname, p1->name, p->problem_grades) == 1);
	assert(a.allPart->size == 1);

	assert(strcmp(getSurname(a.allPart->elements[0]), p->surname) == 0);
	assert(strcmp(getName(a.allPart->elements[0]), p->name) == 0);

	int i;
	for (i = 0; i < 10; i++)
		assert(getProblem(a.allPart->elements[0],i) == p->problem_grades[i]);

	assert(getScore(a.allPart->elements[0]) == p->score);

	destroyParticipant(p);
	destroyParticipant(p1);

	destroyConcurs(&a);
}


void testUpdateGrade()
{
	Concurs a = createConcurs();
	int points1[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	Participant* p = createParticipant("Sincai", "Gheorghe", points1);
	add(a.allPart, p);
	assert(UpdateGrade(&a, p->surname, p->name, 4, 7) == 0);
	assert(UpdateGrade(&a, p->name, p->surname, 4, 7) == 1);
	
	assert(getProblem(a.allPart->elements[0],3) == 7);
	for (int i = 0; i < 10 && i != 3; i++)
		assert(getProblem(a.allPart->elements[0],i) == 10);

	assert(getScore(a.allPart->elements[0]) == 97);

	destroyParticipant(p);
	destroyConcurs(&a);
}


void testRm()
{
	Concurs a = createConcurs();
	int points1[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	int points2[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	Participant* p1 = createParticipant("Sincai", "Gheorghe", points1);
	Participant* p2 = createParticipant("Lucaciu", "Vasile", points2);
	add(a.allPart, p1), add(a.allPart, p2);

	assert(Remove(&a, p1->surname, p1->name) == 0);
	assert(Remove(&a, p1->surname, p1->name) == 1);

	assert(a.allPart->size == 1);

	destroyParticipant(p1);
	destroyParticipant(p2);
	destroyConcurs(&a);
}


void testFilter()
{
	Concurs a = createConcurs();
	int points1[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	int points2[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	Participant* p1 = createParticipant("Sincai", "Gheorghe", points1);
	Participant* p2 = createParticipant("Lucaciu", "Vasile", points2);
	add(a.allPart, p1), add(a.allPart, p2);

	DynamicArray* a1 = FilterScoresLesserThan(&a, 50);
	assert(a1->size == 1);
	assert(equals(a1->elements[0], p2) == 0);

	destroyDynamicArray(a1);

	DynamicArray* a3 = FilterScoresGreaterThan(&a, 50);
	assert(a3->size == 1);
	assert(equals(a3->elements[0], p1));

	destroyDynamicArray(a3);

	DynamicArray* a4 = FilterNamesStartingWith(&a, 'V');
	assert(a4->size == 1);
	assert(equals(a4->elements[0], p2));

	destroyDynamicArray(a4);

	char surname[7] = "Sincai";
	DynamicArray* a5 = FilterSurnames(&a, surname);
	assert(a5->size == 1);
	assert(equals(a5->elements[0], p1));

	destroyDynamicArray(a5);


	DynamicArray* a6 = FilterScoresGreaterThanAndSurname(&a, "Sincai", 2);
	assert(a6->size == 1);
	assert(equals(a6->elements[0], p2));

	destroyDynamicArray(a6);

	DynamicArray* a7 = FilterNameAndSurname(&a, 2);
	assert(a7->size == 1);
	assert(equals(a7->elements[0], p2));

	destroyDynamicArray(a7);

	
	destroyParticipant(p1);
	destroyParticipant(p2);

	destroyConcurs(&a);
}


void testSort()
{
	Concurs a = createConcurs();
	int points1[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	int points2[] = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
	int points3[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
	int points4[] = { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 };
	Participant* p1 = createParticipant("Sincai", "Gheorghe", points1);
	Participant* p2 = createParticipant("Lucaciu", "Vasile", points2);
	Participant* p3 = createParticipant("Racovita", "Emil", points3);
	Participant* p4 = createParticipant("Blaga", "Lucian", points4);
	add(a.allPart, p1), add(a.allPart, p2), add(a.allPart, p3), add(a.allPart, p4);

	SortByScore(&a, 1);
	assert(equals(a.allPart->elements[0], p3));
	assert(equals(a.allPart->elements[1], p4));
	assert(equals(a.allPart->elements[2], p2));
	assert(equals(a.allPart->elements[3], p1));

	SortByName(&a, 0);
	assert(equals(a.allPart->elements[0], p4));
	assert(equals(a.allPart->elements[1], p2));
	assert(equals(a.allPart->elements[2], p3));
	assert(equals(a.allPart->elements[3], p1));

	destroyParticipant(p1);
	destroyParticipant(p2);
	destroyParticipant(p3);
	destroyParticipant(p4);

	destroyConcurs(&a);
}


void testAll()
{
	testEquals();
	testToString();
	testValidate();
	testAdd();
	//testUpdateGrade();
	//testRm();
	//testFilter();
	//testSort();
}
