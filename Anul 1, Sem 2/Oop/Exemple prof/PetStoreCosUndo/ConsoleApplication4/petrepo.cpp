#include "petrepo.h"
#include "pet.h"
#include <fstream>
#include <assert.h>

void PetRepoFile::loadFromFile(){
	std::ifstream in(fName);
	if (!in.is_open()) { //verify if the stream is opened		
		throw PetException("Unable to open file:"+fName);
	}	
	while (!in.eof()) {
		std::string species;
		in >> species;
		std::string type;
		in >> type;

		int price;
		in >> price;
		if (in.eof()) {	//nu am reusit sa citesc numarul
			break;
		}
		Pet p{type.c_str(), species.c_str(), price};
		PetRepo::store(p);
	}
	in.close();
}

void PetRepoFile::writeToFile() {
	std::ofstream out(fName);
	if (!out.is_open()) { //verify if the stream is opened
		throw PetException("Unable to open file:");
	}
	for (auto& p:getAll()) {
		out << p.getSpecies();
		out << std::endl;
		out << p.getType();
		out << std::endl;
		out << p.getPrice();
		out << std::endl;
	}
	out.close();
}

void testFileRepo() {
	std::ofstream out("testPets.txt", std::ios::trunc);
	out.close();//creez un fisier gol
	PetRepoFile repF{ "testPets.txt" };
	repF.store(Pet{ "aaa","bbb",12 });

	PetRepoFile repF2{ "testPets.txt" };
	auto p = repF2.find("aaa", "bbb");
	assert(p.getPrice() == 12);
	try {
		repF2.find("aaa2", "bbb2");
		assert(false);
	}
	catch (PetException&) {
	}
	repF2.sterge(Pet{ "aaa","bbb",12 });
	assert(repF2.getAll().size()==0);
	try {
		repF2.sterge(Pet{ "aaa2","bbb2",12 });
		assert(false);
	}
	catch (PetException&) {
	}

	PetRepoFile repF3{ "testPets.txt" };
	assert(repF3.getAll().size() == 0);

	//fisierul nu exista si nu se poate crea (nu se creaza si directoare)
	//ar trebui sa arunce exceptie
	try {
		PetRepoFile repF4{ "te/stPets.txt" };
		assert(false);
	}
	catch (PetException&) {		
		assert(true);
	}


}

void testPetRepos() {
	testFileRepo();
}