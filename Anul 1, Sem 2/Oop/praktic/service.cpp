#include "service.h"

void Service::modifica(int id, int rank, string titlu)
{
}

vector<Melodie> Service::sorteaza()
{
	auto toti = getAll();
	int n = toti.size();

	for (int i = 0; i < n - 1; i++)
		for (int j = i + 1; j < n; j++)
			if (toti[i].getRank() > toti[j].getRank())
			{
				Melodie aux = toti[i];
				toti[i] = toti[j];
				toti[j] = aux;
			}
	return toti;
}

vector<Melodie> Service::getAll()
{
	return r.getAll();
}
