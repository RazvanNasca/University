/*Problema 1, Colorarea grafurilor, Nasca Razvan, 215/1*/
#include<iostream>
#include<fstream>
#include<vector>
#include<queue>

using namespace std;

ifstream fin("ex1.in");
ofstream fout("ex1.out");

int n, m; /// nr de noduri si de muchii
vector <vector<int>> G;  /// matricea de adiacenta
vector <int> culori;    /// rezultatul
queue <int> Q;


bool sigur(int nod, int c)
{
    for(int i = 0; i < n; i++)
        if(G[nod][i] == 1 && culori[i] == c)
            return false;
    return true;
}

void colorare()
{
    Q.push(0);

    while(!Q.empty())
    {
        int nod = Q.front();

        for(int c = 0; c < n && culori[nod] == -1; c++)
            if(sigur(nod,c) == true)
                culori[nod] = c;

        for(int i = 0; i < n; i++)
            if(G[nod][i] == 1 && culori[i] == -1)
                Q.push(i);

        Q.pop();
    }

}

int main()
{
    fin >> n >> m;
    G.resize(n);
    culori.resize(n, -1);

    for(int i = 0; i < n; i++)
        G[i].resize(n, 0);


    int x, y;
    for(int i = 1; i <= m; i++)
    {
        fin >> x >> y;
        G[x][y] = 1;
        G[y][x] = 1;
    }

    colorare();

    for(int i = 0; i < n; i++)
        fout << culori[i] << " ";

    fin.close();
    fout.close();
    return 0;
}
