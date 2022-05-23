#pragma once

#pragma once
#include <string>
using namespace std;

template <typename T>
struct Nod {
	T val;
	Nod* urm;
	Nod(T v) :val{ v }, urm{ nullptr } {};
	Nod(T v,Nod* urm) :val{ v }, urm{ urm } {};
};

//forward declaration - clasa iterator e definit mai jos 
//Avem nevoie de asta fiindca clasa iterator este folosit in clasa MyLista dar definitia apare mai jos
template <typename T>
class MyListaIterator;

template <typename T>
class MyLista {
	
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
	MyLista() :cap{ nullptr } {};

	/*
	 Constructor de copiere 
	 - apelat automat la transmitere prin valoare, la return prin valoare, explicit ex: MyList l2{l1};
	 
	 Rule of three
	*/
	MyLista(const MyLista& ot);
	/*
	Operator de assignment 
	- apelat automat la expresii a = b; unde a si b sunt de tip MyLista
	Rule of three
	*/
	void operator=(const MyLista& ot);

	/*
	 Destructor - apelat cand obiectul este distrus (out of scope sau delete)
	 Elibereaza memoria pentru fiecare nod
	 Rule of three
	*/
	~MyLista();
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
	MyListaIterator<T> begin() const;

	/*
	Returneaza un iterator cu nodul nullptr (dupa ultimul element)
	*/
	MyListaIterator<T> end() const;

	/*
	Acces dupa pozitie
	Operatie ineficienta, trebuie sa parcurg lista pana la poz
	*/
	T& operator[](size_t poz);


};

template <typename T>
MyLista<T>::MyLista(const MyLista& ot) {
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
void MyLista<T>::operator=(const MyLista& ot) {
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
MyLista<T>::~MyLista() {
	elibereazaNoduri();
}

template <typename T>
Nod<T>* MyLista<T>::copieaza(Nod<T>* current) {
	if (current == nullptr) {
		return nullptr;
	}
	auto n = new Nod<T>{ current->val };	
	n->urm = copieaza(current->urm);
	return n;
}

template <typename T>
void MyLista<T>::addInceput(T v) {
	Nod<T>* n = new Nod<T>{ v,cap };	
	cap = n;
}


template <typename T>
void MyLista<T>::push_back(T v) {
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
size_t MyLista<T>::size() const {
	auto nCurent = cap;
	int lg = 0;
	while (nCurent != nullptr) {
		lg++;
		nCurent = nCurent->urm;
	}
	return lg;
}

template <typename T>
T& MyLista<T>::operator[](size_t poz) {
	auto nCurent = cap;
	int lg = 0;
	while (lg<poz) {
		lg++;
		nCurent = nCurent->urm;
	}
	return nCurent->val;
}

template <typename T>
T& MyLista<T>::first() const{
	return cap->val;
}
template <typename T>
void MyLista<T>::elibereazaNoduri() {
	auto nCurent = cap;
	while (nCurent != nullptr) {
		auto aux = nCurent->urm;
		delete nCurent;
		nCurent = aux;
	}
	cap = nullptr;
}

/*
  Iterator pentru lista inlantuita
*/
template<typename T>
class MyListaIterator {
	//pozitia curenta - este nullptr daca am ajuns la sfarsitul listei
	Nod<T>* current;
public:
	MyListaIterator(Nod<T>* c) :current{ c } {
	}

	/*
	Este nevoie de operator != pentru range for
	*/
	bool operator!=(const MyListaIterator& ot);

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
MyListaIterator<T> MyLista<T>::begin() const {
	return MyListaIterator<T>{ cap };
}


template <typename T>
MyListaIterator<T> MyLista<T>::end() const {
	return MyListaIterator<T>{ nullptr };
}


/*
Este nevoie de operator != pentru range for
*/
template <typename T>
bool MyListaIterator<T>::operator!=(const MyListaIterator& ot) {
	return current != ot.current;
}

/*
trece la urmatorul element din interatie
Este nevoie de operator ++ pentru range for
*/
template <typename T>
void MyListaIterator<T>::operator++() {
	current = current->urm;
}
/*
Returneaza elementul curent din interatir
Este nevoie de operator * pentru range for
*/
template <typename T>
T& MyListaIterator<T>::operator*() {
	return current->val;
}