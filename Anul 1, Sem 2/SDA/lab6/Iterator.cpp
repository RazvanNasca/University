#include "DO.h"
#include "Iterator.h"
#include <iostream>

using namespace std;

void Iterator::deplasare()
{
	/// Compexitate: teta(n^2), n - nr de chei din lista 

	vect = new TElem[dict.dim()+1];
	cnt = 0;
	for (int i = 0; i < dict.m; i++)
	{
		DO::nod* curent = dict.lista[i];
		while (curent != NULL)
		{
			vect[cnt] = curent->elem;
			cnt++;
			curent = curent->urm;
		}
	}

	for(int i = 0; i < cnt - 1; i++)
		for(int j = i+1; j < cnt; j++)
			if (dict.rel(vect[i].first, vect[j].first) == false)
			{
				TElem aux = vect[i];
				vect[i] = vect[j];
				vect[j] = aux;
			}
}

Iterator::Iterator(const DO& d) : dict(d) {
	/// Compexitate: teta(n^2), n - nr de perechi din lista 

	deplasare();
	
	it = 0;
}

void Iterator::prim() {
	/// Compexitate: teta(1)

	it = 0;
}

void Iterator::urmator() {
	/// Compexitate: teta(1)

	it++;
}

bool Iterator::valid() const {
	/// Compexitate: teta(1)

	if (it == cnt || it < 0)
		return false;
	return true;
}

void Iterator::revinoKPasi(int k)
{
	/// Complexitate: teta(1)

	if (it - k < 0)
		throw false;
	if (k <= 0)
		throw false;

	it -= k;
}

TElem Iterator::element() const {
	/// Compexitate: teta(1)

	if (!valid())
		throw false;
	return pair <TCheie, TValoare>(vect[it].first, vect[it].second);
}

