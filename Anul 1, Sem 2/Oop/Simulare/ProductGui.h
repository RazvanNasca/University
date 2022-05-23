#pragma once
#include "service.h"
#include <qwidget.h>
#include <qlistwidget.h>
#include <qpushbutton.h>
#include <qboxlayout.h>
#include <qmessagebox.h>
#include <qspinbox.h>


class ProductGui : public QWidget {

public:
	ProductGui(Service& s) : s{ s } {

		initGui();
		loadData();
		initConnect();
	}

private:

	Service& s;

	// afisarea
	QListWidget* lst = new QListWidget;

	///butoanele
	QPushButton* btnAdauga = new QPushButton{"&Adauga Random"};
	QPushButton* btnSterge = new QPushButton{"&Goleste"};
	QPushButton* btnFiltrare = new QPushButton{"&Filtare"};
	QPushButton* btnSortarePret = new QPushButton{"&Sortare dupa pret"};
	QPushButton* btnNesortat = new QPushButton{"&Nesortat"};
	QSpinBox* spinbox = new QSpinBox;


	void initGui()
	{
		QHBoxLayout* ly = new QHBoxLayout{};	
		QVBoxLayout* lyBtn = new QVBoxLayout{};	
		setLayout(ly);

		ly->addWidget(lst);
		lyBtn->addWidget(spinbox);
		lyBtn->addWidget(btnAdauga);
		lyBtn->addWidget(btnSterge);
		lyBtn->addWidget(btnFiltrare);
		lyBtn->addWidget(btnNesortat);
		ly->addLayout(lyBtn);
	}

	void loadData()
	{
		lst->clear();
		auto rez = s.getAll();

		if (rez.size() < 10)
			QMessageBox::information(nullptr, "Date invalide!", "Prea putine concerte");

		for (auto it : rez)
			lst->addItem(QString::fromStdString(it.getNume()));
	}

	void initConnect()
	{
		QObject::connect(btnAdauga, &QPushButton::clicked, [&]() {
			
			int numar = spinbox->value();
			s.adaugaRandom(numar);
			loadData();
			
			});

		QObject::connect(btnSterge, &QPushButton::clicked, [&]() {

			lst->clear();

			});

		QObject::connect(btnNesortat, &QPushButton::clicked, [&]() {

			loadData();

			});

		QObject::connect(btnFiltrare, &QPushButton::clicked, [&]() {

			int numar = spinbox->value();
			auto rez = s.getAll();

			lst->clear();
			
			for (auto it : rez)
			{
				QListWidgetItem* concert = new QListWidgetItem(QString::fromStdString(it.getNume()));
				if (it.getPret() < numar)
					concert->setBackgroundColor(Qt::green);

				lst->addItem(concert);
			}

			});
	}

};
