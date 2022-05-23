#include "teste.h"

void Teste::testAdauga()
{
	Repo r("teste.txt");
	Valid v;

	Service s{ r,v };

	vector<string> p;
	p.clear();
	p.push_back("Marius");
	string descriere = "ceva";
	string stare = "open";

	try {

		s.adauga(1, descriere, p, stare);
		assert(true);
	}
	catch (ValidException& e)
	{
		assert(false);
	}
	catch (RepoException& e)
	{
		assert(false);
	}

	stare = "op";

	try {

		s.adauga(1, descriere, p, stare);
		assert(false);
	}
	catch (ValidException& e)
	{
		assert(true);
	}

	ofstream f("teste.txt");
	f.clear();
	f.close();

}

void Teste::testSorteazaDescriere()
{
	Repo r("teste.txt");
	Valid v;

	Service s{ r,v };

	vector<string> p;
	p.clear();
	p.push_back("Marius");
	string descriere = "ceva";
	string stare = "open";
	int id = 1;

	s.adauga(id, descriere, p, stare);


	p.clear();
	p.push_back("Marian");
	id = 2;
	descriere = "aaa";

	s.adauga(id, descriere, p, stare);

	auto rez = s.sorteazaDescriere();
	assert(rez[0].getId() == 2);

	ofstream f("teste.txt");
	f.clear();
	f.close();
	
}

void Teste::testStergere()
{
	Repo r("teste.txt");
	Valid v;

	Service s{ r,v };

	vector<string> p;
	p.clear();
	p.push_back("Marius");
	string descriere = "ceva";
	string stare = "open";
	int id = 1;

	s.adauga(id, descriere, p, stare);

	p.clear();
	p.push_back("Marian");
	id = 2;
	descriere = "aaa";

	s.adauga(id, descriere, p, stare);

	p.clear();
	p.push_back("Marius");
	id = 2;
	descriere = "aaa";

	Task t{ id, descriere, p, stare };

	s.sterge(t);

	auto rez = s.getAll();
	assert(rez.size() == 1);

	ofstream f("teste.txt");
	f.clear();
	f.close();
}

void Teste::testTaskProg()
{
	Repo r("teste.txt");
	Valid v;

	Service s{ r,v };

	vector<string> p;
	p.clear();
	p.push_back("Marius");
	string descriere = "ceva";
	string stare = "open";
	int id = 1;

	s.adauga(id, descriere, p, stare);

	p.clear();
	p.push_back("Marian");
	id = 2;
	descriere = "aaa";

	s.adauga(id, descriere, p, stare);

	p.clear();
	p.push_back("Marius");
	id = 2;
	descriere = "aaa";

	Task t{ id, descriere, p, stare };

	auto rez = s.getTasksProg("Marius");
	assert(rez.size() == 1);

	auto rez2 = s.getTasksProg("Marian");
	assert(rez.size() == 1);

	ofstream f("teste.txt");
	f.clear();
	f.close();

}

void Teste::testProg()
{

	Repo r("teste.txt");
	Valid v;

	Service s{ r,v };

	vector<string> p;
	p.clear();
	p.push_back("Marius");
	string descriere = "ceva";
	string stare = "open";
	int id = 1;

	s.adauga(id, descriere, p, stare);

	p.clear();
	p.push_back("Marian");
	id = 2;
	descriere = "aaa";

	s.adauga(id, descriere, p, stare);

	p.clear();
	p.push_back("Marcel");
	id = 3;
	descriere = "aaa";

	auto rez = s.getProg();
	assert(rez.size() == 2);

	ofstream f("teste.txt");
	f.clear();
	f.close();


}

void Teste::run()
{
	testAdauga();
	testSorteazaDescriere();
	testStergere();
	testTaskProg();
	testProg();
}
