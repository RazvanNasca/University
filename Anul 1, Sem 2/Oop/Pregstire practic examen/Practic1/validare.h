#pragma once
#include "entitati.h"

class ValidException {

private:
	string msg;

public:
	ValidException(string m) : msg{ m } {};
	string getMessage();

};

class Valid {

public:

	int valid(Task t);

};