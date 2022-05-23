#pragma once

#include "entities.h"

#define INIT_DYNAMIC_ARRAY_DIM 10


typedef void* TElem;

typedef int (*ComparisonFunction)(Participant*, void*);  //ComparisonFunction f <=> int (*f)(Participant* p, void* value)
typedef int (*ConditionFunction)(Participant*, Participant*);  // ConditionFunction f <=> int (*f)(Participant* p1, Participant* p2)

typedef void(*DestroyFct) (TElem);
typedef TElem(*CopyFct) (TElem);


typedef struct
{
	TElem* elements;
	int size, dimension;
	DestroyFct dfnc;
} DynamicArray;


/*
	Creeaza un vector dinamic

	@param arr  Vectorul dinamic care urmeaza a fi initializat
*/
DynamicArray* createDynamicArray(DestroyFct f);

/*
	Distruge un vector dinamic din memorie

	@param arr  Vectorul dinamic care urmeaza a fi distrus
*/
void destroyDynamicArray(DynamicArray* arr);


/*
	Adauga un participant in repository

	@param arr   Vectorul dinamic in care se va adauga participantul
	@param p     Participantul care va fi adaugat in repository

	@return      false, daca exista deja un participant cu acelasi nume si prenume, true, in caz contrar
*/
int add(DynamicArray* arr, TElem p);


/**
	Actualizeaza punctajul unei probleme pentru un participant dat, impreuna cu scorul acestuia

	@param arr       Vectorul dinamic in care se va cauta participantul
	@param p         Participantul caruia i se va modifica un punctaj
	@param position  Pozitia corespunzatoare problemei pentru care se va modifica punctajul
	@param value     Valoarea noua a punctajului

	@return          false, daca participantul dat nu se gaseste in lista, true, in caz contrar
*/
int updateGrade(DynamicArray* arr, int poz, int position, int value);


/**
	Sterge un participant din repository

	@param arr   Vectorul dinamic in care se va cauta participantul
	@param p     Participantul care urmeaza a fi sters

	@return      false, daca participantul dat nu se gaseste in lista, true, in caz contrar
*/
int rm(DynamicArray* arr, int poz);


/*
	Filtreaza participantii din repository care respecta o anumita conditie

	@param  arr         Vectorul dinamic in care se vor filtra participanti
	@param  condition   O functie care compara atributele unui participant cu o valoare,
						returneaza 1 daca conditia e respectata si 0 in caz contrar
	@param  value       Valoarea cu care se va compara in functie

	@return             Un vector dinamic cu toti participantii care respecta conditia din functie
*/
DynamicArray* filter(DynamicArray* arr, ConditionFunction condition, void* value);


DynamicArray* filter2(DynamicArray* arr, int val, char* surname);


DynamicArray* filter3(DynamicArray* arr, int val);


/*
	Sorteaza un DynamicArray dupa o conditie data folosind quicksort

	@param arr      Vectorul dinamic dat
	@param left     Pozitia cea mai din stanga a partitiei pe care se lucreaza in quicksort la un moment dat
	@param right    Pozitia cea mai din dreapta a partitie pe care se lucreaza in quicksort la un moment dat
	@param compare  Functia de comparare, returneaza -1 pentru <=, 0 pentru egalitate si 1 pentru >=
	@param reverse  Daca sortarea se va face in ordine inversa
*/
void quickSort(DynamicArray* arr, int left, int right, ComparisonFunction compare, int reverse);


/*
	Ordoneaza participantii din repository dupa un anumit criteriu

	@param arr      Vectorul dinamic in care se afla participantii
	@param compare  Functia de comparare, returneaza -1 pentru <=, 0 pentru egalitate si 1 pentru >=
	@param reverse  Indica daca ordonarea se va face in ordine inversa
*/
void sort(DynamicArray* arr, ComparisonFunction compare, int reverse);

/*
Remove last element from the list
!!!! do not destroy removed element
return the removed element
*/
TElem removeLast(DynamicArray* l);                     
/*
  Make a shallow copy of the list
  return Mylist containing the same elements as l
*/
DynamicArray* copyList(DynamicArray* l, CopyFct cf);


TElem get(DynamicArray* l, int poz);
