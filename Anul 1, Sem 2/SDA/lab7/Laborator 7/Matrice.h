#pragma once
#include <iostream>


typedef int TElem;

#define NULL_TELEMENT 0

struct nod {
	nod* stang;
	nod* drept;
	TElem val;
	int linie;
	int coloana;
};

class Matrice {

private:
	

	nod* rad;

	int lin, col;

	nod* ModificaRec(nod* curent, int i, int j, TElem e, TElem& vechi);

public:


	//constructor
	//se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);


	//destructor
	~Matrice() {};

	//returnare element de pe o linie si o coloana
	//se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	//indicii se considera incepand de la 0
	TElem element(int i, int j) const;

	// determina suma elementelor de sub diagonala principala
	TElem sumaSubDiagonalaPrincipala();

	nod* minim(nod* curent);

	void sterge(int i, int j);

	nod* stergereRec(int i, int j, nod* curent);

	TElem suma(nod* rad);


	// returnare numar linii
	int nrLinii() const;

	// returnare numar coloane
	int nrColoane() const;


	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);

};
