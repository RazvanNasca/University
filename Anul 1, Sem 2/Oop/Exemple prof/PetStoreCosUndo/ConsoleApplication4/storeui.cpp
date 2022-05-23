#include "storeui.h"
#include <iostream>
#include "pet.h"
#include <vector>
/*
  Citeste date de la consola (int,float, double, string, etc)
  Reia citirea pana cand utilizatorul introduce corect
*/
template<typename T>
T myread(const char* msg) {
	T cmd{};
	while (true) {
		std::cout << std::endl << msg;
		std::cin >> cmd;
		const bool fail = std::cin.fail();
		std::cin.clear();//resetez failbit
		const auto& aux = std::cin.ignore(1000, '\n');
		if (!fail && aux.gcount() <= 1) {
			break; //am reusit sa citesc numar
		}
	}
	return cmd;
}
/*
tipareste meniu si citeste comanda
*/
int PetUI::readCommand() {
	std::cout << " 1 Adauga \n 2 Toate\n 3 Filtreaza pret\n 4 Sortat dupa tip\n 5 Sortat dupa pret\n";
	std::cout << " 6 Undo\n 7 Adauga la cos\n 8 Goleste cos\n 9 Adauga random\n10 Exporta CVS\n11 Exporta HTML\n";
	std::cout << " 0 Iesire\n";

	return myread<int>("Dati comanda:");
}

void printTableHeader() {
	std::cout.width(10);
	std::cout << "Type";
	std::cout.width(20);
	std::cout << "Species";
	std::cout.width(10);
	std::cout << "Price";
	std::cout << std::endl;
}

/*
Tipareste lista de pet
*/
void PetUI::printPets(const std::string& title,const std::vector<Pet>& v) {
	std::cout << std::endl<<title<< "(" << v.size() << "):\n";
	printTableHeader();
	for (const Pet& p : v) {
		std::cout.width(10);
		std::cout << p.getType();
		std::cout.width(20);
		std::cout << p.getSpecies();
		std::cout.width(10);
		std::cout << p.getType();
		std::cout << std::endl;
	}
	std::cout << std::endl;
}
/*
citest de la tastatura si adauga pet
*/
void PetUI::addPet() {
	string type;
	string species;
	int price;
	std::cout << "\nType:";
	std::cin >> type;
	std::cout << "\nSpecies:";
	std::cin >> species;
	std::cout << "\nPrice:";
	price = myread<int>("\nPrice:");
	ctr.add(type, species, price);
}


void PetUI::startUI()
{
	while (true) {
		const int cmd = readCommand();
		if (cmd == 0) {
			break;
		}
		try {
			if (cmd == 1) {
				addPet();
			}
			if (cmd == 2) {				
				printPets("Pets ", ctr.getAllPets());
			}
			if (cmd == 3) {
				const int price = myread<int>("Pret:");
				printPets("Pets ", ctr.getFilterByPrice(price));
			}
			if (cmd == 4) {
				printPets("Pets ", ctr.getSortByType());
			}
			if (cmd == 5) {
				printPets("Pets ", ctr.getSortByPrice());
			}
			if (cmd == 6) {
				ctr.undo();
				printPets("Pets ", ctr.getAllPets());
			}
			if (cmd == 7) {				
				printPets("Cos Pets ", ctr.addToCos(myread<std::string>("Species:"), myread<std::string>("Type:")));
			}
			if (cmd == 8) {
				printPets("Cos Pets ", ctr.golesteCos());
			}
			if (cmd == 9) {
				printPets("Cos Pets ", ctr.addRandom(myread<int>("Cate:")));
			}
			if (cmd == 10) {
				ctr.exportaCosCVS(myread<string>("CVS File:"));				
			}
			if (cmd == 11) {
				ctr.exportaCosHTML(myread<string>("HTML File:"));
			}
		}
		catch (PetException& ex) {
			std::cout << ex.getMsg() << std::endl;
		}
	}
}
