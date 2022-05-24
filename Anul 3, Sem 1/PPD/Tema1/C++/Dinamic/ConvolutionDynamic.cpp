#include <iostream>
#include <fstream>
#include <chrono>
#include <vector>
#include <thread>
#define N 10
#define M 10
#define n 3
#define m 3
using namespace std;
using namespace chrono;

ifstream fin("D:\Faculta\Anul3,Sem1\PPD\Tema1\C++\Dinamic\ConvolutionDynamic\ConvolutionDynamic\date.txt");
ofstream fout("D:\Faculta\Anul3,Sem1\PPD\Tema1\C++\Dinamic\ConvolutionDynamic\ConvolutionDynamic\output.txt");

int** matrix;
int** result;
int** kernel;
int p = 1, offset;

void alocation();
void dealocation();
void read();
void write();
void sequential();
void parallel();
int computeConv(int i, int j);
void parallelConv(int start, int end);

int main(int argc, char** argv)
{
    alocation();
    read();

    int p = atoi(argv[1]);
    if (p == 1)
        sequential();
    else
        parallel();

    write();
    dealocation();

    return 0;
}


void alocation()
{
    matrix = new int* [N];
    result = new int* [N];
    kernel = new int* [n];

    for (int i = 0; i < N; i++)
    {
        matrix[i] = new int[M];
        result[i] = new int[M];
    }

    for (int i = 0; i < n; i++)
        kernel[i] = new int[m];
}

void dealocation()
{
    for (int i = 0; i < N; i++)
    {
        delete[] matrix[i];
        delete[] result[i];
    }
    delete[] matrix;
    delete[] result;

    for (int i = 0; i < n; i++)
        delete[] kernel[i];
    delete[] kernel;
}

void read()
{
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            fin >> matrix[i][j];

    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            fin >> kernel[i][j];
}

void write()
{
    for (int i = 0; i < N; i++, fout << endl)
        for (int j = 0; j < M; j++)
            fout << result[i][j] << " ";
}


void sequential()
{
    auto startTime = high_resolution_clock::now();

    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            result[i][j] = computeConv(i, j);

    auto endTime = high_resolution_clock::now();
    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

void parallel()
{
    vector <thread> t;

    int start = 0;
    int end = 0;
    int chunk = M / p;
    int rest = M % p;

    auto startTime = high_resolution_clock::now();

    for (int i = 0; i < p; i++) {
        start = end;
        end = start + chunk + (rest-- > 0);
        thread thr = thread(parallelConv, start, end);
        t.push_back(move(thr));
    }

    for (auto& th : t)
        if (th.joinable())
            th.join();

    auto endTime = high_resolution_clock::now();
    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

void parallelConv(int start, int end)
{
    for (int i = 0; i < N; i++)
        for (int j = start; j < end; j++)
            result[i][j] = computeConv(i, j);
}

int computeConv(int i, int j)
{
    int result = 0;

    for (int ii = 0; ii < n; ii++)
    {
        int iv = i + ii - offset;
        for (int jj = 0; jj < m; jj++)
        {
            int jv = j + jj - offset;

            if (iv < 0)
                iv = 0;
            if (iv >= N)
                iv = N - 1;
            if (jv < 0)
                jv = 0;
            if (jv >= M)
                jv = M - 1;

            result += matrix[iv][jv] * kernel[ii][jj];
        }
    }

    return result;
}