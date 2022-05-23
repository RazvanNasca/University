#pragma once
#include <vector>
#include <map>
#include "medicament.h"
#include "Observer.h"

using namespace std;


class RepoVirtual
{

public:

	/*
		adauga in lista un medicament
		input: m - medicamnetul care urmeaza sa fie actualizat
		output: ridica exceptii daca medicamnetul este deja adaugat
	*/
	virtual void store(const Medicament& m) = 0;

	/*
		returneaza lista de medicamente
	*/
	virtual vector <Medicament> getAll() noexcept = 0;

	/*
		cauta un medicament in lista
		intput: m - medicamentul cautat
		output: pozitia medicamentului daca se afla in sir, ridica o exceptie in caz contrar
	*/
	virtual int cauta(const Medicament& m) = 0;

	/*
		actualizeaza un medicament in lista
		intput: m - medicamentul cautat, poz - pozitia elementului care urmeazas a fie actualizat
		output: 0 daca actualizarea a fost facuta cu succes
	*/
	virtual int actualizare(const Medicament& m) = 0;

	/*
		sterge un medicament in lista
		intput: m - medicamentul cautat, poz - pozitia elementului care urmeazas a fie sters
		output: 0 daca stergerea a fost facuta cu succes
	*/
	virtual int stergere(const Medicament& m) = 0;

	virtual double total() = 0;

	//virtual void storeReteta(Medicament m) = 0;

	//virtual void clearAll() = 0;

	///virtual vector<Medicament> getAllReteta() = 0;

	virtual Medicament findMed(string denumire) const = 0;

	//virtual void stergereReteta(Medicament m) = 0;
};


class DTO {

private:
	string denumire, producator;
	string subst_activa;
	int cnt = 1;

public:
	DTO() = default;
	DTO(string key) : denumire{ "" }, producator{ "" }, subst_activa{ key }, cnt{ 1 }{};

	void incrementeaza();
	void set_denumire(string den);
	void set_producator(string prod);
	string get_denumire();
	string get_producator();
	string get_subst();
	int contor();
};

class RepoException
{
	string msg;
public:
	RepoException(string m) :msg{ m } {}
	string getMessage();
};

class Ret: public Observable {

private:
	vector<Medicament> reteta;
	RepoVirtual& r;

public:

	Ret(RepoVirtual& r) : r{ r } {};

	void storeReteta(Medicament m);

	void clearAll();

	vector<Medicament> getAllReteta();

	void stergereReteta(Medicament m);

};

class Repo : public RepoVirtual
{
private:
	map<string, Medicament> medicamente;

public:
	Repo(const Repo& ot) = delete; /// constructor de copiere
	///Repo(double erori);
	Repo() = default;

	/*
		adauga in lista un medicament
		input: m - medicamnetul care urmeaza sa fie actualizat
		output: ridica exceptii daca medicamnetul este deja adaugat
	*/
	virtual void store(const Medicament& m);

	/*
		returneaza lista de medicamente
	*/
	vector <Medicament> getAll() noexcept;

	/*
		cauta un medicament in lista
		intput: m - medicamentul cautat
		output: pozitia medicamentului daca se afla in sir, ridica o exceptie in caz contrar
	*/
	int cauta(const Medicament& m);

	/*
		actualizeaza un medicament in lista
		intput: m - medicamentul cautat, poz - pozitia elementului care urmeazas a fie actualizat
		output: 0 daca actualizarea a fost facuta cu succes
	*/
	int actualizare(const Medicament& m);

	/*
		sterge un medicament in lista
		intput: m - medicamentul cautat, poz - pozitia elementului care urmeazas a fie sters
		output: 0 daca stergerea a fost facuta cu succes
	*/
	virtual int stergere(const Medicament& m);

	double total();

	Medicament findMed(string denumire) const;

};


class RepoProb : public Repo
{
private:

	double erori = 0;

public:
	RepoProb(const RepoProb& ot) = delete; /// constructor de copiere
	RepoProb(double erori);
	RepoProb() = default;

	/*
		adauga in lista un medicament
		input: m - medicamnetul care urmeaza sa fie actualizat
		output: ridica exceptii daca medicamnetul este deja adaugat
	*/
	void store(const Medicament& m) override;

	/*
		actualizeaza un medicament in lista
		intput: m - medicamentul cautat, poz - pozitia elementului care urmeazas a fie actualizat
		output: 0 daca actualizarea a fost facuta cu succes
	*/
	int actualizare(const Medicament& m) override;

	/*
		sterge un medicament in lista
		intput: m - medicamentul cautat, poz - pozitia elementului care urmeazas a fie sters
		output: 0 daca stergerea a fost facuta cu succes
	*/
	int stergere(const Medicament& m) override;
};

class RepoFile :public Repo {
private:
	std::string fName;
	void loadFromFile();
	void writeToFile();
public:
	RepoFile(std::string fName) : fName{ fName } {
		loadFromFile();//incarcam datele din fisier
	}
	void store(const Medicament& m) override {
		Repo::store(m);//apelam metoda din clasa de baza
		writeToFile();
	}
	int stergere(const Medicament& m) override {
		int i = Repo::stergere(m);//apelam metoda din clasa de baza
		writeToFile();
		return i;
	}
};
