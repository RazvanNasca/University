#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include "mpi.h"

using namespace std;
using namespace std::chrono;

void insert(int number[], int n, int element)
{
	for (int i = n; i > 0; i--)
	{
		number[i] = number[i - 1];
	}
	number[0] = element;
}

int main(int argc, char** argv)
{
	int rc = MPI_Init(NULL, NULL);
	if (rc != MPI_SUCCESS)
		MPI_Abort(MPI_COMM_WORLD, rc);

	int p, rank;
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Status status;

	const int N1 = 18, N2 = 18, N = max(N1, N2);
	int numar1[N], numar2[N], numar3[N + 1], n = N, start, end;

	if (rank == 0)
	{
		ifstream fin1("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta1\\Varianta1\\Varianta1\\numar1.txt");
		ifstream fin2("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta1\\Varianta1\\Varianta1\\numar2.txt");

		auto startTime = high_resolution_clock::now();

		int chunk = n / p, rest = n % p;
		start = 0;

		for (int i = 1; i < p; i++)
		{
			if (rest > 0)
			{
				end = start + chunk + 1;
				rest--;
			}
			else
				end = start + chunk;

			int digit = 0;
			if (end <= N1)
				for (int i = start; i < end; i++)
				{
					fin1 >> digit;
					insert(numar1, n, digit);
				}
			else {
				for (int i = start; i < N1; i++)
				{
					fin1 >> digit;
					insert(numar1, n, digit);
				}
				for (int i = N1; i < end; i++)
					numar1[i] = 0;
			}

			if (end <= N2)
				for (int i = start; i < end; i++)
				{
					fin2 >> digit;
					insert(numar2, n, digit);
				}
			else {
				for (int i = start; i < N2; i++)
				{
					fin2 >> digit;
					insert(numar1, n, digit);
				}
				for (int i = N2; i < end; i++)
					numar2[i] = 0;
			}

			MPI_Send(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
			MPI_Send(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD);

			MPI_Send(numar1 + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD);
			MPI_Send(numar2 + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD);

			start = end;
		}

		for (int i = 1; i < p; i++)
		{
			MPI_Recv(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			MPI_Recv(numar3 + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
		}

		MPI_Recv(&n, 1, MPI_INT, p - 1, 0, MPI_COMM_WORLD, &status);

		ofstream fout("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta1\\Varianta1\\Varianta1\\numar3.txt");

		for (int i = n; i >= 0; i--) 
			fout << numar3[i];

		auto endTime = high_resolution_clock::now();

		cout << duration<double, milli>(endTime - startTime).count();
	}
	else
	{
		int carry = 0;
		if (rank != 1)
			MPI_Recv(&carry, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);

		MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		MPI_Recv(numar1 + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(numar2 + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		// suma cif
		for (int i = start; i < end; i++)
		{
			int res = numar1[i] + numar2[i] + carry;
			numar3[i] = res % 10;
			carry = res / 10;
		}

		if (rank == p - 1)
			if (carry != 0)
			{
				numar3[N - 1] = carry;
				end++;
			}
			else
				n--;

		// transfer carry
		if (rank != p - 1)
			MPI_Send(&carry, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);

		MPI_Send(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
		MPI_Send(numar3 + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD);

		if (rank == p - 1)
			MPI_Send(&n, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}

	MPI_Finalize();

	return 0;
}