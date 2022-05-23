#include <QtWidgets/QApplication>
#include "TaskGUI.h"
#include "teste.h"

int main(int argc, char *argv[])
{
    Teste t;
    t.run();

    Repo r("taskuri.txt");
    Valid v;

    Service s{ r,v };

    QApplication a(argc, argv);

    TaskGUI g{ s };
    g.show();

    return a.exec();
}
