/*Problema 1, implemantare statica Dijktra, Nasca Razvan-Alexandru, 215/1*/

#include<fstream>
#include<queue>
#define INF 0x3f3f3f3f

using namespace std;

/*
ifstream fin("ex1.in");
ofstream fout("ex1.out");
*/

int n, m, nodSursa, D[10001], inCoada[10001];

struct graf{
    int nod, adiacent, cost;
};

graf G[150001];

struct Cmp{

    bool operator()(int x, int y)
    {
        return D[x] > D[y];
    }

};

priority_queue < int, vector <int>, Cmp> Q;

void Dijkstra(int nod)
{
    for(int i = 0; i < n; i++)
        D[i] = INF;
    D[nod] = 0;

    Q.push(nod);
    inCoada[nod] = 1;

    while(!Q.empty())
    {
        int nodCurent = Q.top();
        Q.pop();
        inCoada[nodCurent] = 0;

        for(int i = 1; i <= m; i++)
            if(G[i].nod == nodCurent)
            {
                int vecin = G[i].adiacent;
                int cost = G[i].cost;

                if(D[nodCurent] + cost < D[vecin])
                {
                    D[vecin] = D[nodCurent] + cost;
                    if(inCoada[vecin] == 0)
                    {
                        inCoada[vecin] = 1;
                        Q.push(vecin);
                    }
                }
            }
    }

}


int main(int argc, char * argv[])
{
    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]);

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



