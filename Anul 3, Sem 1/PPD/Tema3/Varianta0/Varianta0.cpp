#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <chrono>

using namespace std;
using namespace chrono;

void citesteNumar(string fisier, vector<int>& numar, int& n)
{
	ifstream fin(fisier);
	if (!fin)
	{
		cout << "Fisierul nu a putut fi deschis!";
		return;
	}
	
	fin >> n;
	int x;
	for (int i = 0; i < n; i++)
	{
		fin >> x;
		numar.push_back(x);
	}

	fin.close();
}

void egalizare(vector<int>& numar, int n)
{
	for (int i = 0; i < n; i++)
		numar.insert(numar.begin(), 0);
}

void adunare(vector<int> numar1, vector<int> numar2, vector<int>& rezultat)
{
	int carry = 0;
	for (int i = numar1.size() - 1; i >= 0; i--)
	{
		rezultat[i] = (carry + numar1[i] + numar2[i]) % 10;
		carry = (carry + numar1[i] + numar2[i]) / 10;
	}

	if (carry != 0)
		rezultat.insert(rezultat.begin(), carry);
}

void scriereRezultat(string fisier, vector<int> numar)
{
	ofstream fout(fisier);
	if (!fout)
	{
		cout << "Fisierul nu a putut fi deschis!";
		return;
	}


	for (int i = 0; i < numar.size(); i++)
		fout << numar[i];
}

int main(int args, char** argv)
{
	int n1, n2;
	vector<int> numar1;
	vector<int> numar2;
	vector<int> rezultat;

	auto startTime = high_resolution_clock::now();

	citesteNumar("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta0\\Varianta0\\Varianta0\\Primul.txt", numar1, n1);
	citesteNumar("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta0\\Varianta0\\Varianta0\\AlDoilea.txt", numar2, n2);

	if (n1 < n2)
	{
		egalizare(numar1, n2 - n1);
		rezultat.resize(n2);
	}
	if (n1 > n2)
	{
		egalizare(numar2, n1 - n2);
		rezultat.resize(n1);
	}
	if (n1 == n2)
		rezultat.resize(n1);

	adunare(numar1, numar2, rezultat);

	scriereRezultat("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta0\\Varianta0\\Varianta0\\Rezultat.txt", rezultat);

	auto endTime = high_resolution_clock::now();
	double difference = duration<double, milli>(endTime - startTime).count();

	cout << difference << endl;

	return 0;
}