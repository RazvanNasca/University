/* Nasca Razvan-Alexandru, 215/1 */
#include<iostream>
#include<fstream>
#include<cstring>
#include<queue>

using namespace std;

ifstream fin("ex3.in");
ofstream fout("ex3.out");

int a[1001][1001]; /// labirintul
int b[1001][1001]; /// vizitat sau nu
char c;
int n, m, mm;

queue<pair<int,int>> Q;  /// coada
int ii, ji, id, jd;  /// i_inceput, j_inceput, i_destinatie, j_destinatie

int di[] = {0,0,1,-1};
int dj[] = {1,-1,0,0}; /// vectori de directie

int ok(int i, int j)
{
    if(i < 1 || i > n || j < 1 || j > m )
        return 0;
    return 1;
}


void afisare(int i_curent, int j_curent)
{
    if(i_curent == ii && j_curent == ji)
    {
        fout << i_curent - 1 << " " << j_curent - 1 << '\n';
        return;
    }

    int gasit = 0;
    for(int k = 0; k < 4 && gasit == 0; k++)
    {
        int iv = i_curent + di[k];
        int jv = j_curent + dj[k];

        if(ok(iv,jv) && b[iv][jv] == b[i_curent][j_curent] - 1)
            afisare(iv,jv), gasit = 1;
    }

    fout << i_curent - 1 << " " << j_curent - 1 << '\n';  /// am observat la final ca inexarea era facua de la 0, asa ca am scazut un 1 din coordonatele finale

}


int main()
{
    n = m = 1; /// linii si coloane

    while(fin.get(c))
    {
        while(c != '\n')
        {
            if(c == '1')
                a[n][m] = -1, b[n][m] = -1;
            else
                if(c == ' ')
                    a[n][m] = 0;
                else
                    if(c == 'S')
                    {
                        ii = n;
                        ji = m;
                        a[n][m] = 0;
                    }
                    else
                        if(c == 'F')
                        {
                            id = n;
                            jd = m;
                            a[n][m] = 0;
                        }
            m++;
            fin.get(c);
        }
        mm = m;
        m = 1;
        n++;
    }

    m = mm-1;
    n--;

    Q.push({ii,ji});
    b[ii][ji] = 1;

    while(!Q.empty())
    {
        int i = Q.front().first;
        int j = Q.front().second;

        for(int k = 0; k < 4; k++)
        {
            int iv = i + di[k];
            int jv = j + dj[k];
            if(ok(iv,jv) && b[iv][jv] == 0)
            {
                Q.push({iv,jv});
                b[iv][jv] = b[i][j] + 1;
            }
        }
        Q.pop();
    }

    fout << b[id][jd] << '\n';

    afisare(id,jd);

    /*for(int i = 1; i <= n; i++)
    {
        for(int j = 1; j <= m; j++)
            fout << b[i][j] << " ";
        fout << endl;
    }*/

    fin.close();
    fout.close();
    return 0;
}
