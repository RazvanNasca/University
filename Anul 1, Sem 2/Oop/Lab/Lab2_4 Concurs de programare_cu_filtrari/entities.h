#pragma once

#include <stdlib.h>
#include <stdbool.h>
#include <string.h>


typedef struct
{
	char* surname, * name;
	int problem_grades[10];
	int score;
} Participant;


/*
	Creeaza un nou participant

	@param surname:         Numele participantului care urmeaza a fi creat
	@param name:            Prenumele participantului care urmeaza a fi creat
	@param problem_grades:  O lista cu punctele obtinute de participant la fiecare problema, fiecare element fiind un numar intre 1 si 10

	@return                Participantul nou creat
*/
Participant* createParticipant(char* surname, char* name, int problem_grades[10]);


/*
	Verifica daca participantul dat este valid

	@param p  Participantul care va fi verificat

	@return  0, daca participantul este valid, 1 daca numele sau este vid, 2 daca prenumele este vid sau 
	3 daca una sau mai multe din note sunt invalide
*/
int validateParticipant(Participant* p);


/*
	Distruge un participant din memorie

	@param p:  Participantul care urmeaza a fi distrus
*/
void destroyParticipant(Participant* p);

/*
	Copiaza atributele unui participant si le atribuie altui participant

	@param dest:    Participantul caruia i se va atribui copia
	@param source:  Participantul care se va copia
*/
int copyParticipant(Participant* dest, Participant* source);


/*
	Creeaza un sir de caractere care poate fi afisat, folosind atributele participantului

	@param p:  Participantul care se doreste a fi afisat

	@return   Un sir de caractere corespunzator participantului dat
*/
char* toString(Participant* p);


/*
	Verifica daca 2 participanti au acelasi nume si prenume

	@param p1  Primul participant
	@param p2  Al doilea participant
	
	@return true, daca numele si prenumele participantilor coincid, false, in caz contrar
*/
int equals(Participant* p1, Participant* p2);


Participant* copyParticip(Participant* p);

int getScore(Participant* p);

int getProblem(Participant* p, int index);

void setScore(Participant* p, int val);

void setProblem(Participant* p, int index, int val);

char* getSurname(Participant* p);

char* getName(Participant* p);