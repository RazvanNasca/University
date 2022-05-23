#include "teste.h"
#include "repo.h"
#include "service.h"
#include "validator.h"
#include <assert.h>
#include <iostream>
#include <fstream>
using std::cout;


void Test::testAdaugare()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	s.adauga("Paracetamol", "Gica", 15, 2);
	vector<Medicament> meds = s.getAll();
	assert(meds.size() == 1);

	try {
		r.findMed("Par");
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}

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
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };
	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Nurofen", "Flaviu", 15, 2);
	const auto& meds = s.getAll();
	assert(meds.size() == 2);
}

void Test::testCautare()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };
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
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };
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
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };
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
	Ret ret{ r };
	Valid v;
	Service s{ r , v ,ret };

	vector <Medicament> rez;

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
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	vector <Medicament> rez;

	s.adauga("Paracetamol", "Gica", 15.2, 2);
	s.adauga("Nurofen", "Flaviu", 20, 7);

	rez = s.sorteazaDenumire();
	assert(rez.at(0).getDenumire() == "Nurofen");

	rez = s.sorteazaProducator();
	assert(rez.at(0).getProducator() == "Flaviu");

	rez = s.sorteazaPretSubst();
	assert(rez.at(0).getDenumire() == "Paracetamol");

	s.adauga("Para", "Gica", 15.2, 1);
	rez = s.sorteazaPretSubst();
	assert(rez.at(0).getDenumire() == "Para");

}

void Test::testRaport()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	s.adauga("Nurofen", "Florin", 10, 2);
	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Panadol", "Gheorghe", 12, 3);

	vector<DTO>rez = s.raport();

	assert(rez[2].get_denumire() == "Paracetamol");
	assert(rez[2].get_producator() == "Gica");
	assert(rez[2].contor() == 2);
	assert(rez[2].get_subst() == "2");
	assert(rez[1].get_denumire() == "Panadol");
	assert(rez[1].get_producator() == "Gheorghe");
	assert(rez[1].contor() == 1);
}

void Test::testTotal()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	s.adauga("Nurofen", "Florin", 10.2, 2);
	s.adauga("Paracetamol", "Gica", 15, 2);
	s.adauga("Panadol", "Gheorghe", 12, 3);

	assert(s.total() == 37.2);
}

void Test::testAdaugaReteta()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };
	s.adaugaReteta("Paracetamol", "Gica", 15, 2);
	vector<Medicament> meds = s.getAllReteta();
	assert(meds.size() == 1);

	try {
		s.adaugaReteta("Parac2tamol", "Gica", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adaugaReteta("Paractamol", "Gi3ca", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adaugaReteta("Paractamol", "Gica", -15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adaugaReteta("Paractamol", "Gica", 15, -2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adaugaReteta("", "Gica", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}

	try {
		s.adaugaReteta("Paracetamol", "", 15, 2);
		assert(false);
	}
	catch (ValidException&) {
		assert(true);
	}
}

void Test::testClearAll()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	s.adaugaRandom(4);
	s.clearAll();
	const auto& meds = s.getAllReteta();
	assert(meds.size() == 0);
}

void Test::testAdaugaRandom()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	s.adaugaRandom(4);
	const auto& meds = s.getAllReteta();
	assert(meds.size() == 4);
}

void Test::testAfisareReteta()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };
	s.adaugaReteta("Paracetamol", "Gica", 15, 2);
	s.adaugaReteta("Nurofen", "Flaviu", 15, 2);
	const auto& meds = s.getAllReteta();
	assert(meds.size() == 2);
}

void Test::testExporta() {

	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	s.adaugaReteta("aaa", "bbb", 3, 2);
	s.adaugaReteta("ccc", "bbb", 2, 10);
	s.adaugaReteta("bbb", "bbb", 1, 3);
	s.adaugaRandom(3);
	s.exportCSV("testExport.csv");

	std::ifstream in("testExport.csv");
	assert(in.is_open());
	int countLines = 0;
	while (!in.eof()) {
		string line;
		in >> line;
		countLines++;
	}
	in.close();

	//assert(countLines == 4);//avem o linie pentru fiecare pet + o linie goala
	s.exportHTML("testExport.html");
	in.open("testExport.html");
	assert(in.is_open());

	//daca se da un nume de fisier invalid se arunca exceptie
	try {
		s.exportCSV("test/Export.csv");
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
	try {
		s.exportHTML("a/b/c/Export.html");
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
}

void Test::testUndo()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	try {
		s.undo();
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}

	s.adauga("Paracetamol", "Mircea", 12.3, 2);
	s.undo();
	assert(s.getAll().size() == 0);

	s.adauga("Paracetamol", "Mircea", 12.3, 2);
	s.stergere("Paracetamol", "Mircea", 12.3, 2);
	s.undo();
	assert(s.getAll().size() == 1);

	s.actualizare("Paracetamol", "Mircea", 10, 20);
	s.undo();
	assert(s.getAll().begin()->getPret() == 12.3);

	s.adaugaReteta("Paracetamol", "Mircea", 10, 20);
	s.adaugaReteta("Paramol", "Mircea", 10, 20);
	s.undo();

	assert(s.getAllReteta().size() == 1);
}

void Test::testRepoFile() {
	//std::ofstream out("test.txt", std::ios::trunc);
	//out.close();//creez un fisier gol
	RepoFile repF{ "test.txt"};
	repF.store(Medicament{ "aaa","bbb",12,3 });

	RepoFile repF2{ "test.txt"};
	auto p = repF2.getAll().begin();
	assert(p->getPret() == 12);

	repF2.stergere(Medicament{ "aaa","bbb",12,3 });
	assert(repF2.getAll().size() == 0);
	try {
		repF2.stergere(Medicament{ "aaa","bbb",12,3 });
		assert(false);
	}
	catch (RepoException&) {
	}

	RepoFile repF3{ "test.txt"};
	assert(repF3.getAll().size() == 0);

	try {
		RepoFile repF4{ "te/st.txt"};
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}
}

void Test::testErori()
{
	Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	int cntDA = 0, cantNU = 0;

	for (int i = 1; i <= 100; i++)
		try {
		string denumire = s.randomString();
		string producator = s.randomString();
		double pret = (double)rand();
		int subst_activa = (int)rand();

		s.adauga(denumire, producator, pret, subst_activa);
		cout << "DAAA!\n";
		cntDA++;
	}
	catch (RepoException&) {
		cout << "NUU!!\n";
		cantNU++;
	}

	cout << cntDA << " " << cantNU;
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
	testTotal();
	testAdaugaReteta();
	testClearAll();
	testAdaugaRandom();
	testAfisareReteta();
	testExporta();
	testUndo();
	///testRepoFile();
	testErori();
}