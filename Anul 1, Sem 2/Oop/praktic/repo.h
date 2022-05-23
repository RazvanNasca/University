#pragma once
#include "entitati.h"
#include <vector>
#include <fstream>

class RepoException {

private:

	string msg;

public:

	RepoException(string msg): msg{msg}{}

	string getMessage() {
		return msg;
	}

};

class Repo {

private:

	vector <Melodie> melodii;
	string filename;

public:

	Repo(string filename) : filename{ filename } { loadFromFile(); }

	vector <Melodie> getAll();

	void modifica(Melodie m1, Melodie m2);

	void adauga(Melodie m);

	void loadFromFile();

	void storeToFile();

};
