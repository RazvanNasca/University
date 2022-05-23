#include "controller.h"


char* ToString(Participant* p)
{
	return toString(p);
}


int Add(Concurs* arr, char* surname, char* name, int problem_grades[10])
{
	Participant* p = createParticipant(surname, name, problem_grades);
	DynamicArray* toUndo = copyList(arr->allPart, copyParticip);
	// daca exista deja un participant cu acelasi nume, nu se va efectua adaugarea si se returneaza 1
	int i;
	for (i = 0; i < arr->allPart->size; i++)
		if (strcmp(getSurname(arr->allPart->elements[i]), getSurname(p)) == 0 && strcmp(getName(arr->allPart->elements[i]), getName(p)) == 0)
			return 1;
	int ret = add(arr->allPart, p);
	ret = add(arr->undoList, toUndo);
	///destroyParticipant(p);  /// ??
	return ret;
}

int undo(Concurs* arr) {					
	if (arr->undoList->size == 0) {
		return 1;//no more undo
	}
	DynamicArray* l = removeLast(arr->undoList);
	destroyDynamicArray(arr->allPart);
	arr->allPart = l;
	return 0;
}


int UpdateGrade(Concurs* arr, char* surname, char* name, int position, int value)
{
	Participant* p = createParticipant(surname, name, NULL);
	DynamicArray* toUndo = copyList(arr->allPart, copyParticip);

	int i, poz = -1;
	for (i = 0; i < arr->allPart->size; i++)
		if (strcmp(getSurname(arr->allPart->elements[i]), getSurname(p)) == 0 && strcmp(getName(arr->allPart->elements[i]), getName(p)) == 0)
			poz = i;

	if (poz == -1)
		return 1;

	int ret = updateGrade(arr->allPart, poz, position - 1, value);
	ret = add(arr->undoList, toUndo);
	///ret = updateGrade(arr->undoList, poz, position - 1, value);
	///DynamicArray* toUndo = copyList(arr->allPart, copyParticip);
	//destroyParticipant(p);
	return ret;
}


int Remove(Concurs* arr, char* surname, char* name)
{
	Participant* p = createParticipant(surname, name, NULL);
	DynamicArray* toUndo = copyList(arr->allPart, copyParticip);

	int i, poz = -1;
	for (i = 0; i < arr->allPart->size; i++)
		if (strcmp(getSurname(arr->allPart->elements[i]), getSurname(p)) == 0 && strcmp(getName(arr->allPart->elements[i]), getName(p)) == 0)
			poz = i;

	if (poz == -1)
		return -1;

	int ret = rm(arr->allPart, poz);
	//DynamicArray* toUndo = copyList(arr->allPart, copyParticip);
	ret = add(arr->undoList, toUndo);
	//destroyParticipant(p);
	return ret;
}


int scoreLesserThan(Participant* p, void* value)
{
	return p->score <= *(int*)value;
}

DynamicArray* FilterScoresLesserThan(Concurs* arr, int value)
{
	int val = value;
	return filter(arr->allPart, &scoreLesserThan, &val);
}


int scoreGreaterThan(Participant* p, void* value)
{
	return p->score >= *(int*)value;
}

DynamicArray* FilterScoresGreaterThan(Concurs* arr, int value)
{
	int val = value;
	return filter(arr->allPart, &scoreGreaterThan, &val);
}

DynamicArray* FilterScoresGreaterThanAndSurname(Concurs* arr, char* surname, int value)
{
	int val = value;
	return filter2(arr->allPart, val, surname);
}

DynamicArray* FilterNameAndSurname(Concurs* arr, int value)
{
	int val = value;
	return filter3(arr->allPart, val);
}


int nameStartingWith(Participant* p, void* value)
{
	return p->name[0] == *(char*)value;
}

DynamicArray* FilterNamesStartingWith(Concurs* arr, char c)
{
	int ch = c;
	return filter(arr->allPart, &nameStartingWith, &ch);
}


int withSurname(Participant* p, void* value)
{
	return strcmp(p->surname, (char*)value) == 0;
}

DynamicArray* FilterSurnames(Concurs* arr, char* surname)
{
	return filter(arr->allPart, &withSurname, surname);
}


int compareScore(Participant* p1, Participant* p2)
{
	return p1->score < p2->score ? -1 : (p1->score == p2->score ? 0 : 1);
}

void SortByScore(Concurs* arr, int reverse)
{
	sort(arr->allPart, &compareScore, reverse);
}


int compareName(Participant* p1, Participant* p2)
{
	return strcmp(p1->surname, p2->surname) == 0 ? strcmp(p1->name, p2->name) : strcmp(p1->surname, p2->surname);
}

void SortByName(Concurs* arr, int reverse)
{
	sort(arr->allPart, &compareName, reverse);
}


Concurs createConcurs()
{
	Concurs rez;
	rez.allPart = createDynamicArray(destroyParticipant);
	rez.undoList = createDynamicArray(destroyDynamicArray);
	return rez;
}

void destroyConcurs(Concurs* store)
{
	destroyDynamicArray(store->allPart);						
	destroyDynamicArray(store->undoList);						
}
