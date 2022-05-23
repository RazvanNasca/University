#pragma once
#include <string>
#include "Pet.h"
#include <vector>
using namespace std;

class ValidateException {
	vector<string> msgs;
public:
	ValidateException(const vector<string>& errors) :msgs{ errors } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const ValidateException& ex);
};

ostream& operator<<(ostream& out, const ValidateException& ex);

class PetValidator {
public:
	void validate(const Pet& p);
};

void testValidator();