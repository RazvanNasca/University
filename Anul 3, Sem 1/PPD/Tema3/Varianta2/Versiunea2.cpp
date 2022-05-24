#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include "mpi.h"

using namespace std;
using namespace std::chrono;

int main(int argc, char** argv)
{
	int rc = MPI_Init(NULL, NULL);
	if (rc != MPI_SUCCESS)
	{
		cout << "Error initializing MPI!";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	int p, rank;
	MPI_Comm_size(MPI_COMM_WORLD, &p);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Status status;

	const int N1 = 18, N2 = 18;
	int N = max(N1, N2);
	int n = N;
	while (n % p != 0)
		n++;

	int* numar1 = new int[n]; int* numar2 = new int[n]; int* numar3 = new int[n + 1];
	auto startTime = chrono::high_resolution_clock::now();

	if (rank == 0)
	{
		ifstream fin1("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta2\\Versiunea2\\Versiunea2\\numar1.txt");
		ifstream fin2("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta2\\Versiunea2\\Versiunea2\\numar2.txt");

		int digit;
		for (int i = 0; i < N1; i++)
		{
			fin1 >> digit;
			for (int i = n; i > 0; i--)
				numar1[i] = numar1[i - 1];
			numar1[0] = digit;
		}
		if (N1 < n)
			for (int i = N1; i < n; i++)
				numar1[i] = 0;

		for (int i = 0; i < N2; i++)
		{
			fin2 >> digit;
			for (int i = n; i > 0; i--)
				numar2[i] = numar2[i - 1];
			numar2[0] = digit;
		}
		if (N2 < n)
			for (int i = N2; i < n; i++)
				numar2[i] = 0;
	}

	int* numar1_local = new int[n / p];
	int* numar2_local = new int[n / p];
	int* numar3_local = new int[n / p];

	MPI_Scatter(numar1, n / p, MPI_INT, numar1_local, n / p, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Scatter(numar2, n / p, MPI_INT, numar2_local, n / p, MPI_INT, 0, MPI_COMM_WORLD);

	int carry = 0;
	for (int i = 0; i < n / p; i++)
	{
		int res = numar1_local[i] + numar2_local[i] + carry;
		numar3_local[i] = res % 10;
		carry = res / 10;
	}

	if (rank < p - 1)
		MPI_Send(&carry, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
	else
		MPI_Send(&carry, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);

	if (rank > 0)
		MPI_Recv(&carry, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);
	else
		MPI_Recv(&carry, 1, MPI_INT, p - 1, 0, MPI_COMM_WORLD, &status);

	if (rank > 0)
	{
		int i = 0;
		while (carry == 1 && i < n / p)
		{
			int carryRes = numar3_local[i] + carry;
			carryRes /= 10;
			numar3_local[i] = carryRes % 10;
			i++;
		}
	}

	MPI_Gather(numar3_local, n / p, MPI_INT, numar3, n / p, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0)
	{
		if (carry != 0)
		{
			numar3[N] = carry;
			N++;
		}

		ofstream file("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema3\\Varianta2\\Versiunea2\\Versiunea2\\numar3.txt");

		for (int i = n - 1; i >= 0; i--)
			file << numar3[i];

		auto endTime = chrono::high_resolution_clock::now();

		cout << chrono::duration<double, milli>(endTime - startTime).count() << endl;
	}

	MPI_Finalize();

	return 0;
}