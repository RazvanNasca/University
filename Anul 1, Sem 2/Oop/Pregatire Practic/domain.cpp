#include "domain.h"

string SerialTV::getNume()
{
	return numeSerial;
}

string SerialTV::getGen()
{
	return gen;
}

int SerialTV::getAn()
{
	return anAP;
}

int SerialTV::getNrEp()
{
	return nrEp;
}

int SerialTV::getNrEpV()
{
	return nrEpV;
}

void SerialTV::setNrEpV(int e) 
{
	nrEpV = e;
}
