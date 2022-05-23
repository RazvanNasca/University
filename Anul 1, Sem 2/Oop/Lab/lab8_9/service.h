#pragma once
#include <string>
#include "medicament.h"
#include "repo.h"
#include "validator.h"
#include "undeo.h"
using std::string;

class Service
{
private:
	RepoVirtual& r;
	Valid& v;
	vector<unique_ptr<ActiuneUndo>> undoActions;

public:
	Service(RepoVirtual& r, Valid& v) noexcept : r{ r }, v{ v } {}

	Service(const Service& ot) = delete;  /// nu permite sa se faca copii
	Service() = default;

	/*
		creeaza un obiect din datele introduse si solicita validare datelor
		intput: denumire - denumirea produsului care umeaza sa fie adaugat
				producator - producatorul produsului care umeaza sa fie adaugat
				pret - pretul produsului care umeaza sa fie adaugat
				substanta_activa - cantitatea de substanta activa a produsului care umeaza sa fie adaugat
	*/
	void adauga(string denumire, string producator, double pret, int substanta_activa);

	/*
		apeleaza functia din repo care retuneaza toate elementele
	*/
	vector <Medicament> getAll() noexcept;

	/*
		creeaza obiectul cu datele introduse si apeleaza functia de cautare din repo
		intput: denumire - denumirea produsului care umeaza sa fie adaugat
				producator - producatorul produsului care umeaza sa fie adaugat
				pret - pretul produsului care umeaza sa fie adaugat
				substanta_activa - cantitatea de substanta activa a produsului care umeaza sa fie adaugat
	*/
	void cauta(string denumire, string producator, double pret, int substanta_activa);

	/*
		creeaza obiectul cu datele introduse si apeleaza functia de actualizare din repo
		intput: denumire - denumirea produsului care umeaza sa fie adaugat
				producator - producatorul produsului care umeaza sa fie adaugat
				pret - pretul produsului care umeaza sa fie adaugat
				substanta_activa - cantitatea de substanta activa a produsului care umeaza sa fie adaugat
	*/
	void actualizare(string denumire, string producator, double pret, int substanta_activa);

	/*
		creeaza obiectul cu datele introduse si apeleaza functia de stergere din repo
		intput: denumire - denumirea produsului care umeaza sa fie adaugat
				producator - producatorul produsului care umeaza sa fie adaugat
				pret - pretul produsului care umeaza sa fie adaugat
				substanta_activa - cantitatea de substanta activa a produsului care umeaza sa fie adaugat
	*/
	void stergere(string denumire, string producator, double pret, int substanta_activa);

	vector<Medicament> filtreazaPret(double pret_ref);

	vector<Medicament> filtreazaSubst(int subst_ref);

	vector<Medicament> sorteazaDenumire();

	vector<Medicament> sorteazaProducator();

	vector<Medicament> sorteazaPretSubst();

	vector<DTO> raport();

	double total();

	void adaugaReteta(string denumire, string producator, double pret, int substanta_activa);

	void clearAll();

	void adaugaRandom(int nrMeds);

	string randomString();

	vector<Medicament> getAllReteta();

	void exportCSV(const string& filename) const;

	void exportHTML(const string& filename) const;

	void undo();
};