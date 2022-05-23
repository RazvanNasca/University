 #include<fstream>

 using namespace std;

 ifstream fin("a.in");
 ofstream fout("a.out");

 int n, m, f[1001];

 int main()
 {
     fin >> n >> m;
     int a, b;
     for(int i = 1; i <= m; i++)
     {
         fin >> a >> b;
         f[a] = f[b] = 1;
     }

     for(int i = 0; i < n; i++)
        if(f[i] == 0)
            fout << i << ' ';

     fin.close();
     fout.close();
     return 0;
 }
