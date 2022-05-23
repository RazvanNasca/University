#include "teste.h"
#include "repo.h"
#include "service.h"
#include "validator.h"
#include <assert.h>
#include <iostream>
using namespace std;


void Test::testAdaugare()
{
	Repo r;
	Valid v;
	Service s{ r , v };
	s.adauga("Paracetamol", "Gica", 15, 2);
	const auto& meds = s.getAll();
	assert(meds.size() == 1);

	try {
		s.adauga("Paracetamol", "Gica", 15, 2);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}

	try {
		s.adauga("Parac2tamol", "Gica", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adauga("Paractamol", "Gi3ca", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adauga("Paractamol", "Gica", -15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adauga("Paractamol", "Gica", 15, -2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adauga("", "Gica", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adauga("Paracetamol", "", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}
}

void Test::testAfisare()
{
	Repo r;
	Valid v;
	Service s{ r , v };
	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Nurofen", "Flaviu", 15, 2);
	const auto& meds = s.getAll();
	assert(meds.size() == 2);
}

void Test::testCautare()
{
	Repo r;
	Valid v;
	Service s{ r , v };
	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Nurofen", "Flaviu", 15, 2);
	const auto& meds = s.getAll();

	s.cauta("Paracetamol", "Gica", 15, 2);

	try {
		s.cauta("Para", "Gica", 15, 2);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
}

void Test::testActualizare()
{
	Repo r;
	Valid v;
	Service s{ r , v };
	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Nurofen", "Flaviu", 15, 2);
	const auto& meds = s.getAll();

	s.actualizare("Paracetamol", "Gica", 10, 1);

	try {
		s.actualizare("Para", "Gica", 15, 2);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
}

void Test::testStergere()
{
	Repo r;
	Valid v;
	Service s{ r , v };
	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Nurofen", "Flaviu", 15, 2);
	const auto& meds = s.getAll();

	s.stergere("Paracetamol", "Gica", 10, 1);

	try {
		s.stergere("Para", "Gica", 15, 2);
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
}

void Test::testFiltrari()
{
	Repo r;
	Valid v;
	Service s{ r , v };

	Lista <Medicament> rez;

	s.adauga("Paracetamol", "Gica", 15.2, 2);
	s.adauga("Nurofen", "Flaviu", 20, 7);

	rez = s.filtreazaPret(17);
	assert(rez.size() == 1);
	rez = s.filtreazaPret(21);
	assert(rez.size() == 2);
	rez = s.filtreazaPret(2);
	assert(rez.size() == 0);

	rez = s.filtreazaSubst(5);
	assert(rez.size() == 1);
	rez = s.filtreazaSubst(8);
	assert(rez.size() == 2);
	rez = s.filtreazaSubst(1);
	assert(rez.size() == 0);

}

void Test::testSortari()
{
	Repo r;
	Valid v;
	Service s{ r , v };

	Lista <Medicament> rez;

	s.adauga("Paracetamol", "Gica", 15.2, 2);
	s.adauga("Nurofen", "Flaviu", 20, 7);

	rez = s.sorteazaDenumire();
	assert(rez[0].getDenumire() == "Nurofen");

	rez = s.sorteazaProducator();
	assert(rez[0].getProducator() == "Flaviu");

	rez = s.sorteazaPretSubst();
	assert(rez[0].getDenumire() == "Paracetamol");

	s.adauga("Para", "Gica", 15.2, 1);
	rez = s.sorteazaPretSubst();
	assert(rez[0].getDenumire() == "Para");

}

void Test::testRaport()
{
	Repo r;
	Valid v;
	Service s{ r , v };

	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Panadol", "Gheorghe", 12, 3);
	s.adauga("Nurofen", "Florin", 10, 2);

	vector<DTO>rez = s.raport();

	assert(rez[0].get_denumire() == "Paracetamol");
	assert(rez[0].get_producator() == "Gica");
	assert(rez[0].contor() == 2);
	assert(rez[0].get_subst() == "2");
	assert(rez[1].get_denumire() == "Panadol");
	assert(rez[1].get_producator() == "Gheorghe");
	assert(rez[1].contor() == 1);
}


void Test::runAllTests()
{
	testAdaugare();
	testAfisare();
	testCautare();
	testActualizare();
	testStergere();
	testFiltrari();
	testSortari();
	testRaport();
}