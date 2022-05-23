#include "medicament.h"

string Medicament::getDenumire() const
{
	return denumire;
}

string Medicament::getProducator() const
{
	return producator;
}

double Medicament::getPret() const noexcept
{
	return pret;
}

int Medicament::getSubstanta_activa() const noexcept
{
	return substanta_activa;
}

void Medicament::setPret(double val) noexcept
{
	pret = val;
}

void Medicament::setSubstanta_activa(int val) noexcept
{
	substanta_activa = val;
}
