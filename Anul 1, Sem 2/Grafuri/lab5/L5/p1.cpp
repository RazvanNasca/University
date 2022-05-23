/*Problema 1, implementare algoritmul lui Edmonds-Karp, Nasca Razvan 215/1*/
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#define INF 0x3f3f3f3f

using namespace std;

ifstream fin("ex1.in");
ofstream fout("ex1.out");

vector<vector<int>> G;     /// matricea de adiacenta
vector<vector<int>> flux;  /// matricea fluxurilor
vector <int> t;             /// vectori de tati
vector <bool> viz;          /// vectori de vizitati
queue <int> Q;              /// coada pentru bfs
int n, m;                   /// numarul de noduri si de muchii

int bfs(int sursa, int dest)
{
	for(int i = 0; i < n; i++)
    {
        viz[i] = 0;
        t[i] = -1;
    }

	Q.push(sursa);
	viz[sursa]=1;

	while(!Q.empty())
	{
		int u = Q.front(); ///scoatem primul element din coada

		for(int i = sursa; i <= dest; i++) /// pt fiecare nod ( adiacent )
			if(viz[i] == 0)
				if(G[u][i] - flux[u][i] > 0)
				{
					Q.push(i); /// inseram nodul i in coada
					t[i] = u;
					viz[i] = 1;
				}
        Q.pop();
	}

	if(t[dest] != -1)
        return 1;
	return 0;
}

int edmond_karp(int sursa, int dest)
{
	int flow=0;
	int minim;

	while(bfs(sursa, dest)) /// cat timp mai exista un drum de ameliorare
	{
		minim = INF;
		for(int i = dest; i; i = t[i])
			if(G[t[i]][i] - flux[t[i]][i] < minim)
                minim = G[t[i]][i] - flux[t[i]][i];     ///calculam minimul dintre capacitatile ramase de pe drum


		for(int i = dest ; i; i = t[i])
        {
            flux[t[i]][i] += minim; ///adaugam minimul la fluxul de pe arcele de pe drum
			flux[i][t[i]] -= minim; ///scadem minimul de pe arcele inverse
        }

		flow += minim; /// adaugam minimul la flux
	}

    return flow;
}

/*int main(int argc, char* argv[])
{
    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]); */

int main()
{

    fin >> n >> m;

    /// initializam
    t.resize(n, 0);
    viz.resize(n, 0);
    G.resize(n);
    flux.resize(n);
    for(int i = 0; i < n; i++)
    {
        G[i].resize(n, 0);
        flux[i].resize(n, 0);
    }

    /// citim
    int x, y, c;
    for(int i = 0; i < m; i++)
    {
        fin >> x >> y >> c;
        G[x][y] = c;
    }

    fout << edmond_karp(0,n-1);

    fin.close();
    fout.close();
    return 0;
}

