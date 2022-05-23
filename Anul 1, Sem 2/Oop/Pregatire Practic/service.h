#pragma once
#include <vector>
#include "domain.h"
#include "repo.h"
#include "valid.h"

class Service {

private:
	Repo r;
	Valid v;

public:

	Service(Repo& r, Valid& v) : r{ r }, v{ v }{};

	void store(string numeSerial, string gen, int anAP, int nrEp, int nrEpV);

	void modificare(string numeSerial, int nrEpV);

	vector <SerialTV> getAll();

};
