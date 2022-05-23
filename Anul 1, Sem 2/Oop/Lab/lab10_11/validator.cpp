#include "validator.h"
#include <string>
using std::string;

string ValidException::getMessage()
{
	return msg;
}

int Valid::validare_medicament(const Medicament& m) const
{
	string denumire = m.getDenumire();
	string producator = m.getProducator();
	const double pret = m.getPret();
	const int subst = m.getSubstanta_activa();

	constexpr int error = 0;

	if (denumire.size() == 0 || denumire == " ")
		throw ValidException("Denumire invalida!\n");

	for (unsigned int i = 0; i < denumire.size(); i++)
		if (denumire.at(i) == '0' || denumire.at(i) == '1' || denumire.at(i) == '2' || denumire.at(i) == '3' || denumire.at(i) == '4' || denumire.at(i) == '5' || denumire.at(i) == '6' || denumire.at(i) == '7' || denumire.at(i) == '8' || denumire.at(i) == '9')
			throw ValidException("Denumire invalida!\n");

	if (producator.size() == 0 || producator == " ")
		throw ValidException("Producator invalid!\n");

	for (unsigned int i = 0; i < producator.size(); i++)
		if (producator.at(i) == '0' || producator.at(i) == '1' || producator.at(i) == '2' || producator.at(i) == '3' || producator.at(i) == '4' || producator.at(i) == '5' || producator.at(i) == '6' || producator.at(i) == '7' || producator.at(i) == '8' || producator.at(i) == '9')
			throw ValidException("Producator invalid!\n");

	if (pret <= 0)
		throw ValidException("Pret invalid!\n");

	if (subst <= 0)
		throw ValidException("Cantitate de substanta activa invalida!\n");

	return error;
}