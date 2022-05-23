#pragma once
#include <string>
#include "domain.h"

using namespace std;

class ValidException {
	string msg;

public:
	ValidException(string m) : msg{ m } {};

	string getMesaj()
	{
		return msg;
	}

};

class Valid {
public:

	void valideaza(SerialTV ser);

};
