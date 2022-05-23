/*Algoritmul lui Kruskal, Nasca Razvan, 215/1*/
#include<iostream>
#include<fstream>
#include<algorithm>
#include<vector>
using namespace std;

///ifstream fin("p1.in");
///ofstream fout("p1.out");

struct muchie{
    int x, y, c;
};

vector<muchie> M;   /// lista de muchii
vector<int> viz;    /// muchia a fost sau nu vizitata
vector<int> tata;   /// lista de parinti
vector<int> R;      /// radacini; folosit pt a vedea daca 2 noduri sunt din acelasi arbore

int n, m, s, cnt;

/*bool Cmp(muchie a, muchie b)
{
    return a.c < b.c;
}*/

int radacina(int nod)
{
    int y = nod;
    while(tata[nod] != -1)           /// parcurgem pana ajungem la radacina
        nod = tata[nod];

   ///compresia drumurilor
    while(y != nod)
    {
        int z = tata[y];
        tata[y] = nod;
        y = z;
    }

    return nod;
}

void Kruskal()
{

    for(int i = 0; i < m; i++)      /// parcurgem fiecare muchie
    {
        int ri = radacina(M[i].x);      /// aflam radacinile pentru fiecare extremitate
        int rj = radacina(M[i].y);

        if(ri != rj)                    /// daca nu fac parte dun acelasi subarbore
        {
            s += M[i].c;                /// marim suma
            viz[i] = 1;                 /// marcam muchia ca fiind vizitata
            cnt++;
            if(R[ri] > R[rj])
                tata[rj] = ri;          /// nodul cu radacina cu mai multi fii devine parinte
            else
                {
                    tata[ri] = rj;
                    if(R[ri] == R[rj])
                        R[rj]++;
                }
        }
    }
}

int main(int argc, char* argv[])
{
    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]);

    fin >> n >> m;
    M.resize(m+1);  /// initializam
    viz.resize(m+1);
    tata.resize(n+1);
    R.resize(n+1);

    for(int i = 0; i < n; i++)
        tata[i] = -1, R[i] = -1;

    for(int i = 0; i < m; i++)  /// citim
    {
        fin >> M[i].x >> M[i].y >> M[i].c;
        viz[i] = 0;
    }


    for(int i = 0; i < m - 1; i++)  /// sortam
        for(int j = i + 1; j < m; j++)
            if(M[i].c > M[j].c)
            {
                muchie aux = M[i];
                M[i] = M[j];
                M[j] = aux;
            }

    Kruskal();

    fout << s << '\n' << cnt << '\n';              /// afisam

    for(int i = 0; i < m; i++)
        if(viz[i] == 1)
            fout << M[i].x << " " << M[i].y << '\n';

    fin.close();
    fout.close();
    return 0;
}
