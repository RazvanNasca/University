#include <fstream>
int main(int argc, char * argv[])
{
 //calea fisierului de intrare este data in argv[1]
 std::ifstream fin(argv[1]);
 //calea fisierului de iesire este data in argv[2]
 std::ofstream fout(argv[2]);
 //implementarea problemei
 //...
 fin.close();
 fout.close();
 return 0;
}

