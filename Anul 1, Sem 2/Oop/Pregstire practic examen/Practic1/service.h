#pragma once
#include "repo.h"
#include "validare.h"

class Service {

private:

	Repo& r;
	Valid& v;

public:

	Service(Repo& r, Valid& v): r{r}, v{v}{}

	/// creeaza un obiect de tipul task, valideaza datele si le adauga in lista
	void adauga(int id, string descriere, string stare, vector<string> programatori);

	/// returneaza toate task-urile
	vector <Task> getAll();

	/// modifica starea un task
	void modifica(int id, string stare);

	/// returneaza o lista cu toate task-urile care sunt facute de programatorul p
	vector <Task> cautaProg(string p);

};
