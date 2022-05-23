#pragma once
#include "repo.h"

class Service {

private:
	Repo& r;

public:

	Service(Repo& r) : r{ r } {};

	void adaugaRandom(int k);

	string randomString();

	vector <Concert> getAll();


	vector <Concert> filtreaza(int numar);

};