#pragma once

#include <string.h>
#include <iostream>
#include <string>
using std::string;
class PetException {
	std::string msg;
public:
	PetException(const string& m) :msg{ m } {};
	string getMsg()const { return msg; }
};


class Pet {
private:
	string type;
	string species;
	int price;
public:
	/*
	Constructor default
	*/
	Pet() = default;
	/*
	  Constructor 3 param
	*/
	Pet(const string& t, const string& s, int p) :type{ t }, species{ s }, price{ p } {
	}
	/*
	Constuructor copiere
	Copieaza continutul lui p in obiectul current
	*/
	Pet(const Pet& p) = default;
	//Pet(const Pet& p) :type{ p.getType() }, species{ p.getSpecies() }, price{ p.getPrice() } {
	//	std::cout << "Copie pet!!!!!\n";
	//}


	/*
	Operator de assignare
	apelat cand se scrie p1 = p2;
	Continutul lui p2 (ot) copiat in p1
	Obs: p1 are deja ceva inainte de atribuire - trebuie dealocat memoria daca este cazul
	*/
	Pet& operator=(const Pet& ot) = default;
	//void operator=(const Pet& ot) {
	//	type = ot.type;
	//	species = ot.species;
	//	price = ot.price;
	//	std::cout << "Assignment pet!!!!!\n";
	//}

	int getPrice() const {
		return price;
	}

	void setPrice(int p) {
		this->price = p;
	}

	string getType() const {
		return type;
	}
	string getSpecies() const {
		return species;
	}
};

int cmpByPrice(const Pet& p1, const Pet& p2);

int cmpByType(const Pet& p1, const Pet& p2);

void testPet();
