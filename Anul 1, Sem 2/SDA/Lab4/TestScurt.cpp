#include "TestScurt.h"
#include "MD.h"
#include "IteratorMD.h"
#include <assert.h>
#include <vector>
#include<iostream>

void testAll() {
	MD m;
	m.adauga(1, 100);
	m.adauga(2, 200);
	m.adauga(3, 300);
	m.adauga(1, 500);
	m.adauga(2, 600);
	m.adauga(4, 800);

	assert(m.dim() == 6);

	assert(m.sterge(5, 600) == false);
	assert(m.sterge(1, 500) == true);

	assert(m.dim() == 5);

	vector<TValoare> v;
	v = m.cauta(6);
	assert(v.size() == 0);

	v = m.cauta(1);
	assert(v.size() == 1);

	assert(m.vid() == false);

	IteratorMD im = m.iterator();
	assert(im.valid() == true);
	while (im.valid()) {
		im.element();
		im.urmator();
	}
	assert(im.valid() == false);
	im.prim();
	assert(im.valid() == true);

	/// test functionalitate

	vector<TValoare> rez;
	rez = m.stergeValoriPentruCheie(7);
	assert(rez.size() == 0);

	MD m1;
	m1.adauga(2, 200);
	m1.adauga(2, 300);
	m1.adauga(2, 100);
	m1.adauga(1, 200);
	m1.adauga(1, 200);
	m1.adauga(3, 200);
	rez = m1.stergeValoriPentruCheie(2);
	assert(rez.size() == 3);
	assert(m1.cauta(2).size() == 0);
}