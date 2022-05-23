#include "service.h"
#include "teste.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    Teste test;
    test.run();
    Repo r{ "taskuri.txt" };
    Valid v;
    Service s{ r,v };

    QApplication a(argc, argv);
    return a.exec();
}
