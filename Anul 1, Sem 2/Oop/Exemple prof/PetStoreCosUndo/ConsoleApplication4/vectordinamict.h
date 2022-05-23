#pragma once
#define INITIAL_CAPACITY 5

template<typename Element>
class IteratorVectorT;

template<typename Element>
class VectorDinamicT {
	friend class IteratorVectorT<Element>;
public:
	/*
	Constructor default
	Alocam loc pentru 5 elemente
	*/
	VectorDinamicT() :elems{ new Element[INITIAL_CAPACITY] }, cap{ INITIAL_CAPACITY }, lg{ 0 } {}

	/*
	Constructor de copiere
	*/
	VectorDinamicT(const VectorDinamicT& ot) {
		elems = new Element[ot.cap];
		//copiez elementele		
		for (int i = 0; i < ot.lg; i++) {
			elems[i] = ot.elems[i];  //assignment din Pet
		}
		lg = ot.lg;
		cap = ot.cap;
	}
	VectorDinamicT& operator=(const VectorDinamicT& ot) {
		delete[] elems;
		elems = new Element[ot.cap];
		//copiez elementele
		for (int i = 0; i < ot.lg; i++) {
			elems[i] = ot.elems[i];  //assignment din Pet
		}
		lg = ot.lg;
		cap = ot.cap;
		return *this;
	}
	/*
	Eliberam memoria
	*/
	~VectorDinamicT() {
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
	IteratorVectorT<Element> begin() const;
	IteratorVectorT<Element> end() const;

private:
	int lg;//numar elemente
	int cap;//capacitate
	Element* elems;//elemente

	void ensureCapacity();
};

template<typename Element>
void VectorDinamicT<Element>::ensureCapacity() {
	if (lg < cap) {
		return; //mai avem loc
	}
	cap *= 2;
	Element* aux = new Element[cap];
	for (int i = 0; i < lg; i++) {
		aux[i] = elems[i];
	}
	delete[] elems;
	elems = aux;
}


template<typename Element>
IteratorVectorT<Element> VectorDinamicT<Element>::begin() const
{
	return IteratorVectorT<Element>(*this);
}

template<typename Element>
IteratorVectorT<Element> VectorDinamicT<Element>::end() const
{
	return IteratorVectorT<Element>(*this, lg);
}

template<typename Element>
class IteratorVectorT {
private:
	const VectorDinamicT<Element>& v;
	int poz = 0;
public:
	IteratorVectorT(const VectorDinamicT<Element>& v) :v{ v } {}
	IteratorVectorT(const VectorDinamicT<Element>& v, int poz) :v{ v }, poz{ poz } {}
	bool valid()const {
		return poz < v.lg;
	}
	Element& element() const {
		return v.elems[poz];
	}
	void next() {
		poz++;
	}
	Element& operator*() const{
		return element();
	}
	IteratorVectorT& operator++() {
		next();
		return *this;
	}

	bool operator==(const IteratorVectorT<Element>& ot) {
		return poz == ot.poz;
	}
	bool operator!=(const IteratorVectorT<Element>& ot) {
		return !(*this == ot);
	}
};

void testVectorT();