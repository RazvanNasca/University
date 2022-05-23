#include <QtWidgets/QApplication>
#include "service.h"
#include "valid.h"
#include "ProductGUI.h"


int main(int argc, char *argv[])
{
    Repo r{ "seriale.txt" };
    Valid v;
    Service s{ r, v };

    QApplication a(argc, argv);

    ProductGUI gui{ s };
    gui.show();
   
    return a.exec();
}
