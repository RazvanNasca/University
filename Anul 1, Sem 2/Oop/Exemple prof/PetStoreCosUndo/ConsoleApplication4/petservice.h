#pragma once
#include "pet.h"
#include "petrepo.h"
#include <vector>
#include "undo.h"
#include <memory>
#include "CosPet.h"
#include "export.h"
using std::unique_ptr;

class PetController {
private:
	PetRepo& repo;
	//tinem pointeri la ActiuneUndo pentru ca vrem apel polimorfic
	//punem unique_ptr pentru ca avem responsabilitatea de a elibera memoria pentru 
	std::vector<unique_ptr<ActiuneUndo>> undoActions;
	//std::vector<ActiuneUndo*> undoActions;

	CosPet cos;
public:
	PetController(PetRepo& rep) :repo{ rep } {}
	~PetController();
	PetController(const PetController& ot) = delete;//nu vreau sa se copiez
	void operator=(const PetController& ot) = delete;//nu vreau sa mearga assignment
	/*
	Adauga pet
	Arunca PetException daca mai exista pet
	*/
	void add(const string& type, const string& species, int price);
	
	/*
	  Toate
	*/
	const std::vector<Pet>& getAllPets() const;

	/*
	Sortat dupa pret
	*/
	std::vector<Pet> getSortByPrice() const;
	/*
	Sortat dupa tip
	*/
	std::vector<Pet> getSortByType() const;

	std::vector<Pet> getFilterByPrice(int price) const;

	void undo();

	const std::vector<Pet>& addToCos(const std::string& species, const std::string& type);

	const std::vector<Pet>& addRandom(int cate);

	const std::vector<Pet>& golesteCos();
	
	const std::vector<Pet>& toateDinCos();

	void exportaCosCVS(std::string fName) const;

	void exportaCosHTML(std::string fName)const;

};


void testCtr();