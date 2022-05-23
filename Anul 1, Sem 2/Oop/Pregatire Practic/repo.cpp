#include "repo.h"
#include <fstream>

using namespace std;

void Repo::loadFromFile()
{
	ifstream fin(filename);
	if (!fin.is_open())
		throw RepoException("Nu se poate deschide fisierul!");

	while (!fin.eof())
	{
		string nume, gen;
		int an, ep, epv;
		fin >> nume;
		fin >> gen;
		fin >> an;
		fin >> ep;
		fin >> epv;

		if (fin.eof())
			break;

		SerialTV ser{ nume, gen, an, ep, epv };
		Repo: store(ser);
	}
	fin.close();
}

void Repo::storeToFile()
{
	ofstream fout(filename);
	if (!fout.is_open())
		throw RepoException("Nu se poate deschide fisierul!");

	auto rez = getAll();
	for (auto ser : rez)
	{
		fout << ser.getNume();
		fout << ser.getGen();
		fout << ser.getAn();
		fout << ser.getNrEp();
		fout << ser.getNrEpV();
	}
	fout.close();
}

void Repo::store(const SerialTV& ser)
{
	seriale.push_back(ser);
	storeToFile();
}

void Repo::modifica(SerialTV& ser)
{
	auto rez = getAll();
	for (auto serial : rez)
		if (serial.getNume() == ser.getNume())
			serial.setNrEpV(ser.getNrEpV());

	storeToFile();
}

vector<SerialTV> Repo::getAll()
{
	return seriale;
}

int Repo::cauta(SerialTV ser)
{
	auto rez = getAll();
	for (auto serial : rez)
		if (serial.getNume() == ser.getNume())
			return 1;
	return 0;
}
