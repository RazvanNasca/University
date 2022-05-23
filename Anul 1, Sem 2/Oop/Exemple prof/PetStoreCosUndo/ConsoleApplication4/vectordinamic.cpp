#include "stdafx.h"

#include "vectordinamic.h"

#include "pet.h"
#include <assert.h>
#include <algorithm>

IteratorVector VectorDinamic::begin()
{
	return IteratorVector(*this);	
}

IteratorVector VectorDinamic::end()
{
	return IteratorVector(*this, lg);
}

void VectorDinamic::ensureCapacity() {
	if (lg < cap) {
		return; //mai avem loc
	}
	cap *= 2;
	Element* aux = new Element[cap];
	for (int i = 0; i < lg; i++) {
		aux[i] = elems[i];
	}
	delete[] elems;
	elems = aux;
}

void auxFunct(VectorDinamic v) {
	assert(v.size() == 12);
	v.add(Pet{"a","b",1});
	assert(v.size() == 13);
}

void testVector() {
	VectorDinamic v;
	assert(v.size() == 0);
	v.add(Pet{ "abc","def",7 });
	assert(v.size() == 1);
	Pet p{ "abc2","def2",72 };
	v.add(p);
	assert(v.size() == 2);
	for (int i = 0; i < 10; i++) {
		v.add(p);
		assert(v.size() == i+3);
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