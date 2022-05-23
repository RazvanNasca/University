#pragma once

#include "repository.h"

typedef struct {
	DynamicArray* allPart;
	DynamicArray* undoList;
}Concurs;


Concurs createConcurs();

void destroyConcurs(Concurs* store);

/*
	Creeaza un sir de caractere care poate fi afisat, folosind atributele participantului

	@param p:  Participantul care se doreste a fi afisat

	@return   Un sir de caractere corespunzator participantului dat
*/
char* ToString(Participant* p);


/*
	Adauga un participant in repository

	@param arr             Vectorul dinamic in care se va adauga participantul
	@param surname         Numele participantului
	@param name            Prenumele participantului
	@param problem_grades  Punctele obtinute de participant la fiecare problema

	@return                false, daca exista deja un participant cu acelasi nume si prenume, true, in caz contrar
*/
int Add(Concurs* arr, char* surname, char* name, int problem_grades[10]);


/*
	Actualizeaza punctajul unei probleme pentru un participant dat, impreuna cu scorul acestuia

	@param arr       Vectorul dinamic in care se va cauta participantul
	@param surname   Numele participantului care va fi actualizat
	@param name      Prenumele participantului care va fi actualizat
	@param position  Pozitia corespunzatoare problemei pentru care se va modifica punctajul
	@param value     Valoarea noua a punctajului

	@return          false, daca participantul dat nu se gaseste in lista, true, in caz contrar
*/
int UpdateGrade(Concurs* arr, char* surname, char* name, int position, int value);


/*
	Sterge un participant din repository

	@param arr      Vectorul dinamic in care se va cauta participantul
	@param surname  Numele participantului care va fi sters
	@param name     Prenumele participantului care va fi sters

	@return         false, daca participantul dat nu se gaseste in lista, true, in caz contrar
*/
int Remove(Concurs* arr, char* surname, char* name);


/*
	Filtreaza participantii din repository care au scorul mai mic decat o valoare data

	@param  arr         Vectorul dinamic in care se vor filtra participanti
	@param  value       Valoarea cu care se va compara in functie

	@return             Un vector dinamic cu toti participantii care respecta conditia
*/
DynamicArray* FilterScoresLesserThan(Concurs* arr, int value);


/*
	Filtreaza participantii din repository care au scorul mai mare decat o valoare data

	@param  arr         Vectorul dinamic in care se vor filtra participanti
	@param  value       Valoarea cu care se va compara in functie

	@return             Un vector dinamic cu toti participantii care respecta conditia
*/
DynamicArray* FilterScoresGreaterThan(Concurs* arr, int value);


/*
	Filtreaza participantii din repository carora prenume le incepe cu o litera data

	@param  arr         Vectorul dinamic in care se vor filtra participanti
	@param  value       Valoarea cu care se va compara in functie

	@return             Un vector dinamic cu toti participantii care respecta conditia
*/
DynamicArray* FilterNamesStartingWith(Concurs* arr, char c);


/*
	Filtreaza participantii din repository care au numele de familie dat

	@param  arr         Vectorul dinamic in care se vor filtra participanti
	@param  value       Valoarea cu care se va compara in functie

	@return             Un vector dinamic cu toti participantii care respecta conditia
*/
DynamicArray* FilterSurnames(Concurs* arr, char* surname);


/*
	Ordoneaza participantii din repository dupa scor

	@param arr      Vectorul dinamic in care se afla participantii
	@param reverse  Indica daca ordonarea se va face in ordine inversa
*/
void SortByScore(Concurs* arr, int reverse);


/*
	Ordoneaza participantii din repository dupa nume, prenume

	@param arr      Vectorul dinamic in care se afla participantii
	@param compare  Adresa unei functii care compara 2 participanti si intoarce o valoare care indica relatia dintre cei 2
	@param reverse  Indica daca ordonarea se va face in ordine inversa
*/
void SortByName(Concurs* arr, int reverse);


DynamicArray* FilterScoresGreaterThanAndSurname(Concurs* arr, char* surname, int value);


DynamicArray* FilterNameAndSurname(Concurs* arr, int value);


int undo(Concurs* store);


