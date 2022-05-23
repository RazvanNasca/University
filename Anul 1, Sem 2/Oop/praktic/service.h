#pragma once
#include "repo.h"

class Service {

private:

	Repo& r;

public:

	Service(Repo& r): r{r}{}

	void modifica(int id, int rank, string titlu);

	vector <Melodie> sorteaza();

	vector <Melodie> getAll();

};
