#include "validare.h"

string ValidException::getMessage()
{
	return msg;
}

int Valid::valid(Task t)
{
	if (t.getDescriere() == "")
		throw ValidException("Descriere vida!");

	const string& stare = t.getStare();
	if (stare != "open" && stare != "inprogress" && stare != "closed")
		throw ValidException("Stare invalida!");

	if (t.getProgramatori().size() < 1 || t.getProgramatori().size() > 4)
		throw ValidException("Prea multi programatori!");

	return 0;
}
