#pragma once
#include "valid.h"
#include "repo.h"

class Service {

private:

	Repo& r;
	Valid& v;

public:

	Service(Repo& r, Valid& v) : r{ r }, v{ v }{}

	/// valideaza camurile, creeaza obiecte si le adauga in lista, arunca exceptii pt date invalide
	void adauga(int id, string descriere, vector <string> programatori, string stare);

	/// returneaza lista de task-uri
	vector <Task> getAll();

	/// sorteaza lista dupa descriere
	vector <Task> sorteazaDescriere();

	/// returneaza toate task-urile programatorilor
	vector <Task> getTasksProg(string nume);

	/// sterge un task din lista
	void sterge(Task t);

	/// returneaza programatorii
	vector<string> getProg();

};
