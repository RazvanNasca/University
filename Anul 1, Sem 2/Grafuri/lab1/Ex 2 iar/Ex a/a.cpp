#include<iostream>
#include<fstream>

using namespace std;

ifstream fin("in.txt");
ofstream fout("out.txt");

int noduri, muchii, graf[1001][1001], izolat;

int main()
{
	fin >> noduri >> muchii;
	int a, b;

	for (int i = 1; i <= muchii; i++)
	{
		fin >> a >> b;
		graf[a][b] = graf[b][a] = 1;
	}


	for (int i = 0; i < noduri; i++)
	{
		izolat = i;
		bool ok = true; /// presupunem ca nodul e izolat
		for (int j = 0; j < noduri && ok == true; j++)
			if (graf[i][j] == 1 || graf[j][i] == 1)
				ok = false; /// nodul nu e izolat

		if (ok == true)
			fout << izolat << " ";
	}


	fin.close();
	fout.close();
	return 0;
}
