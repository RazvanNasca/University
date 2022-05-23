#pragma once
#include "medicament.h"

class ValidException
{
	string msg;
public:
	ValidException(string m) :msg{ m } {}
	string getMessage();
};

class Valid
{

public:
	Valid(const Valid& ot) = delete; /// nu permite copierea obiectelor
	Valid() = default;

	/*
		valideaza un medicament introdus
		intput: m - medicamentul care urmeaza sa fie validat
		output: returneaza 0 daca datele sunt valide, ridica o exceptie corespunzatoare in caz contrar
	*/
	int validare_medicament(const Medicament& m) const;
};
