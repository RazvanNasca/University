#include "console.h"

#include "Pet.h"
#include <iostream>
#include <string>

using std::cout;
using std::cin;

void ConsolUI::tipareste(const vector<Pet>& pets) {
	cout << "Animale:\n";
	for (const auto& pet : pets) {
		cout << ' ' << pet.getType() << ' ' << pet.getSpecies() << ' ' << pet.getPrice() << '\n';
	}
	cout << "Sfarsit lista animale\n";
}

void ConsolUI::adaugaUI() {
	string ty, sp;
	int price;
	cout << "Dati tip:";
	cin >> ty;
	cout << "Dati specie:";
	cin >> sp;
	cout << "Dati pret:";
	cin >> price;
	ctr.addPet(ty, sp, price);
	cout << "Adaugat cu succes\n";
}

void ConsolUI::start() {
	while (true) {
		cout << "Meniu:\n";
		cout << "1 adauga\n2 tipareste\n3 sort by type\n4 sort by species\n5 sort by species+price\n6 filtrare pret\n7 Filtrare pret min max\n0 Iesire\nDati comanda:";
		int cmd;
		cin >> cmd;
		try {
			switch (cmd) {
			case 1:
				adaugaUI();
				break;
			case 2:
				tipareste(ctr.getAll());
				break;
			case 3:
				tipareste(ctr.sortByType());
				break;
			case 4:
				tipareste(ctr.sortBySpecies());
				break;
			case 5:
				tipareste(ctr.sortBySpeciesPrice());
				break;
			case 6:
				cout << "Dati pret maxim:";
				int pret;
				cin >> pret;
				tipareste(ctr.filtrarePretMaiMic(pret));
				break;
			case 7:
				cout << "Dati pret minim:";
				int pretMin;
				cin >> pretMin;
				cout << "Dati pret maxim:";
				int pretMax;
				cin >> pretMax;
				tipareste(ctr.filtrarePret(pretMin,pretMax));
				break;
			case 0:
				return;
			default:
				cout << "Comanda invalida\n";
			}			
		}
		catch (const PetRepoException& ex) {
			cout << ex <<'\n';
		}
		catch (const ValidateException& ex) {
			cout << ex << '\n';
		}
	}
}