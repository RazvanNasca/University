#pragma once
#pragma once
#include <string>
#include <vector>

using namespace std;

class Task {

private:
	int id;
	string descriere;
	string stare;
	vector <string> programatori;

public:

	Task(int id, string descriere, string stare, vector <string> programatori) : id{ id }, descriere{ descriere }, stare{ stare }, programatori{ programatori }{}

	/// returneaza id-ul task-ului
	int getID() const;

	/// returneaza descrierea task-ului
	string getDescriere() const;

	/// returneaza descrierea task-ului
	string getStare() const;

	/// seteaza descrierea task-ului
	void setStare(string st);

	/// returneaza toti programatorii
	vector <string> getProgramatori();

};
