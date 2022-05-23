#include<stdio.h>

int prim(int x)
{
    /// verifica daca un numar este prim
    /// input: un numar real
    /// output: 1 daca numarul este prim, 0 daca nu e prim

    if(x < 2)
        return 0;
    if(x == 2)
        return 1;
    if(x % 2 == 0)
        return 0;
    int i;
    for(i = 3; i*i <= x; i+=2)
        if(i % x == 0)
            return 0;
    return 1;
}

void genereaza()
{
    /// afiseaza numerele prime mai mici decat un numar dat

    int n;
    float suma = 0, a;

    printf("Introduceti un numar natural: ");
    scanf("%d", &n);

    int i;
    for(i = 2; i < n; i++)
        if(prim(i) == 1)
            printf("%d ",i);
}

int main()
{
    while(1)
    {
        int cmd = 0;
        printf("1 - Genereaza toate numerele prime mai mici decat un numar natura n");
        printf("\n");
        printf("2 - Exit");
        printf("\n");
        scanf("%d", &cmd);

        if(cmd == 1)
            genereaza();
        else
            if(cmd == 2)
                return 0;
            else
                printf("Comanda gresita!");
    }

    return 0;
}

