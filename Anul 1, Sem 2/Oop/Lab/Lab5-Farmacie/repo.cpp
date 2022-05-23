#include"repo.h"
#include<iostream>
#include<vector>
using std::cout;
using namespace std;

string RepoException::getMessage()
{
	return msg;
}

void Repo::store(const Medicament& m)
{
	for (const Medicament& med : medicamente)
		if (m.getDenumire() == med.getDenumire() && m.getProducator() == med.getProducator())
			throw RepoException("Medicament deja adaugat!\n");

	medicamente.push_back(m);
}

const vector <Medicament>& Repo::getAll() noexcept
{
	return medicamente;
}

int Repo::cauta(const Medicament& m)
{
	int poz = 0;
	for (const Medicament& med : medicamente)
		if (m.getDenumire() == med.getDenumire() && m.getProducator() == med.getProducator())
			return poz;
		else
			poz++;

	throw RepoException("Medicamentul nu este adaugat!\n");
}

int Repo::actualizare(const Medicament& m, int poz) 
{
	
	medicamente.at(poz).setPret(m.getPret());
	medicamente.at(poz).setSubstanta_activa(m.getSubstanta_activa());
	return 0;	
}

int Repo::stergere(const Medicament& m, int poz)
{
	medicamente.erase(medicamente.begin() + poz);
	return 0;
}

