#include "stdafx.h"

#include "vectordinamict.h"
#include<assert.h>
#include "pet.h"
void auxFunct(VectorDinamicT<Pet> v) {
	assert(v.size() == 12);
	v.add(Pet{ "a","b",1 });
	assert(v.size() == 13);
}

void testVectorT() {
	VectorDinamicT<Pet> v;
	assert(v.size() == 0);
	v.add(Pet{ "abc","def",7 });
	assert(v.size() == 1);
	Pet p{ "abc2","def2",72 };
	v.add(p);
	assert(v.size() == 2);
	for (int i = 0; i < 10; i++) {
		v.add(p);
		assert(v.size() == i + 3);
	}
	//testam si constructorul de copiere
	auxFunct(v);
	assert(v.size() == 12);
	//testam iteratorul
	auto it = v.begin();
	while (it != v.end())
	{
		auto p = *it;
		assert(p.getPrice() > 0);
		++it;
	}
	for (const auto& p : v) {
		//std::cout << p.getType() << std::endl;
		assert(p.getPrice() > 0);
	}
	
}