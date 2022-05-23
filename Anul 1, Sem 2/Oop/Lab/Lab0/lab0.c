#include<stdio.h>

int main()
{
    int n;
    float suma = 0, a;

    printf("Introduceti un numar: ");
    scanf("%d", &n);

    int i;
    for(i = 1; i <= n; i++)
    {
        scanf("%f", &a);
        suma += a;
    }

    printf("%f", suma);


}
