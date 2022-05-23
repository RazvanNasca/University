#include "DO.h"
#include "Iterator.h"
#include <iostream>

#include <exception>
using namespace std;

int DO::hashCode(TCheie e) const {
	/// Complexitate: teta(1)

	return abs(e);
}

int DO::d(TCheie e) const {
	//dispersia prin diviziune
	/// Complexitate: teta(1)

	return hashCode(e) % m;
}

DO::DO(Relatie r) {
	/// Complexitate: teta(m)
	
	m = MAX;
	lista = new nod*[m];
	for (int i = 0; i < m; i++)
		lista[i] = nullptr;

	rel = r;
}

//adauga o pereche (cheie, valoare) in dictionar
//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
//daca nu exista cheia, adauga perechea si returneaza null
TValoare DO::adauga(TCheie c, TValoare v) {
	/*
		Complexitate:	Best case: teta(1), cheia exista si este pe prima pozitie
						Worst case: teta(n), n - nr de chei memorate intr-o anumita lista independenta, cheia trebuie introdusa la finalul unei liste independente
						Overall: O(n), n - nr de chei memorate intr-o anumita lista independenta
	*/
	
	/// dispersia
	int disp = d(c);

	nod* curent = lista[disp];
	while (curent != NULL)
	{
		if (curent->elem.first == c)
		{
			int val = curent->elem.second;
			curent->elem.second = v;
			return val;
		}
		curent = curent->urm;
	}

	/// nu am gasit cheia
	nod* nodCurent = new nod;
	nodCurent->elem.first = c;
	nodCurent->elem.second = v;
	nodCurent->prec = NULL;
	nodCurent->urm = NULL;

	/// lista vida
	if (lista[disp] == NULL)
	{
		lista[disp] = nodCurent;
		return NULL_TVALOARE;
	}

	/// un singur element
	curent = lista[disp];
	if (curent->prec == NULL && curent->urm == NULL)
	{
		if (rel(curent->elem.first, c) == false)
		{
			/// adaugare inainte
			curent->prec = nodCurent;
			nodCurent->urm = curent;
			lista[disp] = nodCurent;
		}
		else
		{
			/// adaug dupa
			curent->urm = nodCurent;
			nodCurent->prec = curent;
		}
		return NULL_TVALOARE;
	}
	
	/// adaugam in interiorul listei
	while (curent->urm != NULL)
	{
		if (rel(curent->elem.first, c) == false)
		{
			/// pe prima pozitie
			if (curent->prec == NULL)
			{
				curent->prec = nodCurent;
				nodCurent->urm = curent;
				lista[disp] = nodCurent;
				return NULL_TVALOARE;
			}

			/// in interior
			nodCurent->urm = curent;
			nodCurent->prec = curent->prec;
			curent->prec->urm = nodCurent;
			curent->prec = nodCurent;
			return NULL_TVALOARE;
		}
		curent = curent->urm;
	}

	/// adaugam in functie de ultimul elem
	if (rel(curent->elem.first, c) == false)
	{
		/// adaugare inainte
		nodCurent->prec = curent->prec;
		nodCurent->urm = curent;
		curent->prec->urm = nodCurent;
		curent->prec = nodCurent;
	}
	else
	{
		/// adaug dupa
		curent->urm = nodCurent;
		nodCurent->prec = curent;
	}

	return NULL_TVALOARE;
}

//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) const {
	/*
		Complexitate:	Best case: teta(1), cheia cautata este pe prima pozitie
						Worst case: teta(n), n - nr. de chei memorate, cheia este pe ultima pozitie sau nu exita in lista
						Overall: O(n), n - nr. de chei momorate
	*/
	
	int disp = d(c);
	if (disp >= m)
		return NULL_TVALOARE;

	nod* curent = lista[disp];
	while (curent != NULL)
	{
		if (curent->elem.first == c)
			return curent->elem.second;
		curent = curent->urm;
	}

	return NULL_TVALOARE;
}

//sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) {
	/*
		Complexitate:	Best case: teta(1), lista e vida sau elementul se afla pe prima pozitie
						Worst case: teta(n), cheia nu se afla in lista sau este pe ultima pozitie, n - nr. de chei din lista independenta corespunzatoare
						Overall: O(n), n - nr. de chei din lista independenta corespunzatoare
	*/
	
	int disp = d(c);

	nod* curent = lista[disp];

	/// lista vida
	if (curent == NULL)
		return NULL_TVALOARE;

	/// exista un singur element si trebuie sters
	if (curent->prec == NULL && curent->urm == NULL && curent->elem.first == c)
	{
		int val = curent->elem.second;
		delete[] lista[disp];
		lista[disp] = NULL;
		return val;
	}

	/// trebuie sters primul
	if (curent->prec == NULL && curent->elem.first == c)
	{
		int val = curent->elem.second;
		lista[disp] = curent->urm;
		curent->urm->prec = NULL;
		delete[] curent;
		return val;
	}

	while (curent->urm != NULL)
	{
		if (curent->elem.first == c)
		{
			int val = curent->elem.second;
			curent->prec->urm = curent->urm;
			curent->urm->prec = curent->prec;
			delete[] curent;
			return val;
		}
		curent = curent->urm;
	}

	/// trebuie sters ultimul element
	if (curent->elem.first == c)
	{
		int val = curent->elem.second;
		curent->prec->urm = NULL;
		delete[] curent;
		return val;
	}
	
	/// cheia nu se afla in lista;
	return NULL_TVALOARE;
}

//returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const {
	/// Complexitate: teta(n), n - nr de chei din lista

	int cnt = 0;
	
	for (int i = 0; i < m; i++)
	{
		nod* curent = lista[i];
		while (curent != NULL)
		{
			cnt++;
			curent = curent->urm;
		}
	}

	return cnt;

}

//verifica daca dictionarul e vid
bool DO::vid() const {
	/*
		Complexitate:	Best case: teta(1), prima lista independenta nu este vida
						Worst case: teta(m), lista este vida, m - capacitate
						Overall: O(m), m - capacitatea
	*/
	
	for (int i = 0; i < m; i++)
		if (lista[i] != NULL)
			return false;
	return true;
}

Iterator DO::iterator() const {
	return  Iterator(*this);
}

DO::~DO() {
	/// Complexitate: teta(m), m - capacitatea
	
	for (int i = 0; i < m; i++)
		delete[] lista[i];

	delete[] lista;
}