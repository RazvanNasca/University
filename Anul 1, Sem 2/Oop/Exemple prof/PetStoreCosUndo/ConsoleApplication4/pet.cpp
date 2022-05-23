#include "pet.h"
#include <string.h>
#include <assert.h>

Pet fnct(Pet p) {
	p.setPrice(10);
	return p;
}
void testPet() {
	Pet p{ "abc","dce",8 };
	assert(p.getPrice() == 8);

	assert(p.getType()=="abc");

	Pet p2 = fnct(p);
	
	//compar efectiv stringurile (caracter cu caracter)
	assert(p.getType()==p2.getType());

	p = p2;

	//compar efectiv stringurile (caracter cu caracter)
	assert(p.getType()==p2.getType());

	Pet pp1{ "a","a",10 };
	Pet pp2{ "b","b",11 };

	assert(cmpByPrice(pp1, pp2) < 0);
	assert(cmpByPrice(pp2, pp1) > 0);
	assert(cmpByPrice(pp1, pp1) == 0);

	assert(cmpByType(pp1, pp2) < 0);
	assert(cmpByType(pp2, pp1) > 0);
	assert(cmpByType(pp1, pp1) == 0);

}


int cmpByPrice(const Pet& p1, const Pet& p2) {
	if (p1.getPrice() == p2.getPrice()) {
		return 0;
	}
	if (p1.getPrice() < p2.getPrice()) {
		return -1;
	}
	return 1;
}

int cmpByType(const Pet& p1, const Pet& p2) {
	if (p1.getType() == p2.getType()) {
		return 0;
	}
	if (p1.getType() < p2.getType()) {
		return -1;
	}
	return 1;
}