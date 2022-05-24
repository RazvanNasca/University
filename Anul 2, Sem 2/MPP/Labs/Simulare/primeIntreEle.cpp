#include <iostream>
#include <fstream>
using namespace std;

int cmmdc (int a, int b)
{
  if(b==0) return a;
  return cmmdc(b,a%b);
}

int main()
{
    int a;
    int b;
    cin >> a >> b;

    if(cmmdc(a,b) == 1)
        cout << "Prime intre ele!";
    else
        cout << "Nu sunt prime intre ele!";

    return 0;
}
