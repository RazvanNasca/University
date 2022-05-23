#include "repo.h"
#include <fstream>


void Repo::adauga(Task t)
{
	taskuri.push_back(t);
}

void Repo::modifica(Task t, string st)
{
	auto& rez = getAll();
	for(auto& it: rez)
		if(it.getID() == t.getID())
			t.setStare(st);
}

vector<Task> Repo::getAll()
{
	return taskuri;
}

int Repo::cautaID(Task t)
{
	vector <Task> tasks = taskuri;
	for (const auto& it : tasks)
		if (it.getID() == t.getID())
			return 1;
	return 0;
}

vector<Task> Repo::cautaProg(string p)
{
	vector <Task> rez;
	auto& toate = getAll();
	for (auto& t : toate)
	{
		auto& toti = t.getProgramatori();
		for (auto& it : toti)
			if (it == p)
				rez.push_back(t);
	}

	return rez;
}

void Repo::loadFromFile()
{
	std::ifstream fin(filename);
	if (!fin.is_open())
		throw RepoException("Fisieru nu poate fi deschis!");

	while (!fin.eof())
	{
		int id;
		string descriere, stare, programator;
		vector <string> programatori;

		fin >> id >> descriere >> stare >> programator;

		while (programator != "stop")
		{
			programatori.push_back(programator);
			fin >> programator;
		}

		if (fin.eof())
			break;

		Task t{ id, descriere, stare, programatori };
		adauga(t);
	}

	fin.close();
}

void Repo::storeToFile()
{
	std::ofstream fout(filename);
	if (!fout.is_open());
	throw RepoException("Fisierul nu poate fi deschis");

	auto rez = getAll();
	for (auto& t : rez)
	{
		fout << t.getID() << "\n";
		fout << t.getDescriere() << "\n";
		fout << t.getStare() << "\n";
		
		auto programatori = t.getProgramatori();
		for (auto& it : programatori)
			fout << it << "\n";
		fout << "stop" << "\n";
	}

	fout.close();
}

string RepoException::getMessage()
{
	return msg;
}
