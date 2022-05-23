#include<fstream>
using namespace std;

ifstream fin("in.txt");
ofstream fout("out.txt");

int G[1001][1001], v[1001];

void initializare(int n)
{
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			G[i][j] = 0;
}

void citire(int n)
{
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			fin >> G[i][j];
}

void DFS(int nod, int n)
{
	v[nod] = 1;

	for (int i = 0; i < n; i++)
		if (!v[i] && G[i][nod])
			DFS(i,n);
}


int main()
{
	int n;
	fin >> n;

	initializare(n);
	citire(n);

	DFS(1,n);

	bool ok = true;
	for (int i = 0; i < n && ok == true; i++)
		if (!v[i])
			ok = false;

	if (ok == false)
		fout << 0;
	else
		fout << 1;

	fin.close();
	fout.close();
	return 0;
}