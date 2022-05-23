/*Problema 1, implemantare statica Bellman-Ford, Nasca Razvan-Alexandru, 215/1*/

#include<iostream>
#include<fstream>
#define INF 0x3f3f3f3f

using namespace std;

ifstream fin("ex1.in");
ofstream fout("ex1.out");

int n, m, nodSursa, D[10001];  /// nr_noduri, nr_muchii, nodSursa, vector de distante

struct graf{    /// definesc o structura in care sa pot retine muchia "nod -> adiacent" cu costul "cost"
    int nod, adiacent, cost;
};

graf G[150001];


void BellmanFord(int nod)
{

    for(int i = 0; i < n; i++)
        D[i] = INF;             /// initializam cu un numar mare la inceput toate nodurile
    D[nod] = 0;                 /// nodul de la care pornim o sa fie zero pt ca distanta  nod->nod e nula

    /// relaxare muchiilor

    for(int i = 0; i < n - 1; i++)              /// parcurgem n-1 noduri
        for(int j = 1; j <= m; j++)         /// parcurgem toate muchiile
        {
            int nodCurent = G[j].nod;
            int vecin = G[j].adiacent;
            int cost = G[j].cost;

            if(D[nodCurent] != INF && D[nodCurent] + cost < D[vecin])   /// daca puteam, actualizam distanta dintre 2 noduri printr-un nod intermediar
                D[vecin] = D[nodCurent] + cost;
        }

    /// cautam cicluri negative ... refacem ceea ce am facut mai sus
    for(int i = 1; i <= m; i++)
    {
        int nodCurent = G[i].nod;
        int vecin = G[i].adiacent;
        int cost = G[i].cost;

        if(D[nodCurent] != INF && D[nodCurent] + cost < D[vecin])
        {
            fout << "Ciclu negativ";
            return;  /// stiu ca ii functie void, dar asa am gasit algoritmul si astfel iese din functie chair daca nu e 100% cirect, dar ai inteles algoritmul
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

    BellmanFord(nodSursa);

    for(int i = 0; i < n; i++)
        if(D[i] != INF)
            fout << D[i] << " ";
        else
            fout << "INF" << " ";


    fin.close();
    fout.close();
    return 0;
}




