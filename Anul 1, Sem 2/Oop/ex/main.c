#include<stdio.h>

int cmmdc(int a, int b)
{
	if (b == 0)
		return a;
	return cmmdc(b,a%b);
}

int prim(int x)
{
	if (x < 2)
		return 0;
	if (x == 2)
		return 1;
	if (x % 2 == 0)
		return 0;
	int d;
	for (d = 3; d * d <= x; d += 2)
		if (x % d == 0)
			return 0;
	return 1;
}

int main()
{
	int i, cnt = 1;
	for (i = 3; cnt <= 10 && i <= 500; i++)
	{
		int ok = 1;
		int j;
		for (j = 2; j < i && ok == 1; j++)
			if (cmmdc(i, j) == 1)
				if(prim(j) == 0)
					ok = 0;

		if (ok == 1)
		{
			printf("%d ", j);
			cnt++;
		}
	}

	return 0;
}