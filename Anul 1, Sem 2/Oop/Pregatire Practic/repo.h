#pragma once
#include <vector>
#include "domain.h"

class RepoException {

	string msg;

public:
	RepoException(string m) : msg{ m } {};
	string getMesaj()
	{
		return msg;
	}
};

class Repo {

private:
	vector <SerialTV> seriale;
	string filename;
	void loadFromFile();
	void storeToFile();

public:

	Repo(string filename) : filename{ filename } {
		loadFromFile();
	}

	void store(const SerialTV& ser);

	void modifica(SerialTV& ser);

	vector <SerialTV> getAll();

	int cauta(SerialTV ser);

};
