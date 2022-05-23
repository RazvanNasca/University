/*Problema 1, implemantare statica Dijktra, Nasca Razvan-Alexandru, 215/1*/

#include<iostream>
#include<fstream>
#include<queue>
#define INF 0x3f3f3f3f

using namespace std;

ifstream fin("ex1.in");
ofstream fout("ex1.out");

int n, m, nodSursa, D[10001], inCoada[10001];  /// nr_noduri, nr_muchii, nodSursa, vector de distante, vector care imi arata daca nodul e in coada

struct graf{    /// definesc o structura in care sa pot retine muchia "nod -> adiacent" cu costul "cost"
    int nod, adiacent, cost;
};

graf G[150001];

queue <int> Q; /// coada in care elementele se vor adauga

void Dijkstra(int nod)
{
    for(int i = 0; i < n; i++)
        D[i] = INF;             /// initializam cu un numar mare la inceput toate nodurile
    D[nod] = 0;                 /// nodul de la care pornim o sa fie zero pt ca distanta  nod->nod e nula

    Q.push(nod);                /// punem nodul in coada
    inCoada[nod] = 1;           /// marcam nodul ca fiind in coada

    while(!Q.empty())
    {
        int nodCurent = Q.front();    /// extragem nodul din varful cozii
        Q.pop();                    /// il eliminam
        inCoada[nodCurent] = 0;     /// il marcam ca nefiind in coada

        for(int i = 1; i <= m; i++)     /// parcurgem lista muchiilor
            if(G[i].nod == nodCurent)   /// ne legam doar de muchiile cu prima componenta ca fiind nodCurent
            {
                int vecin = G[i].adiacent;
                int cost = G[i].cost;

                if(D[nodCurent] + cost < D[vecin])  /// daca costul este mai mic printr-un nod intermediar
                {
                    D[vecin] = D[nodCurent] + cost;    /// actualizam
                    if(inCoada[vecin] == 0)            /// si daca nodul nu e in coada il adaugam
                    {
                        inCoada[vecin] = 1;
                        Q.push(vecin);
                    }
                }
            }
    }

}


int main()
{
    fin >> n >> m >> nodSursa;
    int x, y, w;
    for(int i = 1; i <= m; i++)
    {
        fin >> x >> y >> w;
        G[i].nod = x;
        G[i].adiacent = y;
        G[i].cost = w;
    }

    Dijkstra(nodSursa);

    for(int i = 0; i < n; i++)
        if(D[i] != INF)
            fout << D[i] << " ";
        else
            fout << "INF" << " ";


    fin.close();
    fout.close();
    return 0;
}



