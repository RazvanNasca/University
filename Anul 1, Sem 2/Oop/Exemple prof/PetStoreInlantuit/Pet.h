#pragma once
#include <string>
#include <iostream>

using namespace std;

class Pet {
	std::string type;
	std::string species;
	int price;
public:
	Pet(const string t, const string s, int p):type{t},species{s},price{p}{}

	/*Pet(const Pet& ot):type{ ot.type }, species{ ot.species }, price{ ot.price } {
		cout << "!!!!!!!!!!!!!!!!!!!!\n";
	}*/
	

	string getType() const {
		return type;
	}
	string getSpecies() const {
		return species;
	}

	int getPrice() const {
		return price;
	}
};
/*
Compara dupa tip
returneaza true daca p1.tip e mai mic decat p2.tip
*/
bool cmpType(const Pet& p1, const Pet& p2);

/*
Compara dupa specie
returneaza true daca p1.specie e mai mic decat p2.specie
*/
bool cmpSpecies(const Pet& p1, const Pet& p2);

