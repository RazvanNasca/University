#pragma once
#include "concert.h"
#include <vector>

class RepoException {

private:

	string msg;

public:

	RepoException(string msg) : msg{ msg } {};
	string getMesaj()
	{
		return msg;
	}

};

class Repo {

private:

	vector<Concert> Concerte;
	string filename;

public:

	Repo(string filename) : filename{ filename } { loadFromFile(); };

	void adauga(Concert c);

	void loadFromFile();

	vector <Concert> getAll();

};