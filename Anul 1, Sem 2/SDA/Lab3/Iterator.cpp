#include "Iterator.h"
#include "DO.h"

using namespace std;

Iterator::Iterator(const DO& d) : dict(d) {
	
	curent = dict.lista;
}

void Iterator::prim() {

	curent = dict.lista;
}

void Iterator::urmator() {
	
	curent = curent->urm;
}

bool Iterator::valid() const {

	if(curent == NULL)
		return false;
	return true;
}

TElem Iterator::element() const {
	
	if (valid() == false)
		throw false;
	
	return pair <TCheie, TValoare>(curent->valoare.first, curent->valoare.second);
}

