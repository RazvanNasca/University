#pragma once
#include "task.h"
#include <fstream>

using namespace std;

class RepoException {

private:

	string msg;

public:

	RepoException(string msg): msg{msg}{}

	string getMessage() { return msg; };

};

class Repo {

private:

	/// lista de task-uri
	vector<Task> tasks;
	
	/// numele fisierului
	string filename;

	/// incarca datele in fisier
	void storeToFile();

	/// incarca datele din fisier
	void loadFromFile();

	///functia care verifica daca un task exista in lista
	bool exista(Task t);

public:

	Repo(string filename) : filename{ filename } { loadFromFile(); }

	/// adauga task-uri noi in lista, daca ele exista deja arunca exceptii
	void adauga(Task t);

	/// returneaza lista task-urilor
	vector<Task> getAll();

	/// returneaza un task dupa id-ul lui, arunca exceptie daca nu exista
	Task cauta(int id);

	/// returneaza toate task-urile unui programator
	vector<Task> getTasksProg(string nume);

	/// returneaza toate task-urile sortate dupa descriere
	vector<Task> sorteazaDescriere();

	/// sterge un task dupa pozitie
	void sterge(int p);

	/// returneaza pozitia la care se afla task-ul
	int getPoz(Task t);

	/// returneaza programatorii
	vector<string> getProg();

};
