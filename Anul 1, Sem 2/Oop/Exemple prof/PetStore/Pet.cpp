#include "Pet.h"


bool cmpType(const Pet& p1, const Pet& p2) {
	return p1.getType() < p2.getType();
}

bool cmpSpecies(const Pet& p1, const Pet& p2) {
	return p1.getSpecies() < p2.getSpecies();
}

