#include "Iterator.h"
#include "DO.h"
#include <iostream>

#include <exception>
using namespace std;

DO::DO(Relatie r) {
	
	DO::lungime = 0;
	DO::lista = NULL;
	DO::rel = r;
	//DO::lista->preced = NULL;
	///DO::lista->urm = NULL;
	
}

//adauga o pereche (cheie, valoare) in dictionar
//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
//daca nu exista cheia, adauga perechea si returneaza null
TValoare DO::adauga(TCheie c, TValoare v) {

	/*
		Best case: teta(1)
		Worst case: teta(n)
		Overall: O(n)
	*/

	nod* elem = new nod;
	elem->valoare.first = c;
	elem->valoare.second = v;
	elem->preced = NULL;
	elem->urm = NULL;

	if (lista == NULL)
	{
		lista = new nod;
		lista->valoare.first = c;
		lista->valoare.second = v;
		lista->preced = NULL;
		lista->urm = NULL;
		lungime++;
		return NULL_TVALOARE;
	}

	nod* parcurg = lista;
	while (parcurg != NULL && parcurg->valoare.first != c)
		parcurg = parcurg->urm;

	if (parcurg != NULL) /// am gasit cheia
	{
		TValoare aux = parcurg->valoare.second;
		parcurg->valoare.second = v;
		return aux;
	}

	parcurg = lista;
	if (lungime == 1)
	{
		if (rel(parcurg->valoare.first, c) == true)
		{
			parcurg->urm = elem;
			elem->preced = parcurg;
		}
		else
		{
			parcurg->preced = elem;
			elem->urm = parcurg;
			lista = elem;
		}
		lungime++;
		return NULL_TVALOARE;
	}
		

	/// nu am gasit cheia si trebuie inserata
	parcurg = lista;
	while (parcurg->urm != NULL && rel(parcurg->valoare.first, c) == true)
		parcurg = parcurg->urm;

	if (parcurg->urm == NULL) 
	{
		if (rel(parcurg->valoare.first, c) == true) /// inseram pe ultima pozitie
		{
			parcurg->urm = elem;
			elem->preced = parcurg;
		}
		else
			/// penultima pozitie
		{
			elem->urm = parcurg;
			elem->preced = parcurg->preced;
			parcurg->preced->urm = elem;
			parcurg->preced = elem;
		}
	}
	else 
	{
		if (rel(lista->valoare.first, c) == false) /// inseram la inceput
		{
			lista->preced = elem;
			elem->urm = lista;
			lista = elem;
		}
		else	/// in interior
		{
			elem->urm = parcurg;
			elem->preced = parcurg->preced;
			parcurg->preced->urm = elem;
			parcurg->preced = elem;
		}

	}

	lungime++;
	return NULL_TVALOARE;
}

//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) const {
	
	/*
		Best case: teta(1)
		Worst case: teta(n)
		Overall: O(n)
	*/

	if (lista != NULL)
	{
		nod* parcurg = lista;
		while (parcurg != NULL && parcurg->valoare.first != c)
			parcurg = parcurg->urm;

		if (parcurg == NULL)  /// nu exista cheia
			return NULL_TVALOARE;

		if (parcurg->valoare.first == c)
			return parcurg->valoare.second;
	}

	return NULL_TVALOARE;
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) {

	/*
		Best case: teta(1)
		Worst case: teta(n)
		Overall: O(n)
	*/
	
	if (lista != NULL)
	{
		if (cauta(c) == NULL_TVALOARE)		/// cheia nu e in lista
			return NULL_TVALOARE;

		nod* parcurg = lista;
		int poz = 0;
		while (parcurg->urm != NULL && parcurg->valoare.first != c)
		{
			parcurg = parcurg->urm;
			poz++;
		}
		
		if (poz == 0) /// primul element trebuie sters
		{
			nod* elem = lista;
			TValoare val = lista->valoare.second;
			lista = lista->urm;
			if(lista != NULL)
				lista->preced = NULL;

			lungime--;
			delete[] elem;
			return val;
		}
		else	/// se afla in interior
		{
			nod* parcurg = lista;
			while (parcurg->urm != NULL && parcurg->valoare.first != c)
				parcurg = parcurg->urm;

			//nod* elem = parcurg;
			TValoare val = parcurg->valoare.second;

			if (parcurg->urm == NULL)
			{
				parcurg->preced->urm = NULL;
			}
			else
			{
				parcurg->preced->urm = parcurg->urm;
				parcurg->urm->preced = parcurg->preced;
			}

			lungime--;
			//parcurg = NULL;
			delete[] parcurg;
			return val;
		}
	}

	return NULL_TVALOARE;
}

//returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const {
	/// Complexitate: teta(1)

	return lungime;
}

//verifica daca dictionarul e vid
bool DO::vid() const {
	/// Complexitate: teta(1)

	if(lista == NULL)
		return true;
	return false;
}

Iterator DO::iterator() const {
	return  Iterator(*this);
}

DO::~DO() {
	/// Complexitate: teta(n) 

	nod* parcurg = lista;
	while (parcurg != NULL)
	{
		lista = lista->urm;
		delete[] parcurg;
		parcurg = lista;
	}
}

int DO::diferentaValoareMaxMin() const
{
	/*
		Complexitatea: teta(n) , n - numarul de elemente din lista
	*/
	if (lista == NULL)
		return -1;
	
	TValoare minim = 1000000, maxim = -1000000;
	nod* parcurg = lista;
	while (parcurg != NULL)
	{
		if (parcurg->valoare.second < minim)
			minim = parcurg->valoare.second;
		if (parcurg->valoare.second > maxim)
			maxim = parcurg->valoare.second;
		parcurg = parcurg->urm;
	}

	return  maxim - minim;
}

void DO::afisare()
{
	nod* prim = lista;
	while (prim != NULL)
	{
		cout << prim->valoare.first << " " << prim->valoare.second << "\n";
		prim = prim->urm;
	}
}