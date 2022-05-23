#include "Pet.h"


bool cmpType(const Pet& p1, const Pet& p2) {
	return p1.getType() < p2.getType();
}

