#include "service.h"

void Service::adauga(int id, string descriere, vector<string> programatori, string stare)
{
	Task t{ id, descriere, programatori, stare };

	v.validare(t);
	r.adauga(t);
}

vector<Task> Service::getAll()
{
	return r.getAll();
}

vector<Task> Service::sorteazaDescriere()
{
	vector <Task> all = r.getAll();
	int n = all.size();
	for (int i = 0; i < n - 1; i++)
		for (int j = i + 1; j < n; j++)
			if (all[i].getDescriere() > all[j].getDescriere())
			{
				Task aux = all[i];
				all[i] = all[j];
				all[j] = aux;
			}

	return all;
}

vector<Task> Service::getTasksProg(string nume)
{
	return r.getTasksProg(nume);
}

void Service::sterge(Task t)
{
	r.sterge(r.getPoz(t));
}

vector<string> Service::getProg()
{
	return r.getProg();
}
