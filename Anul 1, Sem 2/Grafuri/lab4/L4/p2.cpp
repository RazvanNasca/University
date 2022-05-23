/*Decodarea Puffer, Nasca Razvan, 215*/

#include<fstream>
#include<iostream>
#include<vector>

using namespace std;

///ifstream fin("p1.in");
///ofstream fout("p1.out");

struct nod{
    int parinte;
    int nr_fii;
};

int n;
vector <nod> graf;
vector <int> pruffer;


void Pruffer()
{
    for(int i = 0; i < n; i++)
    {
        bool gasit = false;
        for(int j = 0; j <= n && gasit == false; j++)
            if(graf[j].nr_fii == 1)                     /// cauta nodurile de grad 1, un nod pentru fiecare element din lista pruffer
            {
                graf[j].parinte = pruffer[i];           /// la nodul gasit ii asociem un parinte
                graf[pruffer[i]].nr_fii--;              /// scadem numarul de fii pe care poate sa ii mai aiba
                graf[j].nr_fii--;
                gasit = true;
            }
    }

    /// raman 2 noduri, dintre care unul o sa fie radacina
    int nod1 = -1, nod2 = -1;
    for(int i = 0; i <= n; i++)
            if(graf[i].nr_fii == 1)
                if(nod1 == -1)  /// radacina
                    nod1 = i;
                else
                    nod2 = i;   /// celaltal nod ramas adiacent cu radacina

    graf[nod2].parinte = nod1;  /// ii punem ca si parinte radacina
    graf[nod1].parinte = -1;    /// parintele radacinii e -1

}

int main(int argc, char* argv[])
{
    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]);

    int x;
    fin >> n;

    graf.resize(n+3);             /// initializam
    for(int i = 0; i <= n; i++)
    {
        graf[i].parinte = 0;
        graf[i].nr_fii = 1;
    }

    for(int i = 0; i < n; i++) /// citim
    {
        fin >> x;
        pruffer.push_back(x);
        graf[x].nr_fii += 1;
    }

    Pruffer();

    fout << n+1 << '\n';
    for(int i = 0; i <= n; i++)
        fout << graf[i].parinte << " ";

    fin.close();
    fout.close();
    return 0;
}
