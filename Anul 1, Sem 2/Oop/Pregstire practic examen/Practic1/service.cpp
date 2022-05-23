#include "service.h"

void Service::adauga(int id, string descriere, string stare, vector<string> programatori)
{
	Task t{ id, descriere, stare, programatori };
	if (r.cautaID(t) == 0)
	{
		if (v.valid(t) == 0)
		{
			r.adauga(t);
			r.storeToFile();
		}
	}
	else
		throw RepoException("Id-ul exista!");
}

vector<Task> Service::getAll()
{
	return r.getAll();
}

void Service::modifica(int id, string stare)
{
	vector <string> programatori;
	programatori.push_back("Mircea");
	Task t{ id, "da", "open", programatori };

	if (r.cautaID(t) == 1)
	{
		r.modifica(t, stare);
	}
	else
		throw RepoException("Id-ul nu exista!");
}

vector<Task> Service::cautaProg(string p)
{
	return r.cautaProg(p);
}
