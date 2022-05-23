#include <QtWidgets/QApplication>
#include "ProductGui.h"
#include "service.h"

int main(int argc, char *argv[])
{
    Repo r{ "concerte.txt" };
    Service s{ r };

    QApplication a(argc, argv);
    ProductGui g{ s };
    g.show();
    
    return a.exec();
}
