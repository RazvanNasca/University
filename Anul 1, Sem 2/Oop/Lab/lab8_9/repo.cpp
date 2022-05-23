#include<iostream>
#include<fstream>
#include<vector>
#include<algorithm>
#include<numeric>
#include "repo.h"

using namespace std;

RepoProb::RepoProb(double e)
{
	erori = e;
}

string RepoException::getMessage()
{
	return msg;
}

void RepoProb::store(const Medicament& m)
{
	double prob = (rand() % 100) * 1.0 / 100;
	if (prob < erori)
		throw RepoException("Probabilitatea e prea mica\n");

	Repo::store(m);
}

void Repo::store(const Medicament& m)
{
	auto it = medicamente.find(m.getDenumire());
	if (it != medicamente.end())
		throw RepoException("Medicament deja adaugat!\n");

	 medicamente.insert(pair<string, Medicament>(m.getDenumire(), m));
}

vector <Medicament> Repo::getAll() noexcept
{
	vector<Medicament> vect;
	vect.reserve(medicamente.size());
	std::transform(medicamente.begin(), medicamente.end(), back_inserter(vect), [](std::pair<std::string, Medicament> const& pair)
		{
			return pair.second;
		});
	return vect;
}

int Repo::cauta(const Medicament& m)
{
	if (medicamente.find(m.getDenumire()) != medicamente.end())
		return 0;

	throw RepoException("Medicamentul nu este adaugat!\n");
}

int RepoProb::actualizare(const Medicament& m)
{
	double prob = (rand() % 100) * 1.0 / 100;
	if (prob < erori)
		throw RepoException("Probabilitatea e prea mica\n");

	return Repo::actualizare(m);
}

int Repo::actualizare(const Medicament& m)
{
	auto it = medicamente.find(m.getDenumire());
	it->second.setPret(m.getPret());
	it->second.setSubstanta_activa(m.getSubstanta_activa());
	
	return 0;
}

int RepoProb::stergere(const Medicament& m)
{
	double prob = (rand() % 100) * 1.0 / 100;
	if (prob < erori)
		throw RepoException("Probabilitatea e prea mica\n");

	return Repo::stergere(m);
}

int Repo::stergere(const Medicament& m)
{
	medicamente.erase(m.getDenumire());
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

double Repo::total()
{
	double suma = 0;
	auto vect = getAll();
	vector<double> rez;

	for (auto it : vect)
		rez.push_back(it.getPret());

	return accumulate(rez.begin(), rez.end(), suma);
}

void Repo::storeReteta(Medicament m)
{
	reteta.push_back(m);
}

void Repo::stergereReteta(Medicament m)
{
	int poz = 0, gasit = 0;
	for (auto it : reteta)
	{
		if (m.getDenumire() == it.getDenumire())
		{
			gasit = 1;
			break;
		}
		poz++;
	}

	if(gasit == 1)
		reteta.erase(reteta.begin() + poz);
}

void Repo::clearAll()
{
	reteta.clear();
}

vector<Medicament> Repo::getAllReteta()
{
	return reteta;
}

Medicament Repo::findMed(string denumire) const
{
	auto it = medicamente.find(denumire);
	if (it == medicamente.end())
		throw RepoException("Nu exista medicamentul cautat!");
	else
		return it->second;
}

void RepoFile::loadFromFile() {
	std::ifstream in(fName);
	if (!in.is_open()) { //verify if the stream is opened		
		throw RepoException("Unable to open file:" + fName);
	}
	while (!in.eof()) {
		std::string denumire;
		in >> denumire;
		std::string producator;
		in >> producator;

		double price;
		in >> price;
		int subst_activa;
		in >> subst_activa;

		if (in.eof()) {	//nu am reusit sa citesc numarul
			break;
		}
		Medicament m{ denumire.c_str(), producator.c_str(), price, subst_activa };
		Repo::store(m);
	}
	in.close();
}

void RepoFile::writeToFile() {
	std::ofstream out(fName);
	if (!out.is_open()) { //verify if the stream is opened
		throw RepoException("Unable to open file:");
	}
	for (auto& med : getAll()) {
		out << med.getDenumire();
		out << std::endl;
		out << med.getProducator();
		out << std::endl;
		out << med.getPret();
		out << std::endl;
		out << med.getSubstanta_activa();
		out << std::endl;
	}
	out.close();
}