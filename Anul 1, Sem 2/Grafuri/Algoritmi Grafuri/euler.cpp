/*Problema 1, implementare algoritmul lui Edmonds-Karp, Nasca Razvan 215/1*/
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#define INF 0x3f3f3f3f

using namespace std;

///ifstream fin("ex1.in");
///ofstream fout("ex1.out");

vector<vector<int>> G;
queue <int> Q;
vector <int> vecini;
int n, m;

void euler(int nod)
{
    while(vecini[nod] > 0)
    {
        /// luam un vecin
        bool gasit = 0;
        int alt_nod;
        for(int i = 0; i < n && gasit == 0; i++)
            if(G[nod][i] == 1)
            {
                alt_nod = i;
                gasit = 1;
            }

        /// stergem muchia
        G[nod][alt_nod] = 0;
        G[alt_nod][nod] = 0;
        vecini[nod]--;
        vecini[alt_nod]--;

        euler(alt_nod);
    }

    Q.push(nod);
}

int main(int argc, char* argv[])
{
    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]);

    fin >> n >> m;

    /// initializam
    G.resize(n);
    vecini.resize(n);
    for(int i = 0; i < n; i++)
        G[i].resize(n, 0), vecini[i] = 0;

    int x, y;
    for(int i = 0; i < m; i++)
    {
        fin >> x >> y;
        G[x][y] = 1;
        G[y][x] = 1;
        vecini[x]++;
        vecini[y]++;
    }

    euler(0);

    while(Q.size() > 1)
    {
        int nod = Q.front();
        fout << nod << " ";
        Q.pop();
    }

    fin.close();
    fout.close();
    return 0;
}
