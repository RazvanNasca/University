#include "service.h"
#include "valid.h"

void Service::store(string numeSerial, string gen, int anAP, int nrEp, int nrEpV)
{
	SerialTV ser{ numeSerial,  gen, anAP, nrEp, nrEpV };
	v.valideaza(ser);
	int eroare = r.cauta(ser);

	if (eroare == 0)
		r.store(ser);
	else
		throw RepoException("Serial deja adaugat!");
}

void Service::modificare(string numeSerial,int nrEpV)
{
	SerialTV ser{ numeSerial,  "drama", 2010, 5, nrEpV };
	v.valideaza(ser);
	int eroare = r.cauta(ser);

	if (eroare == 1)
		r.modifica(ser);
	else
		throw RepoException("Serialul nu este adaugat!");
}

vector<SerialTV> Service::getAll()
{
	return r.getAll();
}
