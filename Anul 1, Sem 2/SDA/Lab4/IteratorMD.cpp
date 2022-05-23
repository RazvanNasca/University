#include "IteratorMD.h"
#include "MD.h"

using namespace std;

IteratorMD::IteratorMD(const MD& _md) : md(_md) {
	/// Complexitate: teta(1)

	curent = md.prim;
	if (curent == -1)
		curentLista = -1;
	else
		curentLista = md.e[curent].second->primLista;
}

TElem IteratorMD::element() const {
	
	if (valid() == false)
		throw false;

	return pair <TCheie, TValoare>(md.e[curent].first, md.e[curent].second->val[curentLista]);
}

bool IteratorMD::valid() const {
	/// Complexitate: teta(1)

	if (curentLista == -1)
		return false;

	if(curent== -1)
		return false;

	return true;
}

void IteratorMD::urmator() {
	/// Complexiatate: teta(1)

	curentLista = md.e[curent].second->urmLista[curentLista];

	if (valid() == false)
	{
		curent = md.urm[curent];
		if (curent != -1)
		{
			curentLista = md.e[curent].second->primLista;
		}

	}

}

void IteratorMD::prim() {
	/// Complexitate: teta(1)

	curent = md.prim;
	if (curent == -1)
		curentLista = -1;
	else
		curentLista = md.e[curent].second->primLista;
}
