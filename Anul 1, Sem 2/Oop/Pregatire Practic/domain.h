#pragma once
#include <vector>
#include <string>

using namespace std;

class SerialTV {

private:
	string numeSerial;
	string gen;
	int anAP;
	int nrEp;
	int nrEpV;

public:

	SerialTV(string numeSerial, string gen, int anAP, int nrEp, int nrEpV) : numeSerial{ numeSerial }, gen{ gen }, anAP{ anAP }, nrEp{ nrEp }, nrEpV{ nrEpV }{};
	
	string getNume();

	string getGen();

	int getAn();

	int getNrEp();

	int getNrEpV();

	void setNrEpV(int e);

};