/*Codarea Puffer, Nasca Razvan, 215*/

#include<fstream>
#include<vector>

using namespace std;

///ifstream fin("p1.in");
///ofstream fout("p1.out");

struct nod{
    int parinte;
    int nr_fii;
    int vizitat;
};

int n;
vector <nod> graf;
vector <int> pruffer;


void Pruffer()
{

    for(int i = 0; i < n - 1; i++) /// facem n-2 repetari
    {
        int minim = n + 1;      /// cautam frunza de index minim
        for(int j = 0; j < n; j++)
            if(graf[j].nr_fii == 0 && minim > j && graf[j].vizitat == 0)
                minim = j;

        pruffer.push_back(graf[minim].parinte);  /// punem parintele in lista
        graf[minim].vizitat = 1;                /// marcam frunza ca vizitata (o stergem)
        graf[graf[minim].parinte].nr_fii--;     /// micsoram numarul de fii ai parintelui
    }
}


int main(int argc, char* argv[])
{
    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]);

    int x;
    fin >> n;

    graf.resize(n);             /// initializam
    for(int i = 0; i < n; i++)
    {
        graf[i].parinte = 0;
        graf[i].nr_fii = 0;
        graf[i].vizitat = 0;
    }

    for(int i = 0; i < n; i++) /// citim
    {
        fin >> x;
        if(x != -1)
        {
            graf[i].parinte = x;
            graf[x].nr_fii += 1;
        }
    }

    Pruffer();

    fout << pruffer.size() << '\n';
    for(int i = 0; i < pruffer.size(); i++)
        fout << pruffer[i] << " ";

    fin.close();
    fout.close();
    return 0;
}
