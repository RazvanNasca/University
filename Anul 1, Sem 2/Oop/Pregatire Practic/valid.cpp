#include "valid.h"

void Valid::valideaza(SerialTV ser)
{
	if (ser.getGen() != "comedie" && ser.getGen() != "drama" && ser.getGen() != "thriller" && ser.getGen() != "documentar")
		throw ValidException("Gen invalid!");

	if (ser.getNrEp() < ser.getNrEpV())
		throw ValidException("Numar de episoade vizionate prea mare!");
}
