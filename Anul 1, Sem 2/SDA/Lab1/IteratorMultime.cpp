#include "IteratorMultime.h"
#include "Multime.h"


IteratorMultime::IteratorMultime(const Multime& m) : multime(m) {

	index = 0;
	while (index < multime.capacitate && multime.v[index] != 1) 
		index++;
	
}


void IteratorMultime::prim() {

	index = 0;
	while (index < multime.capacitate && multime.v[index] != 1)
		index++;
	
}


void IteratorMultime::urmator() {

	if (this->valid() == false) { 
		throw false;
	}
	index++;
	while (index < multime.capacitate && multime.v[index] != 1) 
		index++;
}


TElem IteratorMultime::element() const {
	if (this->valid() == false) { 
		throw false;
	}
	if (index <= multime.capacitate / 2)
	{
		if (multime.v[index] == 1)
			return index;
	}
	if (multime.v[index] == 1) {
		return (multime.capacitate / 2) - index;    
	}

	return NULL_TELEM;
}

bool IteratorMultime::valid() const {
	if (multime.dim() == 0)
		return false;

	if (index < multime.capacitate) 
		return true;
	return false;
}