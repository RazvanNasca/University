#include "petservice.h"
#include <vector>
#include <algorithm>
#include <assert.h>
#include <iterator>
#include <memory>
#include "undo.h"
/*
Adauga pet
*/
void PetController::add(const string& type, const string& species, int price) {
	Pet p{ type,species,price };		
	repo.store(p);				
	undoActions.push_back(std::make_unique<UndoAdauga>(repo, p));
	//undoActions.push_back(new UndoAdauga {repo, p});
}


void PetController::undo() {
	if (undoActions.empty()) {
		throw PetException{"Nu mai exista operatii"};
	}
	/*
	ActiuneUndo* act = undoActions.back();
	act->doUndo();
	undoActions.pop_back();
	delete act;
	*/
	// Varianta cu unique_ptr
	//obs: la obiecte unique_ptr nu putem face copie			
	undoActions.back()->doUndo();
	undoActions.pop_back();	
}
PetController::~PetController() {
	
	/*In varianta cu unique_ptr nu mai e nevoie
	for (auto act : undoActions) {
		delete act;
	}*/
	
}
/*
Toate
*/
const std::vector<Pet>& PetController::getAllPets()const {
	return repo.getAll();	
}


/*
Sortat dupa pret
*/
std::vector<Pet> PetController::getSortByPrice()const {
	auto v = repo.getAll();
	std::sort(v.begin(), v.end(), [](const Pet& p1, const Pet& p2) {return p1.getPrice()<p2.getPrice(); });
	return v;
}
/*
Sortat dupa tip
*/
std::vector<Pet> PetController::getSortByType() const {
	auto v = repo.getAll();
	//folosim o functie lamba pentru compararea de elemente
	std::sort(v.begin(), v.end(), [](const Pet& p1, const Pet& p2) {
		return p1.getType()<p2.getType(); 
	});
	return v;
}
/*
Filtrare dupa pret
*/
std::vector<Pet> PetController::getFilterByPrice(int price) const {
	std::vector<Pet> rez;
	auto& all = repo.getAll();
	std::copy_if(all.begin(), all.end(),back_inserter(rez), [=](const Pet& p) {return p.getPrice() > price; });	
	return rez;
}

const std::vector<Pet>& PetController::addToCos(const std::string& type,const std::string& species) {
	const Pet& p = repo.find(type,species);
	cos.adauga(p);
	return cos.lista();
}

const std::vector<Pet>& PetController::addRandom(int cate) {
	cos.umple(cate,repo.getAll());
	return cos.lista();
}

const std::vector<Pet>& PetController::golesteCos() {
	cos.goleste();
	return cos.lista();
}

const std::vector<Pet>& PetController::toateDinCos() {
	return cos.lista();
}

void PetController::exportaCosCVS(std::string fName) const {
	exportToCVS(fName,cos.lista());
}

void PetController::exportaCosHTML(std::string fName) const {
	exportToHTML(fName, cos.lista());
}

void testFiltrare() {
	PetRepo rep;
	PetController ctr{ rep };
	ctr.add("aaa", "bbb", 3);
	ctr.add("ccc", "bbb", 2);
	ctr.add("bbb", "bbb", 1);

	std::vector<Pet> filtrat = ctr.getFilterByPrice(2);
	assert(filtrat.size() == 1);
	assert(filtrat[0].getPrice() == 3);
}
void testSortare() {
	PetRepo rep;
	PetController ctr{ rep };
	ctr.add("aaa", "bbb", 3);
	ctr.add("ccc", "bbb", 2);
	ctr.add("bbb", "bbb", 1);

	std::vector<Pet> v = ctr.getSortByPrice();
	assert(v[0].getPrice() == 1);
	assert(v[1].getPrice() == 2);
	assert(v[2].getPrice() == 3);

	std::vector<Pet> v2 = ctr.getSortByType();
	assert(v2.at(0).getType()=="aaa");
	assert(v2.at(1).getType()== "bbb");
	assert(v2.at(2).getType()== "ccc");

	//testam si assigment operator de la vectordinamic
	v = v2;
	assert(v[0].getType()=="aaa");
	assert(v[1].getType()== "bbb");
	assert(v[2].getType()== "ccc");
	
}

void testUndo() {
	PetRepo rep;
	PetController ctr{ rep };
	ctr.add("aaa", "bbb", 3);
	ctr.add("ccc", "bbb", 2);
	ctr.add("bbb", "bbb", 1);
	ctr.undo();
	assert(ctr.getAllPets().size() == 2);
	ctr.undo();
	ctr.undo();
	assert(ctr.getAllPets().size() == 0);
	try {
		ctr.undo();
		assert(false);
	}
	catch (PetException& ex) {
		assert(ex.getMsg() == "Nu mai exista operatii");
	}
}

void testAdauga() {
	PetRepo rep;
	PetController ctr{ rep };
	ctr.add("aaa", "bbb", 3);
	assert(ctr.getAllPets().size() == 1);
	ctr.add("ccc", "bbb", 2);
	assert(ctr.getAllPets().size() == 2);
	try {
		ctr.add("ccc", "bbb", 2);
		assert(false);
	}
	catch (PetException&) {
		assert(true);
	}
}
void testCos() {
	PetRepo rep;
	PetController ctr{ rep };
	ctr.add("aaa", "bbb", 3);
	ctr.add("ccc", "bbb", 2);
	ctr.add("bbb", "bbb", 1);
	assert(ctr.toateDinCos().size() == 0);
	ctr.addToCos("ccc", "bbb");
	assert(ctr.toateDinCos().size() == 1);
	try {
		ctr.addToCos("1ccc", "1bbb");
		assert(false);
	}
	catch (PetException&) {
		assert(true);
	}
	ctr.golesteCos();
	assert(ctr.toateDinCos().size() == 0);
	ctr.addRandom(5);
	assert(ctr.toateDinCos().size() == 3);//avem doar 3 pet
}
#include <fstream>
void testExporta() {
	PetRepo rep;
	PetController ctr{ rep };
	ctr.add("aaa", "bbb", 3);
	ctr.add("ccc", "bbb", 2);
	ctr.add("bbb", "bbb", 1);
	ctr.addRandom(3);
	ctr.exportaCosCVS("testExport.cvs");
	std::ifstream in("testExport.cvs");
	assert(in.is_open());
	int countLines = 0;
	while (!in.eof()) {
		string line;
		in >> line;
		countLines++;
	}
	in.close();
	assert(countLines == 4);//avem o linie pentru fiecare pet + o linie goala
	ctr.exportaCosHTML("testExport.html");
	in.open("testExport.html");
	assert(in.is_open());

	//daca se da un nume de fisier invalid se arunca exceptie
	try {
		ctr.exportaCosCVS("test/Export.cvs");
		assert(false);
	}
	catch (PetException&) {
		assert(true);
	}
	try {
		ctr.exportaCosHTML("a/b/c/Export.html");
		assert(false);
	}
	catch (PetException&) {
		assert(true);
	}
}

void testCtr() {
	testAdauga();
	testFiltrare();
	testSortare();
	testUndo();
	testCos();
	testExporta();
}