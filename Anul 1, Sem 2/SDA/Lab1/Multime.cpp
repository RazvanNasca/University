#include "Multime.h"
#include "IteratorMultime.h"

#include <iostream>
#include <cmath>
using namespace std;

Multime::Multime() {

	capacitate = 0;
	v = nullptr;
}


bool Multime::adauga(TElem elem) {
	/* 
		best case: teta(1)
		worst case: teta(capacitate) 
		complexitate ~ O(capacitate)
	*/

	if (capacitate == 0)  /// prima adaugare
	{
		capacitate = abs(elem) * 2 + 1;
		v = new int[capacitate];
		for (int i = 0; i < capacitate; i++) 
			v[i] = 0;
	}

	
	if (abs(elem) > capacitate / 2)		// daca elementul nu incape
	{	// marim vectorul

		int dif;
		if (elem > capacitate / 2)
			dif = elem - capacitate / 2;
		else
			dif = -(capacitate / 2) - elem;
		
		int c = capacitate;
		capacitate += dif * 2;

		int* vnou = new int[capacitate];
		for (int i = 0; i < capacitate; i++)
			vnou[i] = 0;

		for (int i = 0; i <= c / 2; i++)
			vnou[i] = v[i];  /// pozitive (inc. 0)

		for (int i = c / 2 + 1; i < c; i++)  
			vnou[i - c / 2 + capacitate / 2] = v[i];


		delete[] v;   
		v = vnou;

	}

	if (elem >= 0)   // element pozitiv
	{
		if (v[elem] == 0)   // daca nu e in multime
		{
			v[elem] = 1;	// il adauga
			return true;
		}

	}
	else
		if (v[capacitate / 2 - elem] == 0)
		{
			v[capacitate / 2 - elem] = 1;
			return true;
		}
	return false;
}


bool Multime::sterge(TElem elem) {
	/* 
		complexitate ~ teta(1)
	*/


	if (capacitate > 0 && abs(elem) <= capacitate / 2) 
	{
		if (elem >= 0)
		{
			if (v[elem] == 1)
			{
				v[elem] = 0;
				return true;
			}
			else 
				return false;
		}
		else
		{
			if (v[capacitate / 2 - elem] == 1)
			{
				v[capacitate / 2 - elem] = 0;
				return true;
			}
			else 
				return false;
		}
	}

	return false;
}


bool Multime::cauta(TElem elem) const {
	/*
		complexitate ~ teta(1)
	*/

	if (capacitate > 0) {

		if (abs(elem) <= capacitate / 2) 
		{
			if (elem >= 0)
			{
				if (v[elem] == 1)
					return true;
				return false;
			}
			else
			{
				if (v[capacitate / 2 - elem] == 1)
					return true;
				return false;
			}
		}
	}

	return false;
}


int Multime::dim() const {
	/*
		complexitate ~ teta(n) - n = capacitatea
	*/

	int cnt = 0;
	for (int i = 0; i < capacitate; i++)
		if (v[i] == 1)
			cnt++;
	return cnt;
}

bool Multime::vida() const {
	/*
		complexitate ~ teta(n)
	*/

	if (Multime::dim() == 0)
		return true;
	return false;
}


Multime::~Multime() {
	/* de adaugat */
	delete[] v;
}


IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}
