/*Codare Huffman, Nasca Razvan, 215/1*/
#include<iostream>
#include<fstream>
#include<queue>
#include<cstring>
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

    fin.getline(sir,10002);         /// citim
    lung = strlen(sir);

    for(int i = 0; i < lung; i++)   /// facem frecventele
        fr[sir[i]]++;

    cnt = fr.size();

    fout << cnt << '\n';

    for(auto it:fr)                 /// parcurgem dictionarul de frecvente
    {
        char key = it.first;
        int value = it.second;

        nod* z = new nod;           /// creem un nod nou
        z->drept = nullptr;
        z->stang = nullptr;
        z->frecv = value;
        z->val = key;

        fout << key << " " << value << '\n';

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
        z->val = min(x->val,y->val);
        z->drept = y;
        z->stang = x;

        Q.push(z);
        cnt--;
    }

    dfs(Q.top(), "", true, true);       /// parcurgem arborele
    string result = "";                 /// construim rezultatul
    for (char c: sir)
        result += encoding[c];

    fout << result;

    destroy(Q.top());                   /// eliberam memeoria alocata

    fin.close();
    fout.close();
    return 0;
}
