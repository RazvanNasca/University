#include "service.h"
#include "repo.h"
#include "export.h"
#include<iostream>
#include<map>
#include<algorithm>
#include<random>

using namespace std;

void Service::adauga(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	const int erori = v.validare_medicament(m);
	//const int erori = 0;
	if (erori == 0)
	{
		r.store(m);
		undoActions.push_back(make_unique<UndoAdauga>(r, m));
	}

}

vector <Medicament> Service::getAll() noexcept
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
		erori = r.cauta(m);
		if (erori == 0)
		{
			Medicament med = r.findMed(denumire);
			erori = r.actualizare(m);
			undoActions.push_back(make_unique<UndoModifica>(r, med, m));
		}
	}
}

void Service::stergere(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	int erori = v.validare_medicament(m);
	if (erori == 0)
	{
		erori = r.cauta(m);
		if (erori == 0)
		{
			erori = r.stergere(m);
			undoActions.push_back(make_unique<UndoSterge>(r, m));
		}
	}

}

vector<Medicament> Service::filtreazaPret(double pret_ref)
{
	vector<Medicament> rez;
	vector<Medicament> meds = getAll();

	std::copy_if(meds.begin(), meds.end(), back_inserter(rez), [=](const Medicament& m) {return m.getPret() < pret_ref; });

	/*for (const auto& med : meds)
		if (med.getPret() <= pret_ref)
			rez.push_back(med);*/

	return rez;
}

vector<Medicament> Service::filtreazaSubst(int subst_ref)
{
	vector<Medicament> rez;
	auto meds = getAll();
	std::copy_if(meds.begin(), meds.end(), back_inserter(rez), [=](const Medicament& m) {return m.getSubstanta_activa() < subst_ref; });

	return rez;
}

vector<Medicament> Service::sorteazaDenumire()
{
	vector<Medicament> rez;
	auto meds = getAll();
	for (auto med : meds)
		rez.push_back(med);

	sort(rez.begin(), rez.end(), cmpDenumire);

	return rez;
}

vector<Medicament> Service::sorteazaProducator()
{
	vector<Medicament> rez;
	auto meds = getAll();
	for (auto med : meds)
		rez.push_back(med);

	sort(rez.begin(), rez.end(), cmpProducator);

	return rez;
}

vector<Medicament> Service::sorteazaPretSubst()
{
	vector<Medicament> rez;
	auto meds = getAll();
	for (auto med : meds)
		rez.push_back(med);

	sort(rez.begin(), rez.end(), cmpSubstantaSiPret);

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
			raport.insert(pair<string, DTO>(std::to_string(med.getSubstanta_activa()), nou));
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

double Service::total()
{
	return r.total();
}

void Service::adaugaReteta(string denumire, string producator, double pret, int substanta_activa)
{
	Medicament m{ denumire, producator, pret, substanta_activa };
	const int erori = v.validare_medicament(m);
	if (erori == 0)
	{
		rete.storeReteta(m);
		undoActions.push_back(make_unique<UndoAdaugaReteta>(rete, m));
	}

}

void Service::clearAll()
{
	rete.clearAll();
}

void Service::adaugaRandom(int nrMeds)
{
	for (int i = 1; i <= nrMeds; i++)
	{
		string denumire = randomString();
		string producator = randomString();
		int substantaActiva = rand();
		double pret = (double)rand();
		Medicament med{ denumire,producator,pret,substantaActiva };
		rete.storeReteta(med);
	}
}

string Service::randomString()
{
	std::string str("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

	std::random_device rand;
	std::mt19937 generator(rand());

	std::shuffle(str.begin(), str.end(), generator);

	return str.substr(0, 10);
}

vector<Medicament> Service::getAllReteta()
{
	return rete.getAllReteta();
}

void Service::exportCSV(const string& filename) const
{
	exportToCSV(filename, rete.getAllReteta());
}

void Service::exportHTML(const string& filename) const
{
	exportToHTML(filename, rete.getAllReteta());
}

void Service::undo()
{
	if (undoActions.size() == 0)
	{
		throw RepoException{ "Nu mai exista operatii pentru undo!" };
	}

	undoActions.back()->doUndo();
	undoActions.pop_back();
}