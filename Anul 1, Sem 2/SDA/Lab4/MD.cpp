#include "MD.h"
#include "IteratorMD.h"
#include <exception>
#include <iostream>
#define kap 20242

using namespace std;


MD::MD(){
	/// Complexitate teta(n^2)

	capacitate = 2;
	e = new pair<TCheie, lista*>[capacitate];
	urm = new int[capacitate];
	prim = -1;
	primLiber = 0;

	for (int i = 0; i < capacitate; i++)
	{
		urm[i] = i + 1;
		e[i].second = new lista[10];
		e[i].second->capacitateLista = 10;
		e[i].second->val = new TValoare[e[i].second->capacitateLista];
		e[i].second->primLista = -1;
		e[i].second->primLiberLista = 0;

		e[i].second->urmLista = new int[10];
		for (int j = 0; j < e[i].second->capacitateLista - 1; j++)
			e[i].second->urmLista[j] = j + 1;
		e[i].second->urmLista[e[i].second->capacitateLista - 1] = -1;
	}

	urm[capacitate - 1] = -1;

}

int MD::aloca() {
	/// Complexitate: teta(1)

	int i = primLiber;
	primLiber = urm[primLiber];
	return i;
}

void MD::dealoca(int i) {
	/// Complexitate: teta(1)
	
	urm[i] = primLiber;
	primLiber = i;
}

int MD::alocaLista(lista* list) {
	/// Complexitate: teta(1)

	int i = list->primLiberLista;
	list->primLiberLista = list->urmLista[list->primLiberLista];
	return i;
}

void MD::dealocaLista(lista* list, int i) {
	/// Complexitate: teta(1)

	list->urmLista[i] = list->primLiberLista;
	list->primLiberLista = i;
}

void MD::Redim()
{
	/// Complexitate: teta(n*m), n - capacitate initiala, m - capacitatea fiecarei liste ~ aceeasi

	int capacitate_noua = capacitate * 2;
	pair<TCheie, lista*>* enou = new pair<TCheie, lista*>[capacitate_noua];
	int* urmnou = new int[capacitate_noua];

	for (int i = 0; i < capacitate; i++)
	{
		urmnou[i] = urm[i];
		enou[i].first = e[i].first;
		enou[i].second = e[i].second;
	}
	
	for (int i = capacitate; i < capacitate_noua; i++)
	{
		urmnou[i] = i + 1;
		enou[i].second = new lista[10];
		enou[i].second->capacitateLista = 10;
		enou[i].second->val = new TValoare[enou[i].second->capacitateLista];
		enou[i].second->primLista = -1;
		enou[i].second->primLiberLista = 0;

		enou[i].second->urmLista = new int[10];
		for (int j = 0; j < enou[i].second->capacitateLista - 1; j++)
			enou[i].second->urmLista[j] = j + 1;
		enou[i].second->urmLista[enou[i].second->capacitateLista - 1] = -1;
	}
	urmnou[capacitate_noua - 1] = -1;

	primLiber = capacitate;
	capacitate = capacitate_noua;
	
	/// stergem lista veche

	delete[] urm;
	delete[] e;

	e = enou;
	urm = urmnou;

}


void MD::adauga(TCheie c, TValoare v) {
	/*
		Complexitate: Best case: teta(1) , cheie pe prima pozitie si nu necesita redim
					  Worst case: teta(n+m), cheia se afla pe ultima pozitie si necesita redim, n - nr de chei din lista, m - 2 * capacitatea listei asociate cheii
					  Overall: O(n+m), n - nr de chei, m - lungimea listei de valori asociate
	*/
	
	int curent = prim;
	while (curent != -1 && e[curent].first != c)
		curent = urm[curent];
	
	if (curent == -1) // nu exista cheia
	{
		if (primLiber == -1) /// redim
			Redim();

		int i = aloca();
		e[i].first = c;
		urm[i] = prim;
		prim = i;

		int j = alocaLista(e[i].second);
		e[i].second->primLista = j;
		e[i].second->val[j] = v;
		e[i].second->urmLista[j] = -1;
	}
	else
	{	/// exista cheia

		if (e[curent].second->primLiberLista == -1) /// redim
		{
			/// teta(capacitate_lista * 2)

			int capacitate_noua = e[curent].second->capacitateLista * 2;
			lista* listanoua = new lista[capacitate_noua];
			listanoua->urmLista = new int[capacitate_noua];
			listanoua->val = new TValoare[capacitate_noua];

			for (int i = 0; i < e[curent].second->capacitateLista; i++)
			{
				listanoua->urmLista[i] = e[curent].second->urmLista[i];
				listanoua->val[i] = e[curent].second->val[i];
			}

			for (int i = e[curent].second->capacitateLista; i < capacitate_noua - 1; i++)
			{
				listanoua->urmLista[i] = i + 1;
			}
			listanoua->urmLista[capacitate_noua - 1] = -1;

			listanoua->primLiberLista = e[curent].second->capacitateLista;
			listanoua->primLista = e[curent].second->primLista;

			delete[] e[curent].second;

			e[curent].second = listanoua;
			e[curent].second->capacitateLista = capacitate_noua;
		}

		int j = alocaLista(e[curent].second);
		e[curent].second->urmLista[j] = e[curent].second->primLista;
		e[curent].second->primLista = j;
		e[curent].second->val[j] = v;
	}

}


bool MD::sterge(TCheie c, TValoare v) {
	/*
		Complexitate: Best case: teta(1), cheia este pe prima pozitie si valoare e prima in lista
					  Worst case: teta(n+m), cheia e pe ultima pozitie si valoarea fie nu exista fie este ultima, n - nr de chei, m - nr de valori asociate cheii
					  Overall: O(n+m), n - nr de chei, m - nr de valori asociate cheii
	*/
	
	int curent = prim;
	while (curent != -1 && e[curent].first != c)
		curent = urm[curent];

	if (curent == -1) /// nu gasim cheia
		return false;
	
	/// am gasit cheia
	int curentLista = e[curent].second->primLista;
	
	if (e[curent].second->val[curentLista] == v) /// valoare e pe prima pozitie
	{
		e[curent].second->primLista = e[curent].second->urmLista[curentLista];
		dealocaLista(e[curent].second, curentLista);

		if (e[curent].second->primLista == -1)	/// cheia trebuie stearsa
		{
			if (e[prim].first == c) /// e prima cheie in lista
			{
				if (urm[prim] == -1) /// e singura cheie
				{
					dealoca(prim);
					prim = -1;
					return true;
				}

				int aux = prim;
				prim = urm[prim];
				dealoca(aux);
				
				return true;
			}

			curent = prim;
			while (e[urm[curent]].first != c)
				curent = urm[curent];

			int aux = urm[curent];
			urm[curent] = urm[urm[curent]];
			dealoca(aux);
			return true;
		}	

		return true;
	}

	while (e[curent].second->urmLista[curentLista] != -1 && e[curent].second->val[e[curent].second->urmLista[curentLista]] != v)
		curentLista = e[curent].second->urmLista[curentLista];

	if (e[curent].second->urmLista[curentLista] == -1)		/// suntem pe ultima poitie si nu am gasit in lista valoarea
		//if (e[curent].second->val[curentLista] != v)
			return false;

	int aux = e[curent].second->urmLista[curentLista];
	e[curent].second->urmLista[curentLista] = e[curent].second->urmLista[e[curent].second->urmLista[curentLista]];
	dealocaLista(e[curent].second, aux);

	if (e[curent].second->primLista == -1) /// sterge si dealoca cheia
		dealoca(curent);

	return true;
}


vector<TValoare> MD::cauta(TCheie c) const {
	
	/* Complexitate: BestCase: teta(m), cheia se afla pe prima poz in lista mare, m = nr de valori asociate cheii
					 WorstCase: teta(m+n) ~ teta(m), cheia se alfa la final, m = nr de valori asociate cheii, n = nr de chei
					 Overall: teta(m), m = nr de valori asociate unei chei
	*/
	
	vector<TValoare> rez;
	int curent = this->prim;

	while (curent != -1)
	{
		if (e[curent].first == c)
		{
			auto i = e[curent].second;
			int curentLista = i->primLista;
			while (curentLista != -1)
			{
				rez.push_back(i->val[curentLista]);
				curentLista = i->urmLista[curentLista];
			}
			return rez;
		}
		curent = urm[curent];
	}

	return rez;
}


int MD::dim() const {
	/// Complexitate: teta(md), md = suma numarului de valori asociate fiecarei chei
	
	int curent = this->prim;
	int cnt = 0;

	while (curent != -1)
	{
		auto i = e[curent].second;
		int curentLista = i->primLista;
		while (curentLista != -1)
		{
			cnt++;
			curentLista = i->urmLista[curentLista];
		}
			
		curent = urm[curent];
	}
	
	return cnt;
}

vector<TValoare>MD::stergeValoriPentruCheie(TCheie cheie)
{
	/*
		Complexitate: Best case: teta(m), m - nr de valori asociate cheii, cheia e pe prima pozitie
					  Worst case: ~teta(n*m), n - nr de chei, m - nr de valori asociate, cheia e pe ultima pozitie
					  Overall: O(n*m), n - nr de chei, m - nr de valori asociate
	*/
	vector<TValoare> rez;
	int curent = this->prim;

	while (curent != -1)
	{
		if (e[curent].first == cheie)
		{
			auto i = e[curent].second;
			int curentLista = i->primLista;
			while (curentLista != -1)
			{
				rez.push_back(i->val[curentLista]);
				int aux = curentLista;
				curentLista = i->urmLista[curentLista];
				sterge(cheie,i->val[aux]);
			}
		}
		curent = urm[curent];
	}

	return rez;
}


bool MD::vid() const {
	/// Complexitate: teta(1)

	if(prim == -1)
		return true;
	return false;
}

IteratorMD MD::iterator() const {
	return IteratorMD(*this);
}


MD::~MD() {
	/// Complexitate: teta(n), n - nr chei
	
	int curent = this->prim;
	while (curent != -1)
	{
		auto i = e[curent].second;
		delete[] i->urmLista;
		delete[] i->val;
		curent = urm[curent];
	}

	delete[] urm;
	delete[] e;
}
