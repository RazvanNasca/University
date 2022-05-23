#include<stdio.h>


int prim(int x)
{
    /// verifica daca un numar este prim
    /// input: un numar real
    /// output: 1 daca numarul este prim, 0 daca nu e prim

    if (x < 2)
        return 0;
    if (x == 2)
        return 1;
    if (x % 2 == 0)
        return 0;
    int i;
    for (i = 3; i * i <= x; i += 2)
        if (i % x == 0)
            return 0;
    return 1;
}

void genereaza()
{
    /// afiseaza numerele prime mai mici decat un numar dat

    int n;
    float suma = 0, a;

    printf("Introduceti un numar natural: ");
    scanf_s("%d", &n);

    int i;
    for (i = 2; i < n; i++)
        if (prim(i) == 1)
            printf("%d ", i);
}

void descompune(int n)
{
    /// descompune un numar dat in factori primi

    int i;
    for(i = 2; i <= n; i++)
        if (prim(i) == 1)
        {
            int p = 0; ///puterea
            while (n % i == 0)
            {
                p++;
                n /= i;
            }

            if (p != 0)
                printf("%d^%d ", i, p);
        }

}

int main()
{
    while (1)
    {
        int cmd = 0;
        printf("1 - Genereaza toate numerele prime mai mici decat un numar natura n");
        printf("\n");
        printf("2 - Descompune in factori primi un numar natura n");
        printf("\n");
        printf("3 - Exit");
        printf("\n");
        scanf_s("%d", &cmd);


        if (cmd == 1)
            genereaza();
        else
            if (cmd == 2)
            {
                int n;
                printf("Introduceti un numar natural :");
                scanf_s("%d", &n);
                descompune(n);
            }
            else
                if (cmd == 3)
                    return 0;
                else
                    printf("Comanda gresita!");
        printf("\n");
    }

    return 0;
}
