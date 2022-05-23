#include "teste.h"

void Teste::testAdaugare()
{
	Repo r{ "teste.txt" };
	Valid v;
	Service s{ r,v };

	vector <string> programatori;
	programatori.push_back("Andrei");
	programatori.push_back("Mihai");
	programatori.push_back("Denis");
	///Task t{ 1, "da", "open", programatori };

	s.adauga(1, "da", "open", programatori);

	try {
		s.adauga(1, "da", "open", programatori);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}

	try {
		s.adauga(2, "da", "oen", programatori);
		assert(false);
	}
	catch (ValidException&)
	{
		assert(true);
	}

	programatori.push_back("Florin");
	programatori.push_back("Catalin");

	try {
		s.adauga(3, "da", "open", programatori);
		assert(false);
	}
	catch (ValidException&)
	{
		assert(true);
	}
}

void Teste::testModifica()
{
	Repo r{ "teste.txt" };
	Valid v;
	Service s{ r,v };

	s.modifica(1, "closed");

	try {
		s.modifica(2, "closed");
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}

}

void Teste::testCautaID()
{
	Repo r{ "teste.txt" };
	vector <string> programatori;
	programatori.push_back("Andrei");
	programatori.push_back("Mihai");
	programatori.push_back("Denis");
	Task t{ 1, "da", "open", programatori };

	assert(r.cautaID(t) == 1);

	Task t2{ 2, "da", "open", programatori };
	assert(r.cautaID(t2) == 0);
}

void Teste::testCautaProg()
{
	Repo r{ "teste.txt" };
	Valid v;
	Service s{ r,v };

	vector <string> programatori;
	programatori.push_back("Andrei");
	programatori.push_back("Mihai");
	s.adauga(2, "da", "open", programatori);

	auto& rez = s.cautaProg("Andrei");
	assert(rez.size() == 2);

	rez = s.cautaProg("Razvan");
	assert(rez.size() == 0);

	rez = s.cautaProg("Denis");
	assert(rez.size() == 1);
}

void Teste::testStocare()
{
	Repo r{ "teste.txt" };

	vector <Task> rez;
	r.loadFromFile();
	rez = r.getAll();

	assert(rez.size() == 2);
}

void Teste::run()
{
	testAdaugare();
	testCautaID();
	testCautaProg();
	testModifica();
	testStocare();
}
