#include<fstream>

using namespace std;

ifstream fin("b.in");
ofstream fout("b.out");

int n, m, f[1001];

int main()
{
    fin >> n >> m;
    int a, b;
    for(int i = 1; i <= m; i++)
    {
        fin >> a >> b;
        f[a]++;
        f[b]++;
    }

    bool ok = true;
    for(int i = 0; i < n-1 && ok == true; i++)
        if(f[i] != f[i+1])
            ok = false;

    if(ok == false)
        fout << 0;
    else
        fout << 1;

    fin.close();
    fout.close();
    return 0;
}


