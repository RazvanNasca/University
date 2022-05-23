#pragma once
#include "PetStore.h"
#include "Pet.h"
class ConsolUI {
	PetStore& ctr;
	/*
	Citeste datele de la tastatura si adauga Pet
	arunca exceptie daca: nu se poate salva, nu e valid
	*/
	void adaugaUI();
	/*
	Tipareste o lista de animale la consola
	*/
	void tipareste(const vector<Pet>& pets);
public:
	ConsolUI(PetStore& ctr) :ctr{ ctr } {
	}
	//nu permitem copierea obiectelor
	ConsolUI(const ConsolUI& ot) = delete;

	void start();
};