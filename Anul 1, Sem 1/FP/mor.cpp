#include<iostream>
using namespace std;

long long n;
int b;

int main()
{
    cin >> n >> b;
    int maxim = -1;

    while(n != 0)
    {
        int uc = n % b;
        if(maxim < uc)
            maxim = uc;
        n /= b;
    }

    cout << maxim;

    return 0;
}
