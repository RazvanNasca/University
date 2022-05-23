/* Nasca Razvan-Alexandru, 215/1 */
#include<fstream>
#include<queue>
using namespace std;

ifstream fin("ex2.in");
ofstream fout("ex2.out");

int n, m;
int G[101][101];
int vizitat[101];


void reinitializare()
{
    for(int i = 0; i < n; i++)
        vizitat[i] = 0;
}

void DFS(int nod_curent)
{
    vizitat[nod_curent] = 1;
    for(int i = 0; i < n; i++)
        if(G[nod_curent][i] == 1 && vizitat[i] == 0)
            DFS(i);
}

void afisare()
{
    for(int i = 0; i < n; i++)
        fout << vizitat[i] << ' ';
    fout << '\n';
}


int main()
{
    fin >> n >> m;
    int a, b;
    for(int i = 1; i <= m; i++)
    {
        fin >> a >> b;
        G[a][b] = 1;
    }

    reinitializare();

    for(int i = 0; i < n; i++)
    {
        DFS(i);
        afisare();
        reinitializare();
    }


    fin.close();
    fout.close();
    return 0;
}

