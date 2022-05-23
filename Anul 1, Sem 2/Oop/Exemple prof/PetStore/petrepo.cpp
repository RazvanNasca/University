#include "petrepo.h"
#include <assert.h>
#include <iostream>
#include <sstream>

using std::ostream;
using std::stringstream;

void PetRepo::store(const Pet& p)
{
	if (exist(p)) {
		throw PetRepoException("Exista deja animal tip:" + p.getType() + " specie:" + p.getSpecies());
	}
	all.push_back(p);
}

bool PetRepo::exist(const Pet& p) const{
	try {
		find(p.getType(), p.getSpecies());
		return true;
	}
	catch (PetRepoException& ) {
		return false;
	}
}
/*
Cauta
arunca exceptie daca nu exista animal
*/
const Pet& PetRepo::find(string type, string species) const{
	for (const auto& p : all) {
		if (p.getType() == type && p.getSpecies() == species) {
			return p;
		}
	}
	//daca nu exista arunc o exceptie
	throw PetRepoException("Nu exista animal tip:" + type + " specie:" + species);
}

/*
returneaza toate animalele salvate
*/
const vector<Pet>& PetRepo::getAll() const noexcept{
	return all;
}


ostream& operator<<(ostream& out, const PetRepoException& ex) {
	out << ex.msg;
	return out;
}




void testAdauga() {
	PetRepo rep;
	rep.store(Pet{ "a","a",4 });
	assert(rep.getAll().size() == 1);
	assert(rep.find("a", "a").getSpecies() == "a");

	rep.store(Pet{ "b","b",4 });
	assert(rep.getAll().size() == 2);

	//nu pot adauga 2 cu acelasi tip si specie
	try {
		rep.store(Pet{ "a","a",4 });
		assert(false);
	}
	catch (const PetRepoException&) {
		assert(true);
	}
	//cauta inexistent
	try {
		rep.find("c", "c");
		assert(false);
	}
	catch (const PetRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista")>=0);
	}
}

void testCauta() {
	PetRepo rep;
	rep.store(Pet{ "a","a",4 });
	rep.store(Pet{ "b","b",4 });

	auto p = rep.find("a", "a");
	assert(p.getSpecies() == "a");
	assert(p.getType() == "a");

	//arunca exceptie daca nu gaseste
	try {
		rep.find("a", "b");
		assert(false);
	}
	catch (PetRepoException&) {
		assert(true);
	}
}

void testeRepo() {
	testAdauga();
	testCauta();
}