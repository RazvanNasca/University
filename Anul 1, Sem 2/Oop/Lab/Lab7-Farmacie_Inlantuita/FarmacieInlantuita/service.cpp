#include "service.h"
#include"repo.h"
#include<iostream>
#include<map>
#include<vector>
#include<string>

using namespace std;

void Service::adauga(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	const int erori = v.validare_medicament(m);
	if (erori == 0)
		r.store(m);
}

const Lista <Medicament>& Service::getAll() noexcept
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

Lista<Medicament> Service::filtreazaPret(double pret_ref)
{
	Lista<Medicament> rez;
	const auto& meds = getAll();
	for (const auto& med : meds)
		if (med.getPret() <= pret_ref)
			rez.push_back(med);

	return rez;
}

Lista<Medicament> Service::filtreazaSubst(int subst_ref)
{
	Lista<Medicament> rez;
	const auto& meds = getAll();
	for (const auto& med : meds)
		if (med.getSubstanta_activa() <= subst_ref)
			rez.push_back(med);

	return rez;
}

Lista<Medicament> Service::sorteazaDenumire()
{
	Lista<Medicament> rez = getAll();
	for (unsigned int i = 0; i < rez.size() - 1; i++)
		for (unsigned int j = i + 1; j < rez.size(); j++)
			if (rez[i].getDenumire() > rez[j].getDenumire())
			{
				cout << 1;
				Medicament aux = rez[i];
				rez[i] = rez[j];
				rez[j] = aux;
			}

	for(unsigned int i = 0; i < rez.size(); i++)
		cout << rez[i].getDenumire() << " " << rez[i].getProducator() << " " << rez[i].getPret() << " " << rez[i].getSubstanta_activa() << "\n";

	return rez;
}

Lista<Medicament> Service::sorteazaProducator()
{
	Lista<Medicament> rez = getAll();
	for (unsigned int i = 0; i < rez.size() - 1; i++)
		for (unsigned int j = i + 1; j < rez.size(); j++)
			if (rez[i].getProducator() > rez[j].getProducator())
			{
				Medicament aux = rez[i];
				rez[i] = rez[j];
				rez[j] = aux;
			}

	return rez;
}

Lista<Medicament> Service::sorteazaPretSubst()
{
	Lista<Medicament> rez = getAll();
	for (unsigned int i = 0; i < rez.size() - 1; i++)
		for (unsigned int j = i + 1; j < rez.size(); j++)
			if (rez[i].getPret() > rez[j].getPret())
			{
				Medicament aux = rez[i];
				rez[i] = rez[j];
				rez[j] = aux;
			}
			else
				if (rez[i].getPret() == rez[j].getPret())
					if (rez[i].getSubstanta_activa() > rez[j].getSubstanta_activa())
					{
						Medicament aux = rez[i];
						rez[i] = rez[j];
						rez[j] = aux;
					}

	return rez;
}

vector<DTO> Service::raport()
{
	auto meds = r.getAll();
	map<string, DTO> raport;

	for (auto med : meds)
	{
		string aux = std::to_string(med.getSubstanta_activa());
		if (raport.find(aux) == raport.end())
		{
			DTO nou{ aux };
			//raport.insert({ med.getSubstanta_activa, nou });
			raport.insert(pair<string, DTO>(std::to_string(med.getSubstanta_activa()),nou));
		}
		else
		{
			DTO aux1 = raport[aux];
			aux1.incrementeaza();
			raport[aux] = aux1;
		}
	}

	map<string, DTO> ::iterator it = raport.begin();
	vector<DTO> output;

	for (auto& m : meds)
	{
		string aux = std::to_string(m.getSubstanta_activa());
		DTO dto = raport.find(aux)->second;
		dto.set_denumire(m.getDenumire());
		dto.set_producator(m.getProducator());
		output.push_back(dto);
	}

	return output;
}