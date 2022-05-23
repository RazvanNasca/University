#pragma once

#include "pet.h"

//punem obiete Pet nu adrese (Pet*)
//constructorul de copiere / operatorul de assignment se ocupa cu copierea corecta
typedef Pet Element;
#define INITIAL_CAPACITY 5

class IteratorVector;

class VectorDinamic {
	
public:
	/*
	Constructor default
	Alocam loc pentru 5 elemente
	*/
	VectorDinamic() :elems{ new Element[INITIAL_CAPACITY] }, cap{ INITIAL_CAPACITY }, lg{ 0 } {}

	/*
	Constructor de copiere
	*/
	VectorDinamic(const VectorDinamic& ot) {
		elems = new Element[ot.cap];
		//copiez elementele
		for (int i = 0; i < ot.lg; i++) {
			elems[i] = ot.elems[i];  //assignment din Pet
		}
		lg = ot.lg;
		cap = ot.cap;
	}
	/*
	Eliberam memoria
	*/
	~VectorDinamic() {
		delete[] elems;
	}

	void add(const Element& el) {
		ensureCapacity();//daca e nevoie mai alocam memorie
		elems[lg++] = el;
	}

	Element& get(int poz) const {
		return elems[poz];
	}

	void set(int poz, const Element& el) {
		elems[poz] = el;
	}

	int size() const {
		return lg;
	}

	friend class IteratorVector;

	IteratorVector begin();
	IteratorVector end();
		
	
private:
	int lg;//numar elemente
	int cap;//capacitate
	Element* elems;//elemente

	void ensureCapacity();

};

class IteratorVector {
private:
	const VectorDinamic& v;
	int poz = 0;
public:
	IteratorVector(const VectorDinamic& v) :v{v} {}
	IteratorVector(const VectorDinamic& v, int poz) :v{ v }, poz{poz} {}
	bool valid()const {
		return poz < v.lg;
	}
	Element& element() const {
		return v.elems[poz];
	}
	void next() {
		poz++;
	}
	Element& operator*() {
		return element();
	}
	IteratorVector& operator++() {
		next();
		return *this;
	}
	bool operator==(const IteratorVector& ot) {
		return poz == ot.poz;
	}
	bool operator!=(const IteratorVector& ot) {
		return !(*this == ot);
	}
};
void testVector();