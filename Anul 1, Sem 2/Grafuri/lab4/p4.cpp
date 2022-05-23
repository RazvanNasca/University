/*Decodare Huffman, Nasca Razvan, 215/1*/
#include<iostream>
#include<fstream>
#include<cstring>
#include<queue>
#include<map>

using namespace std;

///ifstream fin("p1.in");
///ofstream fout("p1.out");

struct nod{
    int frecv;
    char val;
    nod* stang;
    nod* drept;
};

struct Cmp{     /// fucntia de comparare pentru coada cu prioritati

    bool operator()(nod* x, nod* y)
    {
        if(x->frecv == y->frecv)
            return x->val > y->val;

        return x->frecv > y->frecv;
    }

};

char sir[10002];    /// sirul citit
int lung, cnt;

priority_queue<nod*, vector<nod*>, Cmp> Q;
map<char,int> fr;               /// retinem frecventele caracterelor
map<char, string> encoding;     /// construim mesajul codat
char lit;
int val;
string mesaj;


void dfs(nod* node, string so_far, bool left, bool root) {
    if (node == nullptr)        /// daca ajungem la frunza revenim
        return;

    string now = "";            /// formam sirul de codat
    if (!root) {

        if (left) {
            now = "0";
        }
        else {
            now = "1";
        }
    }
    encoding[node->val] = so_far + now;
    dfs(node->stang, so_far + now, true, false);    /// parcurgem mai deaprte pe dreapta si pe stanga
    dfs(node->drept, so_far + now, false, false);
}

void destroy(nod* nod) {
    if (nod == nullptr)
        return;
    destroy(nod->stang);
    destroy(nod->drept);
    delete nod;
}

int main(int argc, char* argv[])
{
    std::ifstream fin(argv[1]);
    std::ofstream fout(argv[2]);

    char rand[20];
    fin >> lung;
    fin.getline(rand,20);
    for(int i = 0; i < lung; i++)
    {
        fin.getline(rand,20);       /// citire pt spatii albe

        lit = rand[0];              /// extragem litera
        int n = strlen(rand), j = 2;
        val = 0;

        while(j != n)                /// construim frecventa
        {
            int cifra = (int)(rand[j] - '0');
            val *= 10;
            val += cifra;
            j++;
        }

        fr[lit] = val;
        ///cerr << lit << val << "\n";
    }

    fin >> mesaj;

    cnt = lung;

    for(auto it:fr)                 /// parcurgem dictionarul de frecvente
    {
        char key = it.first;
        int value = it.second;

        nod* z = new nod;           /// creem un nod nou
        z->drept = nullptr;
        z->stang = nullptr;
        z->frecv = value;
        z->val = key;

        Q.push(z);                  /// il punem in coada
    }

    while(cnt > 1)                  /// construim arborele binar
    {
        nod* z = new nod;
        nod* x = Q.top();
        Q.pop();
        nod* y = Q.top();
        Q.pop();

        z->frecv = x->frecv + y->frecv;
        z->val = x->val;
        z->drept = y;
        z->stang = x;

        Q.push(z);
        cnt--;
    }

    dfs(Q.top(), "", true, true);

    string cod = "";                        /// decodificam mesajul
    for(int i = 0; i < mesaj.size(); i++)
    {
        cod += mesaj[i];
        bool gasit = false;
        for(auto it:encoding)
            if(it.second == cod && fr[it.first] != 0)
            {
                fout << it.first;
                fr[it.first]--;
                gasit = true;
            }

        if(gasit == true)
            cod = "";
    }

    destroy(Q.top());

    fin.close();
    fout.close();
    return 0;
}
