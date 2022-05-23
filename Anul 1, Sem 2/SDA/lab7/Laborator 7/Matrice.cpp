#include "Matrice.h"
#include <exception>
#include <iostream>

using namespace std;


Matrice::Matrice(int m, int n) {
	///	Complexitate: teta(1) 

	if (m <= 0 || n <= 0)
		throw exception();

	lin = n;
	col = m;
	rad = nullptr;
}


int Matrice::nrLinii() const {
	///	Complexitate: teta(1) 

	return lin;
}


int Matrice::nrColoane() const {
	///	Complexitate: teta(1) 

	return col;
}

TElem Matrice::sumaSubDiagonalaPrincipala()
{
	/// Complexitate: teta(n),  n - numarul de noduri din arbore
	return suma(rad);
}

nod* Matrice::minim(nod* curent)
{
	/// Complexitate: O(h), h - inaltimea arborelui
	if (curent->stang == nullptr)
		return curent;
	else
		while (curent->stang != nullptr)
			curent = curent->stang;

	return curent;
}

void Matrice::sterge(int i, int j)
{
	/*
		Complexitate:	Best case: teta(1), stergem radacina
						Worst case: teta(h), h - inaltimea arborelui
						Overall: O(h), h - inaltimea arborelui
	*/

	rad = stergereRec(i, j, rad);
}

nod* Matrice::stergereRec(int i, int j, nod* curent)
{
	if (curent == nullptr)
		return nullptr;
	else
		if (i < curent->linie)
			curent->stang = stergereRec(i, j, curent->stang);
		else
			if (i > curent->linie)
				curent->drept = stergereRec(i, j, curent->drept);
			else
				if (j < curent->coloana)
					curent->stang = stergereRec(i, j, curent->stang);
				else
					if (j > curent->coloana)
						curent->drept = stergereRec(i, j, curent->drept);
					else
					{
						/// am gasit nodul
						if (curent->stang != nullptr && curent->drept != nullptr)
						{
							nod* valMin = minim(curent->drept);
							curent->val = valMin->val;
							curent->linie = valMin->linie;
							curent->coloana = valMin->coloana;
							curent->drept = stergereRec(valMin->linie, valMin->coloana, valMin);
						}
						else
						{
							nod* aux = curent;
							nod* repl;
							if (curent->stang == nullptr)
								repl = curent->drept;
							else
								repl = curent->stang;

							///delete aux;
							return repl;
						}
					}
	return curent;
}

TElem Matrice::suma(nod* curent)
{
	if (curent == nullptr)
		return 0;
	if (curent->drept == nullptr && curent->stang == nullptr)
	{
		if (curent->linie > curent->coloana)
			return curent->val;
		else
			return 0;
	}
	else
		return suma(curent->drept) + suma(curent->stang);
}


TElem Matrice::element(int i, int j) const 
{
	/*
		Complexitate:	Best case: teta(1), elementul se afla pe radacina
						Worst case: teta(h)
						Overall: O(h), h - inaltimea arborelui
	*/
	
	if (i < 0 || j < 0 || i >= lin || j >= col)
		throw exception();
	
	
	nod* curent = rad;
	while (curent != nullptr)
	{
		if (curent->linie == i && curent->coloana == j)
			return curent->val;
	
		if (i < curent->linie)
			curent = curent->stang;
		else
			if (i > curent->linie)
				curent = curent->drept;
			else
				if (j < curent->coloana)
					curent = curent->stang;
				else
					curent = curent->drept;
	}

	return NULL_TELEMENT;

}




nod* Matrice::ModificaRec(nod* curent, int i, int j, TElem e, TElem& vechi)
{
	/*
		Complexitate:	Best case: teta(1), elementul cautat se afla pe radacina 
						Worst case: teta(h), h - inaltimea arborelui
						Overall: O(h), h - inaltimea arborelui
	*/

	/// trebuie adaugat un nou nod
	if (curent == nullptr)
	{
		if (e != 0)
		{
			curent = new nod;
			curent->drept = nullptr;
			curent->stang = nullptr;
			curent->val = e;
			curent->linie = i;
			curent->coloana = j;
			vechi = NULL_TELEMENT;
		}
	}
	else
		if (curent->linie == i && curent->coloana == j)
		{
			if (e == 0)
			{
				vechi = curent->val;
				curent->val = e;
				sterge(i, j);
			}
			else
			{
				vechi = curent->val;
				curent->val = e;
			}
		}
		else
		{
			if (i < curent->linie)
				curent->stang = ModificaRec(curent->stang, i, j, e, vechi);
			else
				if (i > curent->linie)
					curent->drept = ModificaRec(curent->drept, i, j, e, vechi);
				else
					if (j < curent->coloana)
						curent->stang = ModificaRec(curent->stang, i, j, e, vechi);
					else
						curent->drept = ModificaRec(curent->drept, i, j, e, vechi);
		}
	

	return curent;
}

TElem Matrice::modifica(int i, int j, TElem e) 
{
	/// Complexitate: O(h), h - inaltimea arborelui

	
	if (i < 0 || j < 0 || i >= lin || j >= col)
		throw exception();

	TElem val;
	rad = ModificaRec(rad, i, j, e, val);

	return val;
}

