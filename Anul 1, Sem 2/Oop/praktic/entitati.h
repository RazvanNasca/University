#pragma once
#include <string>

using namespace std;

class Melodie {

private:

	int id;
	string titlu;
	string artist;
	int rank;

public:

	Melodie(int id, string titlu, string artist, int rank): id{id}, titlu{titlu}, artist{artist}, rank{rank}{}

	int getId();

	string getTitlu();

	string getArtist();

	int getRank();

	void setTitlu(string t);

	void setRank(int r);

};
