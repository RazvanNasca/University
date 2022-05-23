#include<iostream>
#include<fstream>
#include<algorithm>
using namespace std;

ifstream fin("kruskal.in");
ofstream fout("kruskal.out");

struct muchie{
    int x, y, c;
}M[5000];

int n, m, A[5000], tata[101], R[101];

bool Cmp(muchie A, muchie B)
{
    return A.c < B.c;
/*  if(A.c < B.c)
        return true;
    return false;   */
}

int radacina(int nod)
{
    int y = nod;
    while(tata[nod] != 0)
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

int main()
{
    fin >> n >> m;
    for(int i = 1; i <= m; i++)
        fin >> M[i].x >> M[i].y >> M[i].c;

    ///sort(M+1, M+m+1, Cmp);

    int s = 0;
    for(int i = 1; i <= m; i++)
    {
        int ri = radacina(M[i].x);
        int rj = radacina(M[i].y);

        if(ri != rj)
        {
            s += M[i].c;
            A[i] = 1;
            if(R[ri] > R[rj])
                tata[rj] = ri;
            else
                {
                    tata[ri] = rj;
                    if(R[ri] == R[rj])
                        R[rj]++;
                }
        }
    }

    fout << s << '\n';

    for(int i = 1; i <= m; i++)
        if(A[i] == 1)
            fout << M[i].x << " " << M[i].y << '\n';

    fin.close();
    fout.close();
    return 0;
}
