#pragma once

template<typename T>
class VectDinamic {
	T elems*;
	int lg;
	int cap;
public:
	void add(const T& el) {
		ensureCapacity();//daca e nevoie mai alocam memorie
		elems[lg++] = el;
	}
};

VectDinamic<Pet> v;