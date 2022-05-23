#pragma once
#include "task.h"

class ValidException {

private:

	string msg;

public:

	ValidException(string msg) : msg{ msg }{}

	string getMessage() { return msg; }

};

class Valid {

public:

	Valid() = default;

	/// valideaza campurile unui task, arunca exceptie daca datele sunt invalide
	void validare(Task t);

};
