#pragma once
#include "entitati.h"

class RepoException {

private:
	string msg;

public:
	RepoException(string m) : msg{ m } {};
	string getMessage();

};


class Repo {

private:

	vector <Task> taskuri;
	string filename;

public:

	Repo(string f) : filename{ f } { loadFromFile(); }

	/// adauga un nou task
	void adauga(Task t);

	/// modifica starea task
	void modifica(Task t, string st);

	/// returneaza toate task-urile
	vector <Task> getAll();

	/// cauta in id
	int cautaID(Task t);

	/// afiseaza toate task-urile care au programatorul p
	vector <Task> cautaProg(string p);

	/// incarca datele din fisier
	void loadFromFile();

	/// incarca datele in fisier
	void storeToFile();

};
