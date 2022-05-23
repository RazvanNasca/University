#include<fstream>
#define INF 10000000
using namespace std;

ifstream fin("in.txt");
ofstream fout("out.txt");

int D[1001][1001];

void initializare(int n)
{
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			D[i][j] = 0;
}

void initializareINF(int n)
{
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			if (i != j && D[i][j] != 1)
				D[i][j] = INF;
}

void citire(int n)
{
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			fin >> D[i][j];
}

void roy(int n)
{	
	for (int k = 0; k < n; k++)
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (D[i][j] > D[i][k] + D[k][j])
					D[i][j] = D[i][k] + D[k][j];
}

void afisare(int n)
{
	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n; j++)
			if (D[i][j] == INF)
				fout << -1 << " ";
			else
				fout << D[i][j] << " ";
		fout << '\n';
	}
}

int main()
{
	int n;
	fin >> n;

	initializare(n);
	citire(n);
	initializareINF(n);
	roy(n);
	afisare(n);

	
	fin.close();
	fout.close();
	return 0;
}