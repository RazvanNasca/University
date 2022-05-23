#include "valid.h"

void Valid::validare(Task t)
{
	if (t.getDescriere() == "")
		throw ValidException("Descriere invalida!");

	string stare = t.getStare();
	if(stare != "open" && stare != "inprogress" && stare != "closed")
		throw ValidException("Stare invalida!");

	int n = t.getProgramatori().size();
	if(n < 1 || n > 4)
		throw ValidException("Prea multi programatori!");

}
