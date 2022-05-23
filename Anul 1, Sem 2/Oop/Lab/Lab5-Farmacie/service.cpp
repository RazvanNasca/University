#include "service.h"
#include "repo.h"
#include<iostream>
using std::cout;

void Service::adauga(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	const int erori = v.validare_medicament(m);
	if(erori == 0)
		r.store(m);
}

const vector <Medicament>& Service::getAll() noexcept
{
	return r.getAll();
}

void Service::cauta(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	int erori = v.validare_medicament(m);
	if (erori == 0)
		erori = r.cauta(m);
}

void Service::actualizare(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	int erori = v.validare_medicament(m);
	if (erori == 0)
	{
		const int poz = r.cauta(m);
		erori = r.actualizare(m, poz);
	}
}

void Service::stergere(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	int erori = v.validare_medicament(m);
	if (erori == 0)
	{
		const int poz = r.cauta(m);
		erori = r.stergere(m, poz);
	}
}

vector<Medicament> Service::filtreazaPret(double pret_ref)
{
	vector<Medicament> rez;
	const auto& meds = getAll();
	for (const auto& med : meds)
		if (med.getPret() <= pret_ref)
			rez.push_back(med);

	return rez;
}

vector<Medicament> Service::filtreazaSubst(int subst_ref)
{
	vector<Medicament> rez;
	const auto& meds = getAll();
	for (const auto& med : meds)
		if (med.getSubstanta_activa() <= subst_ref)
			rez.push_back(med);

	return rez;
}

vector<Medicament> Service::sorteazaDenumire()
{
	vector<Medicament> rez = getAll();
	for(unsigned int i = 0; i < rez.size() - 1; i++)
		for(unsigned int j = i + 1; j < rez.size(); j++)
			if (rez.at(i).getDenumire() > rez.at(j).getDenumire())
			{
				Medicament aux = rez.at(i);
				rez.at(i) = rez.at(j);
				rez.at(j) = aux;
			}

	return rez;
}

vector<Medicament> Service::sorteazaProducator()
{
	vector<Medicament> rez = getAll();
	for (unsigned int i = 0; i < rez.size() - 1; i++)
		for (unsigned int j = i + 1; j < rez.size(); j++)
			if (rez.at(i).getProducator() > rez.at(j).getProducator())
			{
				Medicament aux = rez.at(i);
				rez.at(i) = rez.at(j);
				rez.at(j) = aux;
			}

	return rez;
}

vector<Medicament> Service::sorteazaPretSubst()
{
	vector<Medicament> rez = getAll();
	for (unsigned int i = 0; i < rez.size() - 1; i++)
		for (unsigned int j = i + 1; j < rez.size(); j++)
			if (rez.at(i).getPret() > rez.at(j).getPret())
			{
				Medicament aux = rez.at(i);
				rez.at(i) = rez.at(j);
				rez.at(j) = aux;
			}
			else 
				if(rez.at(i).getPret() == rez.at(j).getPret())
					if(rez.at(i).getSubstanta_activa() > rez.at(j).getSubstanta_activa())
					{
						Medicament aux = rez.at(i);
						rez.at(i) = rez.at(j);
						rez.at(j) = aux;
					}

	return rez;
}