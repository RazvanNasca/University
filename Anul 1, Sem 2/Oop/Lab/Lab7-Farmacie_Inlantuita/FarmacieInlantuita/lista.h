#pragma once

#pragma once
#include <string>
using namespace std;

template <typename T>
struct Nod {
	T val;
	Nod* urm;
	Nod(T v) :val{ v }, urm{ nullptr } {};
	Nod(T v, Nod* urm) :val{ v }, urm{ urm } {};
};

//forward declaration - clasa iterator e definit mai jos 
//Avem nevoie de asta fiindca clasa iterator este folosit in clasa MyLista dar definitia apare mai jos
template <typename T>
class ListaIterator;

template <typename T>
class Lista {

	Nod<T>* cap;
	/*
	dealoca toate nodurile
	*/
	void elibereazaNoduri();

	/*
	Face o copie a listei inlantuite
	Parcurge recursiv lista si creaza noduri in care copieaza valorile
	*/
	Nod<T>* copieaza(Nod<T>* current);

public:
	Lista() :cap{ nullptr } {};

	/*
	 Constructor de copiere
	 - apelat automat la transmitere prin valoare, la return prin valoare, explicit ex: MyList l2{l1};

	 Rule of three
	*/
	Lista(const Lista& ot);

	/*
	Operator de assignment
	- apelat automat la expresii a = b; unde a si b sunt de tip MyLista
	Rule of three
	*/
	void operator=(const Lista& ot);

	/*
	 Destructor - apelat cand obiectul este distrus (out of scope sau delete)
	 Elibereaza memoria pentru fiecare nod
	 Rule of three
	*/
	~Lista();

	/*
	Returneaza numarul de elemente
	*/
	size_t size() const;

	/*
	Adauga la inceput
	*/
	void addInceput(T v);

	/*
	adauga la sfarsit
	*/
	void push_back(T v);

	/*
	Primul element
	*/
	T& first() const;

	/*
	Returneaza un iterator pozitionat pe primul element
	E nevoie de el ex. daca vrem sa folosim lista in range for
	*/
	ListaIterator<T> begin() const;

	/*
	Returneaza un iterator cu nodul nullptr (dupa ultimul element)
	*/
	ListaIterator<T> end() const;

	/*
	Acces dupa pozitie
	Operatie ineficienta, trebuie sa parcurg lista pana la poz
	*/
	T& operator[](size_t poz);


	/*
		sterge un element de la o pozitie
	*/
	void erase(int poz);

};

/*
  Iterator pentru lista inlantuita
*/
template<typename T>
class ListaIterator {
	//pozitia curenta - este nullptr daca am ajuns la sfarsitul listei
	Nod<T>* current;
public:
	ListaIterator(Nod<T>* c) :current{ c } {
	}

	/*
	Este nevoie de operator != pentru range for
	*/
	bool operator!=(const ListaIterator& ot);

	/*
	trece la urmatorul element din interatie
	Este nevoie de operator ++ pentru range for
	*/
	void operator++();

	/*
	Returneaza elementul curent din interatir
	Este nevoie de operator * pentru range for
	*/
	T& operator*();
};

template <typename T>
Lista<T>::Lista(const Lista& ot) {
	//obiectul acum se creaza (constructor) 
	//copiez din ot in this
	cap = copieaza(ot.cap);
}

/*
Operator de assignment
- apelat automat la expresii a = b; unde a si b sunt de tip MyLista
Rule of three
*/
template <typename T>
void Lista<T>::operator=(const Lista& ot) {
	//poate destinate (this) contine deja ceva
	elibereazaNoduri();
	//copiez din ot in this
	cap = copieaza(ot.cap);
}

/*
Destructor - apelat cand obiectul este distrus (out of scope sau delete)
Rule of three
*/
template <typename T>
Lista<T>::~Lista() {
	elibereazaNoduri();
}

template <typename T>
Nod<T>* Lista<T>::copieaza(Nod<T>* current) {
	if (current == nullptr) {
		return nullptr;
	}
	auto n = new Nod<T>{ current->val };
	n->urm = copieaza(current->urm);
	return n;
}

template <typename T>
void Lista<T>::addInceput(T v) {
	Nod<T>* n = new Nod<T>{ v,cap };
	cap = n;
}


template <typename T>
void Lista<T>::push_back(T v) {
	//ma pun pe ultimul element
	auto nCurent = cap;
	while (nCurent != nullptr && nCurent->urm != nullptr) {
		nCurent = nCurent->urm;
	}

	if (nCurent == nullptr) {
		cap = new Nod<T>{ v,nullptr };//poate era o lista vida
	}
	else {
		nCurent->urm = new Nod<T>{ v,nullptr };
	}
}

template <typename T>
size_t Lista<T>::size() const {
	auto nCurent = cap;
	int lg = 0;
	while (nCurent != nullptr) {
		lg++;
		nCurent = nCurent->urm;
	}
	return lg;
}

template <typename T>
T& Lista<T>::operator[](size_t poz) {
	auto nCurent = cap;
	unsigned int lg = 0;
	while (lg < poz) {
		lg++;
		nCurent = nCurent->urm;
	}
	return nCurent->val;
}

template <typename T>
T& Lista<T>::first() const {
	return cap->val;
}

template <typename T>
void Lista<T>::erase(int poz) {

	if (this->size() == 1)
	{
		delete this->cap;
		this->cap = nullptr;
		return;
	}

	if (poz == 0)
	{
		Nod<T>* curent = this->cap;
		this->cap = this->cap->urm;
		delete curent;
		return;
	}

	auto nodCurent = this->cap;
	auto nodUrmator = this->cap->urm;
	int lg = 0;
	while (lg < poz - 1)
	{
		lg++;
		nodCurent = nodCurent->urm;
		nodUrmator = nodUrmator->urm;
	}
	nodCurent->urm = nodUrmator->urm;
	delete nodUrmator;
	
}

template <typename T>
void Lista<T>::elibereazaNoduri() {
	auto nCurent = cap;
	while (nCurent != nullptr) {
		auto aux = nCurent->urm;
		delete nCurent;
		nCurent = aux;
	}
	cap = nullptr;
}

template <typename T>
ListaIterator<T> Lista<T>::begin() const {
	return ListaIterator<T>{ cap };
}


template <typename T>
ListaIterator<T> Lista<T>::end() const {
	return ListaIterator<T>{ nullptr };
}


/*
Este nevoie de operator != pentru range for
*/
template <typename T>
bool ListaIterator<T>::operator!=(const ListaIterator& ot) {
	return current != ot.current;
}

/*
trece la urmatorul element din interatie
Este nevoie de operator ++ pentru range for
*/
template <typename T>
void ListaIterator<T>::operator++() {
	current = current->urm;
}
/*
Returneaza elementul curent din interatir
Este nevoie de operator * pentru range for
*/
template <typename T>
T& ListaIterator<T>::operator*() {
	return current->val;
}

