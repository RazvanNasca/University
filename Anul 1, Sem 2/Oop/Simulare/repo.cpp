#include "repo.h"
#include <iostream>
#include <fstream>

using namespace std;

void Repo::adauga(Concert c)
{
	Concerte.push_back(c);
}
/// incarca din fiser
void Repo::loadFromFile()
{
	ifstream fin(filename);
	if (!fin.is_open())
		throw RepoException("Fisierul nu poate fii deschis!");

	while (!fin.eof())
	{
		string nume, data;
		int nrBilete;
		double pret;

		fin >> nume >> data >> nrBilete >> pret;

		if (fin.eof())
			break;

		Concert c{ nume, data, nrBilete, pret };
		adauga(c);
	}

	fin.close();
}

///returneaza toate concertele
vector<Concert> Repo::getAll()
{
	return Concerte;
}
