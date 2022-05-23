#pragma once
#include "Pet.h"

#include <vector>
#include <string>
#include <ostream>

using std::vector;
using std::string;
using std::ostream;

class PetRepo {
	vector<Pet> all;
	/*
	metoda privata verifica daca exista deja p in repository
	*/
	bool exist(const Pet& p) const;
public:
	PetRepo() = default;
	//nu permitem copierea de obiecte
	PetRepo(const PetRepo& ot) = delete;
	/*
	Salvare pet
	arunca exceptie daca mai exista un animal cu acelasi tip si specie
	*/
	void store(const Pet& p);

	/*
	Cauta
	arunca exceptie daca nu exista animal
	*/
	const Pet& find(string type, string species) const;

	/*
	returneaza toate animalele salvate
	*/
	const vector<Pet>& getAll() const noexcept;

};

/*
Folosit pentru a semnala situatii exceptionale care pot aparea in repo
*/
class PetRepoException {
	string msg;
public:
	PetRepoException(string m) :msg{ m } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const PetRepoException& ex);
};

ostream& operator<<(ostream& out, const PetRepoException& ex);

void testeRepo();