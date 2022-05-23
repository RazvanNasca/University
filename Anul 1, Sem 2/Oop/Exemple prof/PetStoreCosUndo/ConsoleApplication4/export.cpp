#include "export.h"
#include "pet.h"
#include <fstream>
#include <string>
#include <vector>
/*
Scrie in fisierul fName lista de animale
in format Comma Separated Values
arunca PetException daca nu poate crea fisierul
*/
void exportToCVS(const std::string& fName,const std::vector<Pet>& pets) {
	std::ofstream out(fName, std::ios::trunc);	
	if (!out.is_open()) {
		throw PetException("Unable to open file:" + fName);
	}
	for (const auto& p : pets) {
		out << p.getSpecies()<<",";
		out << p.getType() << ",";
		out << p.getPrice() << std::endl;
	}
	out.close();
}
/*
Scrie in fisierul fName lista de animale
in format HTML
arunca PetException daca nu poate crea fisierul
*/
void exportToHTML(const std::string& fName, const std::vector<Pet>& pets) {
	std::ofstream out(fName, std::ios::trunc);
	if (!out.is_open()) {
		throw PetException("Unable to open file:" + fName);
	}
	out << "<html><body>" << std::endl;
	out << "<table border=\"1\" style=\"width:100 % \">" << std::endl;
	for (const auto& p : pets) {
		out << "<tr>" << std::endl;
		out << "<td>" << p.getSpecies() << "</td>"<<std::endl;
		out << "<td>" << p.getType() << "</td>" << std::endl;
		out << "<td>" << p.getPrice() << "</td>" << std::endl;
		out << "</tr>" << std::endl;
	}
	out << "</table>" << std::endl;
	out << "</body></html>" << std::endl;
	out.close();
}