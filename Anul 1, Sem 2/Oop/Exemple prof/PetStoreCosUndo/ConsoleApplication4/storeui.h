#pragma once

#include "petservice.h"
#include <vector>
class PetUI {
private:
	PetController& ctr;
	/*
	tipareste meniu si citeste comanda
	*/
	int readCommand();
	/*
	Tipareste lista de pet
	*/
	void printPets(const std::string& title, const std::vector<Pet>& v);
	/*
	citest de la tastatura si adauga pet
	*/
	void addPet();
public:
	PetUI(PetController& ctr) noexcept :ctr{ ctr } {}
	void startUI();
};