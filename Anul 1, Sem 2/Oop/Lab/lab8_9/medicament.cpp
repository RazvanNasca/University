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

bool cmpDenumire(const Medicament& m1, const Medicament& m2)
{
	return m1.getDenumire() < m2.getDenumire();
}

bool cmpProducator(const Medicament& m1, const Medicament& m2)
{
	return m1.getProducator() < m2.getProducator();
}

bool cmpSubstantaSiPret(const Medicament& m1, const Medicament& m2)
{
	if (m1.getSubstanta_activa() == m2.getSubstanta_activa())
		return m1.getPret() < m2.getPret();
	return m1.getSubstanta_activa() < m2.getSubstanta_activa();
}
