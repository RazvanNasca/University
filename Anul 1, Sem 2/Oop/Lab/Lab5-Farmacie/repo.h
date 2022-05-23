#pragma once
#include <vector>
#include "medicament.h"
using std::vector;

class RepoException
{
	string msg;
public:
	RepoException(string m) :msg{ m }{}
	string getMessage();
};

class Repo
{
private:
	vector<Medicament> medicamente;

public:
	Repo(const Repo& ot) = delete; /// constructor de copiere
	Repo() = default;

	/*
		adauga in lista un medicament
		input: m - medicamnetul care urmeaza sa fie actualizat
		output: ridica exceptii daca medicamnetul este deja adaugat
	*/
	void store(const Medicament& m);

	/*
		returneaza lista de medicamente
	*/
	const vector <Medicament>& getAll() noexcept;

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
	int actualizare(const Medicament& m, int poz) ;

	/*
		sterge un medicament in lista
		intput: m - medicamentul cautat, poz - pozitia elementului care urmeazas a fie sters
		output: 0 daca stergerea a fost facuta cu succes
	*/
	int stergere(const Medicament& m, int poz) ;
};
