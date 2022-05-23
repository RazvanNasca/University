#include "qtsemnalslot.h"
#include <QtWidgets/QApplication>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QPushButton>
#include <QtCore/QDebug>
#include <QtWidgets/QSpinBox>
#include <QtWidgets/QSlider>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qtablewidget.h>
#include <QtWidgets/QMessageBox>
#include "Notepad.h"
#include "Excel.h"

QWidget* createButtons(QApplication &a) {
	QWidget* btns = new QWidget;
	QHBoxLayout* btnsL = new QHBoxLayout;
	btns->setLayout(btnsL);
	QPushButton* store = new QPushButton("&Store");
	btnsL->addWidget(store);
	QPushButton* close = new QPushButton("&Close");
	btnsL->addWidget(close);
	//connect the clicked signal from close button to the quit slot (method)
	QObject::connect(close, &QPushButton::clicked, [&a]() {
		QMessageBox::information(nullptr, "Info", "Butonul apasat");		
	});
	QObject::connect(close, &QPushButton::clicked, [&a]() {
		QMessageBox::information(nullptr, "Alytu Info", "Alytu Butonul apasat");
	});


	//QObject::connect(close, &QPushButton::clicked, &a, QApplication::quit);
	//QObject::connect(close, &QPushButton::clicked, [&a]() {
//		a.quit();
	//});
	return btns;
}

QWidget* createSpinner() {
	QWidget* rez = new QWidget;
	QHBoxLayout* ly = new QHBoxLayout;
	rez->setLayout(ly);

	QSpinBox *spAge = new QSpinBox();
	ly->addWidget(spAge);
	QSlider *slAge = new QSlider(Qt::Horizontal);
	ly->addWidget(slAge);
	//Synchronise the spinner and the slider
	//Connect spin box - valueChanged to slider setValue
	QObject::connect(spAge, SIGNAL(valueChanged(int)), slAge, SLOT(setValue(int)));
	//Connect - slider valueChanged to spin box setValue
	//QObject::connect(slAge, SIGNAL(valueChanged(int)), spAge, SLOT(setValue(int)));
	QObject::connect(slAge, &QSlider::valueChanged, [spAge](int val) {
		spAge->setValue(val);}
	);

	return rez;
}

void createListWidget() {
	QWidget* main = new QWidget;
	QHBoxLayout* lay = new QHBoxLayout;
	main->setLayout(lay);
	//se creaza
	QListWidget* lst = new QListWidget;
	
	//se pot adauga elemente
	QListWidgetItem* item = new QListWidgetItem("Bla", lst);

	//informatii suplimentare in item
	item->setBackground(QBrush{ Qt::red, Qt::SolidPattern });
	item->setTextColor(Qt::blue);
	item->setData(Qt::UserRole, QString{ "informatii care nu se vad" });
	item->setCheckState(Qt::Checked);
	item->setIcon(QApplication::style()->standardIcon(QStyle::SP_BrowserReload));

	//se poate configura modul de selectie
	lst->setSelectionMode(QAbstractItemView::SingleSelection);
	//se poate obtine selectia
	auto selItms = lst->selectedItems();
	
	//putem reactiona la semnale
	QObject::connect(lst, &QListWidget::itemSelectionChanged, [lst]() {
		qDebug() << "Selectie modificata!!!!\n" << lst->selectedItems() << "\n";
	});

	lay->addWidget(lst);

	//se creaza
	int nrLinii = 4;
	int nrColoane = 3;
	QTableWidget* tbl = new QTableWidget{ nrLinii,nrColoane };
	

	//se pot adauga elemente
	QTableWidgetItem* cellItem1 = new QTableWidgetItem("Linie1");
	tbl->setItem(0, 0, cellItem1);
	tbl->setItem(0, 1, new QTableWidgetItem("Linie1 coloana2"));

	//informatii suplimentare pentru fiecare celula
	cellItem1->setBackground(QBrush{ Qt::red, Qt::SolidPattern });
	cellItem1->setTextColor(Qt::blue);
	cellItem1->setData(Qt::UserRole, QString{ "informatii care nu se vad" });
	cellItem1->setCheckState(Qt::Unchecked);
	cellItem1->setIcon(QApplication::style()->standardIcon(
		QStyle::SP_ArrowBack));

	//se poate configura modul de selectie
	tbl->setSelectionBehavior(QAbstractItemView::SelectRows);
	tbl->setSelectionMode(QAbstractItemView::SingleSelection);

	//se poate obtine selectia
	auto selTblItms = tbl->selectedItems();

	//putem reactiona la semnale
	QObject::connect(tbl, &QTableWidget::itemSelectionChanged, [tbl]() {
		qDebug() << "Selectie tabel modificata!!!!\n"<<tbl->selectedItems()<<"\n"; 
	});

	lay->addWidget(tbl);

	main->show();

}
int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
	
	//createButtons(a)->show();
	//createSpinner()->show();
	//createListWidget();
	Excel excel;
	excel.show();
	/*Notepad n;
	n.show();*/
	return a.exec();
}
