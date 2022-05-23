#include "entitati.h"

int Task::getID() const
{
	return id;
}

string Task::getDescriere() const
{
	return descriere;
}

string Task::getStare() const
{
	return stare;
}

void Task::setStare(string st)
{
	stare = st;
}

vector<string> Task::getProgramatori()
{
	return	programatori;
}
