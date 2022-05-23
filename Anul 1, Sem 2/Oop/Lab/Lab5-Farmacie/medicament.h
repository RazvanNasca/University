#pragma once
#include<string>
#include<iostream>
using std::string;
using std::cout;

class Medicament
{
private:
	string denumire;
	string producator;
	double pret;
	int substanta_activa;
public:
	/*
		returneaza denumirea medicamentului
	*/
	string getDenumire() const;

	/*
		returneaza producatorul medicamentului
	*/
	string getProducator() const;

	/*
		returneaza pretul medicamentului
	*/
	double getPret() const noexcept;

	/*
		returneaza cantitatea de substanta activa a medicamentului
	*/
	int getSubstanta_activa() const noexcept;

	/*
		seteaza pretul medicamentului
	*/
	void setPret(double val) noexcept;

	/*
		seteaza cantitatea de substanta activa medicamentului
	*/
	void setSubstanta_activa(int val) noexcept;

	Medicament(string denumire, string producator, double pret, int substanta_activa) :denumire{ denumire }, producator{ producator }, pret{ pret }, substanta_activa{ substanta_activa }{}

	Medicament(const Medicament& ot) :denumire{ ot.denumire }, producator{ ot.producator }, pret{ ot.pret }, substanta_activa{ ot.substanta_activa }{
		cout << "Copy constructor called.\n";
	}

};
