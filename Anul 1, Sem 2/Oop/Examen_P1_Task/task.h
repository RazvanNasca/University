#pragma once
#include <vector>
#include <string>

using namespace std;

class Task {

private:
	
	int id;
	string descriere;
	vector<string> programatori;
	string stare;

public:

	Task(int id, string descriere, vector<string> programatori, string stare): id{id}, descriere{descriere}, programatori{programatori}, stare{stare}{}

	/// getters
	int getId();
	string getDescriere();
	string getStare();
	vector<string> getProgramatori();

	/// setters
	void setStare(string s);
	void setProgramatori(string p);

};
