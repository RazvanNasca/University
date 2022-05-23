/* Nasca Razvan-Alexandru, 215/1 */
#include<fstream>
#include<queue>
#define INF 0x3f3f3f3f
using namespace std;

ifstream fin("ex1.in");
ofstream fout("ex1.out");

int n, m, nsursa;
int l[101];
int G[101][101];
int p[101];


void afiseaza(int nod, int lungime)
{
    if(nod == nsursa)
        fout << lungime + 1 << " " << nod << " ";
    else
    {
        afiseaza(p[nod],lungime + 1);
        fout << nod << " ";
    }

}

int main()
{
    fin >> n >> m >> nsursa;
    int a, b;
    for(int i = 1; i <= m; i++)
    {
        fin >> a >> b;
        G[a][b] = 1;
    }

    for(int i = 0; i < n; i++)
        l[i] = INF, p[i] = -1;

    l[nsursa] = 0;
    p[nsursa] = -1;

    queue <int> Q;

    Q.push(nsursa);

    while(!Q.empty())
    {
        int nod_curent = Q.front();
        for(int j = 0; j < n; j++)
            if(G[nod_curent][j] == 1)
                if(l[j] == INF)
                {
                    p[j] = nod_curent;
                    l[j] = l[nod_curent] + 1;
                    Q.push(j);
                }
        Q.pop();
    }

    for(int i = 0; i < n; i++)
        if(i != nsursa && p[i] != -1)
        {
            afiseaza(i, 0);
            fout << '\n';
        }
        else
            if(i == nsursa)
                fout << 1 << " " << nsursa << '\n';


    fin.close();
    fout.close();
    return 0;
}
