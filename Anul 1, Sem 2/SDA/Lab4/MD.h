#pragma once
#include<vector>
#include<utility>

using namespace std;

typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

class IteratorMD;

class MD
{
	friend class IteratorMD;

private:

	struct lista {
		TValoare* val;
		int* urmLista;
		int primLista;
		int primLiberLista;
		int capacitateLista;
	};
	
	pair<TCheie, lista*>* e;
	int* urm;
	int prim;
	int primLiber;
	int capacitate;
	
	//se sterge primul element din lista spatiului liber
	int aloca();

	//se trece pozitia i in lista spatiului liber
	void dealoca(int i);

	///se sterge primul element din lista spatiului liber
	int alocaLista(lista* list);

	//se trece pozitia i in lista spatiului liber
	void dealocaLista(lista* list, int i);

	/// redimensioneaza lista
	void Redim();


public:
	// constructorul implicit al MultiDictionarului
	MD();

	// elimina o cheie impreuna cu toate valorile sale
	// returneaza un vector cu valorile care au fost anterior asociate acestei chei (si au fost eliminate)
	vector<TValoare>stergeValoriPentruCheie(TCheie cheie);

	// adauga o pereche (cheie, valoare) in MD	
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MD 
	int dim() const;

	//verifica daca MultiDictionarul e vid 
	bool vid() const;

	// se returneaza iterator pe MD
	IteratorMD iterator() const;

	// destructorul MultiDictionarului	
	~MD();



};
#pragma once
