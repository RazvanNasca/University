#include "service.h"
#include <random>

void Service::adaugaRandom(int k)
{
	for (int i = 1; i <= k; i++)
	{
		string nume = randomString();
		string data = randomString();
		int nr = rand();
		double pret = rand();

		Concert c{ nume, data, nr, pret };
		r.adauga(c);
	}
}

string Service::randomString()
{
	string str("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqestuvwxyz");

	random_device rand;
	mt19937 generare(rand());

	shuffle(str.begin(), str.end(), generare);

	return str.substr(0, 5);
}

vector<Concert> Service::getAll()
{
	return r.getAll();
}

vector<Concert> Service::filtreaza(int numar)
{
	vector <Concert> rez;

	auto toate = getAll();

	for (auto it : toate)
		if (it.getPret() < numar)
			rez.push_back(it);

	return rez;
}
