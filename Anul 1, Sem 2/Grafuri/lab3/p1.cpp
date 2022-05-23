/*Problema 1, implemantare dinamica Dijktra, Nasca Razvan-Alexandru, 215/1*/

#include<fstream>
#include<vector>
#include<queue>
#define INF 0x3f3f3f3f

/*using std::vector;
using std::pair;
using std::priority_queue;*/

using namespace std;

ifstream fin("p1.in");
ofstream fout("p1.out");

int n, m, nodSursa, D[10001], inCoada[10001];

struct Cmp{

    bool operator()(int x, int y)
    {
        return D[x] > D[y];
    }

};

vector < pair <int,int> > G[150001];
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

        for(int i = 0; i < G[nodCurent].size(); i++)
        {
            int vecin = G[nodCurent][i].first;
            int cost = G[nodCurent][i].second;

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


int main()
{
    fin >> n >> m >> nodSursa;
    int x, y, w;
    for(int i = 1; i <= m; i++)
    {
        fin >> x >> y >> w;
        G[x].push_back(make_pair(y,w));
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


