#include<fstream>

#include<iostream>

using namespace std;

ifstream fin("lab1.in");
ofstream aout("matrice_adiacenta.txt");
ofstream iout("matrice_incidenta.txt");

struct muchie{
    int n1, n2;
}m[5000];


int n, G[1001][1001], muchii, m_incid[1000][5000];

int main()
{
    fin >> n >> muchii;
    //cerr << n;
    int a, b, i=0;
    while (i < muchii)
    {
        fin >> a >> b;
        G[a][b] = G[b][a] = 1;
        m[i].n1 = a;
        m[i].n2 = b;
        i++;
    }


    aout << n << '\n';
    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
            aout << G[i][j] << " ";
        aout << "\n";
    }


    ///sortam muchiile
    for(int i = 0; i < muchii-1; i++)
        for(int j = i+1; j < muchii; j++)
            if(m[i].n1 > m[j].n1)
                swap(m[i],m[j]);
            else
                if (m[i].n1 == m[j].n1)
                    if(m[i].n2 > m[j].n2)
                        swap(m[i],m[j]);


    for(int j = 0; j < muchii; j++)
        m_incid[m[j].n1][j] = m_incid[m[j].n2][j] = 1;


    iout << n << '\n';
    for(int i = 0; i < n; i++, iout << '\n')
        for(int j = 0; j < muchii; j++)
            iout << m_incid[i][j] << ' ';


    fin.close();
    aout.close();
    iout.close();
    return 0;
}
