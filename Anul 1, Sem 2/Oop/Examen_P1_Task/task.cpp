#include "task.h"

int Task::getId()
{
	return id;
}

string Task::getDescriere()
{
	return descriere;
}

string Task::getStare()
{
	return stare;
}

vector<string> Task::getProgramatori()
{
	return programatori;
}

void Task::setStare(string s)
{
	stare = s;
}

void Task::setProgramatori(string p)
{
	programatori.push_back(p);
}
