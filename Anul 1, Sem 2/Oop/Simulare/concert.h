#pragma once
#include <string>

using namespace std;

class Concert {

private:

	string nume;
	string data;
	int nrBilete;
	double pret;

public:

	Concert(string nume, string data, int nrBilete, double pret) : nume{ nume }, data{ data }, nrBilete{ nrBilete }, pret{ pret }{};

	/// returneaza numele concerutlui
	string getNume();

	/// returneaza data concertului
	string getData();

	///returneaza numarul de bilete
	int getNr();

	///returneaza pretul unui bilet
	double getPret();

};
