#include "repo.h"

void Repo::storeToFile()
{
	ofstream fout(filename);
	if (!fout.is_open())
		throw RepoException("Fisierul nu se poate deschide!");

	for(auto t : tasks)
		for (auto p : t.getProgramatori())
		{
			fout << t.getId() << "\n";
			fout << t.getDescriere() << "\n";
			fout << p << "\n";
			fout << t.getStare() << "\n";
		}

	fout.close();
}

void Repo::loadFromFile()
{
	ifstream fin(filename);

	if (!fin.is_open())
		throw RepoException("Fisierul nu poate fi deschis!");

	int id;
	string descriere, stare, aux;
	vector<string> programatori;

	while (!fin.eof())
	{
		fin >> id;
		fin >> descriere;
		fin >> stare;
		fin >> aux;

		programatori.clear();
		programatori.push_back(aux);

		if (fin.eof())
			break;

		Task t{ id,descriere, programatori, stare };
		adauga(t);
	}

	fin.close();

}

bool Repo::exista(Task t)
{
	for (auto it : tasks)
		if (it.getId() == t.getId())
			return true;
	return false;
}

int Repo::getPoz(Task t)
{
	int poz = 0;
	for (auto it : tasks)
	{
		if (it.getId() == t.getId())
			break;
		poz++;
	}

	if (poz == tasks.size())
		return -1;

	return poz;
}

vector<string> Repo::getProg()
{
	vector<string> rez;
	auto all = tasks;

	for (auto it : all)
	{
		for (auto p : it.getProgramatori())
		{
			bool gasit = false;
			for (auto pp : rez)
				if (pp == p)
					gasit = true;

			if (gasit == false)
				rez.push_back(p);
		}
	}

	return rez;

}

void Repo::adauga(Task t)
{
	for (auto it : tasks)
		if (it.getId() == t.getId())
			if (it.getProgramatori().size() == 4)
				throw RepoException("Prea multi programatori pentru acest id!");

	int poz = getPoz(t);
	if (poz == -1)
		tasks.push_back(t);
	else
		tasks[poz].setProgramatori(t.getProgramatori()[0]);

	storeToFile();
}

vector<Task> Repo::getAll()
{
	return tasks;
}

Task Repo::cauta(int id)
{
	for (auto t : tasks)
		if (t.getId() == id)
			return t;

	throw RepoException("Task-ul nu exista1");
}

vector<Task> Repo::getTasksProg(string nume)
{
	vector<Task> rez;
	for(auto t : tasks)
		for(auto p : t.getProgramatori())
			if (p.find(nume) != string::npos)
			{
				rez.push_back(t);
				break;
			}

	return rez;
}

vector<Task> Repo::sorteazaDescriere()
{
	vector <Task> all = tasks;
	int n = all.size();
	for (int i = 0; i < n - 1; i++)
		for(int j = i + 1; j < n; j++)
			if (all[i].getDescriere() > all[j].getDescriere())
			{
				Task aux = all[i];
				all[i] = all[j];
				all[j] = aux;
			}

	return all;
}

void Repo::sterge(int p)
{
	tasks.erase(tasks.begin() + p);

	storeToFile();
}

