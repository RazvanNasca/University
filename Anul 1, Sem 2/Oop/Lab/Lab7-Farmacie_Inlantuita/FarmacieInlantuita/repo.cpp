#include"repo.h"
#include<iostream>
#include"Lista.h"

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

const Lista <Medicament>& Repo::getAll() noexcept
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

	medicamente[poz].setPret(m.getPret());
	medicamente[poz].setSubstanta_activa(m.getSubstanta_activa());
	return 0;
}

int Repo::stergere(const Medicament& m, int poz)
{
	medicamente.erase(poz);
	return 0;
}

void DTO::incrementeaza()
{
	cnt++;
}

string DTO::get_denumire()
{
	return denumire;
}

string DTO::get_producator()
{
	return producator;
}

string DTO::get_subst()
{
	return subst_activa;
}

int DTO::contor()
{
	return cnt;
}

void DTO::set_denumire(string den) 
{
	denumire = den;
}

void DTO::set_producator(string prod)
{
	producator = prod;
}
