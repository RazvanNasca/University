#pragma once
#include "pet.h"
#include <vector>
#include <algorithm>
#include <string>

class PetRepo {
private:
	std::vector<Pet> all;
public:
	PetRepo() = default;

	PetRepo(const PetRepo& ot) = delete;//nu vreau sa se copieze repo
	virtual ~PetRepo() = default;
	/*
	Salveaza pet
	*/
	virtual void store(const Pet& p) {
		//verificam sa nu mai existe un pet 
		auto found = std::find_if(all.begin(), all.end(), [p](const Pet& pp) {
			return pp.getSpecies() == p.getSpecies() && pp.getType() == p.getType(); 
		});
		if (found != all.end()) {			
			throw PetException{ "Pet existent!!!" };
		}
		all.push_back(p);
	}

	const Pet& find(std::string type, std::string species) {
		auto found = std::find_if(all.begin(), all.end(), [&](const Pet& pp) {
			return pp.getSpecies() == species && pp.getType() == type;
		});
		if (found == all.end()) {
			throw PetException{ "Pet inexistent!!!" };
		}
		return *found;
	}
	virtual void sterge(const Pet& p) {
		auto found = std::find_if(all.begin(), all.end(), [p](const Pet& pp) {
			return pp.getSpecies() == p.getSpecies() && pp.getType() == p.getType();
		});
		if (found == all.end()) {
			throw PetException{ "Pet inexistent!!!" };
		}
		//stergem pet
		auto rez = all.erase(found);
	}

	const std::vector<Pet>& getAll() const{
		return all;
	}
};

class PetRepoFile :public PetRepo {
private:
	std::string fName;
	void loadFromFile();
	void writeToFile();
public:
	PetRepoFile(std::string fName) :PetRepo(), fName{ fName } {
		loadFromFile();//incarcam datele din fisier
	}
	void store(const Pet& p) override {
		PetRepo::store(p);//apelam metoda din clasa de baza
		writeToFile();
	}
	void sterge(const Pet& p) override {
		PetRepo::sterge(p);//apelam metoda din clasa de baza
		writeToFile();
	}
};

void testPetRepos();