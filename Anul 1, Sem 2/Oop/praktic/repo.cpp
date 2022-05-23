#include "repo.h"

vector<Melodie> Repo::getAll()
{
	return melodii;
}

void Repo::modifica(Melodie m1, Melodie m2)
{
	m1.setRank(m2.getRank());
	m1.setTitlu(m2.getTitlu());
}

void Repo::adauga(Melodie m)
{
	melodii.push_back(m);
}

void Repo::loadFromFile()
{
	ifstream fin(filename);
	if (!fin.is_open())
		throw RepoException("Fisierul nu poate fi deschis!");

	while (!fin.eof())
	{
		int id;
		string titlu;
		string artist;
		int rank;

		fin >> id >> titlu >> artist >> rank;

		if (fin.eof())
			break;

		Melodie m{ id, titlu, artist, rank };
		adauga(m);
	}

	fin.close();

}

void Repo::storeToFile()
{
	ofstream fout(filename);
	if (!fout.is_open())
		throw RepoException("Fisierul nu poate fi deschis!");

	auto all = getAll();

	for (auto& one : all)
	{
		fout << one.getId() << " ";
		fout << one.getTitlu() << " ";
		fout << one.getArtist() << " ";
		fout << one.getRank() << " ";
	}

	fout.close();

}
