/*Problema 2, implemantare statica Johnson, Nasca Razvan-Alexandru, 215/1*/

#include<fstream>
#include<queue>
#define INF 0x3f3f3f3f

using namespace std;

///ifstream fin("ex2.in");
///ofstream fout("ex2.out");

int n, m, nodSursa, D[1001], inCoada[1001], muchie[10001];

struct graf{
    int nod, adiacent, cost;
};

graf G[10001];

struct Cmp{

    bool operator()(int x, int y)
    {
        return D[x] > D[y];
    }

};

priority_queue < int, vector <int>, Cmp> Q;

int Johnson(ofstream& fout);
void sortareMuchii();
int BellmanFord(int nod);
void Dijkstra(int nod);
void afisareDistante();



int main(int argc, char * argv[])
{

    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]);

    fin >> n >> m;
    int x, y, w;
    for(int i = 1; i <= m; i++)
    {
        fin >> x >> y >> w;
        G[i].nod = x;
        G[i].adiacent = y;
        G[i].cost = w;
    }


    int john = Johnson(fout);
    if(john == -1)
        fout << -1;


    fin.close();
    fout.close();
    return 0;
}

int BellmanFord(int nod)
{

    for(int i = 0; i <= n; i++)
        D[i] = INF;
    D[nod] = 0;

    /// relaxare muchiilor

    for(int i = 0; i < n ; i++)
        for(int j = 1; j <= m + n; j++)
        {
            int nodCurent = G[j].nod;
            int vecin = G[j].adiacent;
            int cost = G[j].cost;

            if(D[nodCurent] != INF && D[nodCurent] + cost < D[vecin])
                D[vecin] = D[nodCurent] + cost;
        }


    for(int i = 1; i <= m + n; i++)
    {
        int nodCurent = G[i].nod;
        int vecin = G[i].adiacent;
        int cost = G[i].cost;

        if(D[nodCurent] != INF && D[nodCurent] + cost < D[vecin])
            return -1;
    }

    return 0;
}



void Dijkstra(int nod, ofstream& fout)
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

    for(int i = 0; i < n; i++)
        if(D[i] != INF)
            fout << D[i] << " ";
        else
            fout << "INF" << " ";
    fout << '\n';

}

void sortareMuchii()
{
    for(int i = 1; i < m; i++)
        for(int j = i+1; j <= m; j++)
        {
            int nod1 = G[i].nod;
            int vecin1 = G[i].adiacent;
            int nod2 = G[j].nod;
            int vecin2 = G[j].adiacent;

            if(nod1 > nod2)
            {
                graf aux = G[i];
                G[i] = G[j];
                G[j] = aux;
            }
            else
                if(nod1 == nod2)
                    if(vecin1 > vecin2)
                    {
                        graf aux = G[i];
                        G[i] = G[j];
                        G[j] = aux;
                    }
        }
}

int Johnson(ofstream& fout)
{
    /// adaugam un nod nou
    for(int i = 0; i < n; i++)
    {
        G[m + i + 1].nod = n;
        G[m + i + 1].adiacent = i;
        G[m + i + 1].cost = 0;
    }

    /// facem Bellman Ford pt noul graf
    int bell = BellmanFord(n);
    if(bell == -1)
        return -1;

    /// sortam muchiile pt afisare
    sortareMuchii();


    /// facem update la costuri
    for(int i = 1; i <= m; i++)
        muchie[i] = G[i].cost + D[G[i].nod] - D[G[i].adiacent];


    for(int i = 1; i <= m; i++)
        fout << G[i].nod << " " << G[i].adiacent << " " << muchie[i] << '\n';


    /// apelam Dijktra pt toate nodurile
    for(int i = 0; i < n; i++)
        Dijkstra(i, fout);

    return 0;
}





