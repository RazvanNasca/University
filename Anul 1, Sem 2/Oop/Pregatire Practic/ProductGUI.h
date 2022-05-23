#pragma once
#include <qwidget.h>
#include <QApplication.h>
#include <qpushbutton.h>
#include <qboxlayout.h>
#include <qformlayout.h>
#include <qlistwidget.h>
#include <qlineedit.h>
#include <qmessagebox.h>
#include "service.h"

using namespace std;

class ProductGUI: public QWidget {

public:
	ProductGUI(Service& s) : s{ s } {
		initGUI();
		loadData();
		initConnect();
		
	}

private:

	Service& s;

	/// unde afiseaza
	QListWidget* lst = new QListWidget;

	///butoane
	QPushButton* btnAdauga = new QPushButton;
	QPushButton* btnModifica = new QPushButton;

	///campurile citite
	QLineEdit* txtNume = new QLineEdit;
	QLineEdit* txtGen = new QLineEdit;
	QLineEdit* txtAn = new QLineEdit;
	QLineEdit* txtEp = new QLineEdit;
	QLineEdit* txtEpV = new QLineEdit;

	void initGUI()
	{
		QHBoxLayout* ly = new QHBoxLayout;
		setLayout(ly);

	}

	void loadData()
	{
		lst->clear();
		auto rez = s.getAll();

		for (auto serial : rez)
		{
			lst->addItem(QString::fromStdString(serial.getNume()));
			lst->addItem(QString::number(serial.getNrEp()));
			lst->addItem(QString::number(serial.getNrEpV()));
		}
	}

	void initConnect()
	{
		QObject::connect(btnAdauga, &QPushButton::clicked, [&]() {
			
			auto nume = txtNume->text();
			auto gen = txtGen->text();
			auto an = txtAn->text();
			auto ep = txtEp->text();
			auto epv = txtEpV->text();

			try {
				s.store(nume.toStdString(), gen.toStdString(), an.toInt(), ep.toInt(), epv.toInt());
			}
			catch (RepoException& e){
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMesaj()));
			}
			catch (ValidException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMesaj()));
			}

			loadData();

			});
	}

};
