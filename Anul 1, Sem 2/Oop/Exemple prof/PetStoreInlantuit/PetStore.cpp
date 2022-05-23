#include "PetStore.h"

#include <functional>
#include <algorithm>
#include <assert.h>
using namespace std;

MyLista<Pet> PetStore::generalSort(bool(*maiMicF)(const Pet&, const Pet&)) {
	MyLista<Pet> v{ rep.getAll() };//fac o copie	
	for (size_t i = 0; i < v.size(); i++) {
		for (size_t j = i + 1; j < v.size(); j++) {
			if (!maiMicF(v[i], v[j])) {
				//interschimbam
				Pet aux = v[i];
				v[i] = v[j];
				v[j] =  aux;
			}
		}
	}
	return v;
}

/*
Adauga un nou pet
arunca exceptie daca: nu se poate salva, nu este valid
*/
void PetStore::addPet(const string& type, const string& species, int price) {
	Pet p{ type,species,price };
	val.validate(p);
	rep.store(p);
}

/*
Sorteaza dupa tip
*/
MyLista<Pet> PetStore::sortByType() {
	return generalSort(cmpType);
}

/*
Sorteaza dupa species
*/
MyLista<Pet> PetStore::sortBySpecies() {
	//return generalSort(cmpSpecies);
	return generalSort([](const Pet&p1, const Pet&p2) {		
		return p1.getSpecies() < p2.getSpecies();
	});
}


/*
Sorteaza dupa species+price
*/
MyLista<Pet> PetStore::sortBySpeciesPrice() {
	return generalSort([](const Pet&p1, const Pet&p2) {
		if (p1.getSpecies() == p2.getSpecies()) {
			return p1.getPrice() < p2.getPrice();
		}
		return p1.getSpecies() < p2.getSpecies();
	});	
}

MyLista<Pet> PetStore::filtreaza(function<bool(const Pet&)> fct) {
	MyLista<Pet> rez;
	for (const auto& pet : rep.getAll()) {
		if (fct(pet)) {
			rez.push_back(pet);
		}
	}
	return rez;
}

MyLista<Pet> PetStore::filtrarePretMaiMic(int pret) {
	return filtreaza([pret](const Pet& p) {
		return p.getPrice() < pret;
	});
}

MyLista<Pet> PetStore::filtrarePret(int pretMin, int pretMax) {
	return filtreaza([=](const Pet& p) {
		return p.getPrice() >= pretMin && p.getPrice() <= pretMax;
	});
}


void testAdaugaCtr() {
	PetRepo rep;
	PetValidator val;
	PetStore ctr{ rep,val };
	ctr.addPet("a", "a", 6);
	assert(ctr.getAll().size() == 1);

	//adaug ceva invalid
	try {
		ctr.addPet("", "", -1);
		assert(false);
	}
	catch (ValidateException&) {
		assert(true);
	}
	//incerc sa adaug ceva ce existadeja
	try {
		ctr.addPet("a", "a", -1);
		assert(false);
	}
	catch (ValidateException&) {
		assert(true);
	}
}

void testFiltrare() {
	PetRepo rep;
	PetValidator val;
	PetStore ctr{ rep,val };
	ctr.addPet("a", "a", 6);
	ctr.addPet("b", "a", 60);
	ctr.addPet("c", "a", 600);
	assert(ctr.filtrarePret(6, 70).size() == 2);
	assert(ctr.filtrarePret(6, 60).size() == 2);
	assert(ctr.filtrarePretMaiMic(60).size() == 1);
	assert(ctr.filtrarePretMaiMic(7).size() == 1);
	assert(ctr.filtrarePretMaiMic(6).size() == 0);
}

void testSortare() {
	PetRepo rep;
	PetValidator val;
	PetStore ctr{ rep,val };
	ctr.addPet("z", "z", 6);
	ctr.addPet("b", "a", 60);
	ctr.addPet("c", "a", 600);

	auto firstP = ctr.sortByType().first();
	assert(firstP.getType() == "b");

	firstP = ctr.sortBySpecies().first();
	assert(firstP.getSpecies() == "a");

	firstP = ctr.sortBySpeciesPrice().first();
	assert(firstP.getPrice() == 60);

}

void testCtr() {
	testAdaugaCtr();
	testFiltrare();
	testSortare();
}
