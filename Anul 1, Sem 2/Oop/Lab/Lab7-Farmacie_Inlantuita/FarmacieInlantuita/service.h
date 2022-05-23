#pragma once
#include <string>
#include <vector>
#include "medicament.h"
#include"repo.h"
#include "validator.h"

using namespace std;

class Service
{
private:
	Repo& r;
	Valid& v;
public:
	Service(Repo& r, Valid& v) noexcept : r{ r }, v{ v } {}

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
	const Lista <Medicament>& getAll() noexcept;

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

	Lista<Medicament> filtreazaPret(double pret_ref);

	Lista<Medicament> filtreazaSubst(int subst_ref);

	Lista<Medicament> sorteazaDenumire();

	Lista<Medicament> sorteazaProducator();

	Lista<Medicament> sorteazaPretSubst();

	vector<DTO> raport();
};
