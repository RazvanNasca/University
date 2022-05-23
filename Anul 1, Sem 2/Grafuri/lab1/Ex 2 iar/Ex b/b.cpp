#include<fstream>
using namespace std;

ifstream fin("in.txt");
ofstream fout("out.txt");

int n, m, g[1001][1001], grad;

int main()
{
	fin >> n >> m;
	int a, b;
	for (int i = 1; i <= m; i++)
	{
		fin >> a >> b;
		g[a][b] = g[b][a] = 1;
	}

	for (int i = 0; i < n; i++)
		if (g[i][0] == 1)
			grad++;

	bool ok = true;
	for (int i = 0; i < n && ok == true; i++)
	{
		int cnt_grad = 0;
		for (int j = 0; j < n; j++)
			if (g[i][j] == 1)
				cnt_grad++;

		if (cnt_grad != grad)
			ok = false;
	}

	if (ok == true)
		fout << 1;
	else
		fout << 0;


	fin.close();
	fout.close();
	return 0;
}