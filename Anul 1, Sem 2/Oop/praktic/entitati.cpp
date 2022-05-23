#include "entitati.h"

int Melodie::getId()
{
	return id;
}

string Melodie::getTitlu()
{
	return titlu;
}

string Melodie::getArtist()
{
	return artist;
}

int Melodie::getRank()
{
	return rank;
}

void Melodie::setTitlu(string t)
{
	titlu = t;
}

void Melodie::setRank(int r)
{
	rank = r;
}
