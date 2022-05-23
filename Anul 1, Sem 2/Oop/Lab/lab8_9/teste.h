#pragma once

class Test {
public:
	void runAllTests();

private:
	/*
		testeaza functia de adaugare
	*/
	void testAdaugare();

	/*
		testeaza functia de afisare
	*/
	void testAfisare();

	/*
		testeaza functia de cautare
	*/
	void testCautare();

	/*
		testeaza functia de actualizare
	*/
	void testActualizare();

	/*
		testeaza functia de stergere
	*/
	void testStergere();

	/*
		testeaza functia de filtrare
	*/
	void testFiltrari();

	/*
		testeaza functia de sortare
	*/
	void testSortari();

	void testRaport();

	void testTotal();
	
	void testAdaugaReteta();
	
	void testClearAll();
	
	void testAdaugaRandom();
	
	void testAfisareReteta();

	void testExporta();

	void testUndo();

	void testRepoFile();

	void testErori();
};
