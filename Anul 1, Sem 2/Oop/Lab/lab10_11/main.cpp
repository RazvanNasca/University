#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include "ProductGUI.h"
#include "HistogramaGUI.h"
#include <vector>
#include <string>

using namespace std;

int main(int argc, char* argv[])
{
	RepoFile r{ "meds.txt" };
	//Repo r;
	Ret ret{ r };
	Valid v;
	Service s{ r , v, ret };

	QApplication a(argc, argv);

	ProductGui gui{s, ret};
	gui.show();

	return a.exec();
}
