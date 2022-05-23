#pragma once
#include <QtWidgets/qwidget.h>
#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QGridLayout>
#include <QTabWidget>
#include <qradiobutton.h>
#include <qdebug.h>
#include <qmessagebox.h>
#include <vector>
#include <string>
#include "service.h"
#include "medGUI.h"
#include "HistogramaGUI.h"


using namespace std;

class ProductGui: public QWidget, public Observer {
	
public:
	ProductGui(Service& s, Ret& ret) : s{ s }, ret{ ret } {
		initGui();
		loadData();
		initConnect();
		adaugaButoane(s.getAll());
	}
private:

	Service& s;
	Ret& ret;
	vector < QPushButton*> butoane;

	/// casuta cu produsele
	QListWidget* lst = new QListWidget;

	/// butoanele
	QPushButton* btnExit = new QPushButton{ "&Exit" };
	QPushButton* btnAfiseaza = new QPushButton{ "&Afiseaza" };
	QPushButton* btnAdauga = new QPushButton{ "&Adauga" };
	QPushButton* btnActualizeaza = new QPushButton{ "&Actualizeaza" };
	QPushButton* btnSterge = new QPushButton{ "&Sterge" };
	QPushButton* btnUndo = new QPushButton{ "&Undo" };
	QPushButton* btnRetetaRead = new QPushButton{ "&Reteta Read" };
	QPushButton* btnRetetaCRUD = new QPushButton{ "&Reteta CRUD" };
	QPushButton* btnFiltrarePret = new QPushButton{ "&Filtreaza Pret" };
	QPushButton* btnFiltrareSubst = new QPushButton{ "&Filtreaza Substanta" };
	QPushButton* btnSorteazaDen = new QPushButton{ "&SortDen" };
	QPushButton* btnSorteazaProd = new QPushButton{ "&SortProd" };
	QPushButton* btnSorteazaPret = new QPushButton{ "&SortPret" }; 
	QPushButton* btnAdaugaReteta = new QPushButton{ "&Adauga" };
	QPushButton* btnRandomReteta = new QPushButton{ "&Adauga Random" };
	QPushButton* btnStergeReteta = new QPushButton{ "&Golire" };
	QVBoxLayout* lyBtnDyn = new QVBoxLayout;
	QWidget* btnDyn = new QWidget;

	/// campurile de citire
	QLineEdit* txtDenumire = new QLineEdit;
	QLineEdit* txtProducator = new QLineEdit;
	QLineEdit* txtPret = new QLineEdit;
	QLineEdit* txtSubstantaActiva = new QLineEdit;
	QLineEdit* txtadaugarirandom = new QLineEdit;

	void adaugaButoane(vector<Medicament> meds)
	{
		stergeButoane();

		for (auto med : meds)
		{
			auto btn = new QPushButton{ QString::fromStdString(med.getDenumire()) };
			butoane.push_back(btn);
			///lyBtnDyn->addWidget(btn);
			QObject::connect(btn, &QPushButton::clicked, [this,btn,med]() {
				
				try {
					s.stergere(med.getDenumire(), med.getProducator(), med.getPret(), med.getSubstanta_activa());
				}
				catch (RepoException & e) {
					QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
				}
				catch (ValidException & e) {
					QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
				}


				adaugaButoane(s.getAll());
				loadData();
			});

			for(auto buton:butoane)
				lyBtnDyn->addWidget(buton);
		}
	}

	void stergeButoane()
	{
		for (auto buton : butoane)
			delete buton;
		butoane.clear();
	}

	void update() override
	{
		loadData();
	}

	void initConnect()
	{
		
		/// actiunea de adaugare in reteta
		QObject::connect(btnAdaugaReteta, &QPushButton::clicked, [&]() {

			auto nume = txtDenumire->text();
			auto prod = txtProducator->text();
			auto pret = txtPret->text();
			auto subst = txtSubstantaActiva->text();
			qDebug() << "Nume" << nume << "Prod" << prod << " Pret" << pret << "Subst" << subst; /// afisare in visual output  ~cout

			try {
				s.adaugaReteta(nume.toLocal8Bit().constData(), prod.toLocal8Bit().constData(), pret.toDouble(), subst.toInt());
			}
			catch (RepoException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}
			catch (ValidException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}

			/// reincarca lista
			loadData();
			});

		/// actiunea de golire a retetei
		QObject::connect(btnStergeReteta, &QPushButton::clicked, [&]() {

			s.clearAll();

			/// reincarca lista
			loadData();
			});

		/// actiunea de adaugare random
		QObject::connect(btnRandomReteta, &QPushButton::clicked, [&]() {

			auto nr_rand = txtadaugarirandom->text();

			try {
				s.adaugaRandom(nr_rand.toInt());
			}
			catch (RepoException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}
			catch (ValidException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}

			/// reincarca lista
			loadData();
			});


		/// actiunea de exit
		QObject::connect(btnExit, &QPushButton::clicked, [&]() {

			 QMessageBox::information(nullptr,"Info","Exit buton apasat!!!");
			 close();
			});

		/// actiunea de adaugare
		QObject::connect(btnAdauga, &QPushButton::clicked, [&]() {

			auto nume = txtDenumire->text();
			auto prod = txtProducator->text();
			auto pret = txtPret->text();
			auto subst = txtSubstantaActiva->text();
			qDebug() << "Nume" << nume << "Prod" << prod << " Pret" << pret << "Subst" << subst; /// afisare in visual output  ~cout
			
			try{
				s.adauga(nume.toLocal8Bit().constData(), prod.toLocal8Bit().constData(), pret.toDouble(), subst.toInt());
			}
				catch (RepoException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString( e.getMessage()));
			}
			catch (ValidException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}
	
			/// reincarca lista
			loadData();
			//stergeButoane();
			adaugaButoane(s.getAll());
			});

		/// actiunea de actualizare
		QObject::connect(btnActualizeaza, &QPushButton::clicked, [&]() {

			auto nume = txtDenumire->text();
			auto prod = txtProducator->text();
			auto pret = txtPret->text();
			auto subst = txtSubstantaActiva->text();
			qDebug() << "Nume" << nume << "Prod" << prod << " Pret" << pret << "Subst" << subst; /// afisare in visual output  ~cout

			try {
				s.actualizare(nume.toLocal8Bit().constData(), prod.toLocal8Bit().constData(), pret.toDouble(), subst.toInt());
			}
			catch (RepoException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}
			catch (ValidException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}

			/// reincarca lista
			adaugaButoane(s.getAll());
			loadData();
			});

		/// actiunea de stergere
		QObject::connect(btnSterge, &QPushButton::clicked, [&]() {

			/// sterg doar dupa campul DENUMIRE (cheia din map) 
			auto nume = txtDenumire->text();
			auto prod = QString::fromStdString("Mirel");
			auto pret = QString::fromStdString("12.2");
			auto subst = QString::fromStdString("12");
			qDebug() << "Nume" << nume << "Prod" << prod; /// afisare in visual output  ~cout

			try {
				s.stergere(nume.toLocal8Bit().constData(), prod.toLocal8Bit().constData(), pret.toDouble(), subst.toInt());
			}
			catch (RepoException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}
			catch (ValidException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}

			/// reincarca lista
			adaugaButoane(s.getAll());
			loadData();
			});

		/// actiunea de undo
		QObject::connect(btnUndo, &QPushButton::clicked, [&]() {

			try {
				s.undo();
			}
			catch (RepoException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}
			catch (ValidException & e) {
				QMessageBox::information(nullptr, "Date invalide!", QString::fromStdString(e.getMessage()));
			}

			/// reincarca lista
			adaugaButoane(s.getAll());
			loadData();
			});

		QObject::connect(btnAfiseaza, &QPushButton::clicked, [&]() {

			loadData();

			});

		QObject::connect(btnFiltrarePret, &QPushButton::clicked, [&]() {

			reloadData(s.filtreazaPret(txtPret->text().toDouble()));
			adaugaButoane(s.filtreazaPret(txtPret->text().toDouble()));

			});

		QObject::connect(btnFiltrareSubst, &QPushButton::clicked, [&]() {

			reloadData(s.filtreazaSubst(txtSubstantaActiva->text().toInt()));
			adaugaButoane(s.filtreazaSubst(txtSubstantaActiva->text().toInt()));

			});

		QObject::connect(btnSorteazaDen, &QPushButton::clicked, [&]() {

			reloadData(s.sorteazaDenumire());
			adaugaButoane(s.sorteazaDenumire());
			});

		QObject::connect(btnSorteazaProd, &QPushButton::clicked, [&]() {

			reloadData(s.sorteazaProducator());
			adaugaButoane(s.sorteazaProducator());
			});

		QObject::connect(btnSorteazaPret, &QPushButton::clicked, [&]() {

			reloadData(s.sorteazaPretSubst());
			adaugaButoane(s.sorteazaPretSubst());
			});

		QObject::connect(btnRetetaRead, &QPushButton::clicked, [&]() {

			HistogramGUI* hg = new HistogramGUI{ ret };
			hg->show();

			});

		QObject::connect(btnRetetaCRUD, &QPushButton::clicked, [&]() {

			RetetaCrud* cos = new RetetaCrud{ s,ret };
			cos->show();

			});

		QObject::connect(lst, &QListWidget::itemSelectionChanged, [&]() {

			auto sel = lst->selectedItems(); /// lista cu elementele selectate
			if (sel.isEmpty())
			{
				txtDenumire->setText("");
				txtProducator->setText("");
				txtPret->setText("");
				txtSubstantaActiva->setText("");
			}
			else
			{
				auto selItem = sel.at(0);
				auto den = selItem->text();
				txtDenumire->setText(den);

				auto meds = s.getAll();
				for(auto med : meds)
					if (med.getDenumire() == den.toStdString())
					{
						txtProducator->setText(QString::fromStdString(med.getProducator()));
						txtPret->setText(QString::number((med.getPret())));
						txtSubstantaActiva->setText(QString::number((med.getSubstanta_activa())));
					}
			}

			});

	}

	void reloadData(vector<Medicament> rez)
	{
		lst->clear();
		///int cnt = 1;

		if(rez.size() == 0)
			lst->addItem(QString::fromStdString("Nu exista asemenea produse!"));

		for (const auto& numeProd : rez)
		{
			lst->addItem(QString::fromStdString(numeProd.getDenumire()));
		
		}
	}

	void loadData()
	{
		lst->clear(); /// curatam lista inainte de fiecare reincarcare
		vector<Medicament> allProds = s.getAll();

		for (const auto& numeProd : allProds)
		{
			lst->addItem(QString::fromStdString(numeProd.getDenumire()));
		}
			
	}

	void initGui() 
	{
		this->setStyleSheet("background-image: url(fundalProductGUI.jpg)");
		this->setWindowIcon(QApplication::style()->standardIcon(QStyle::SP_ToolBarHorizontalExtensionButton));
		this->setWindowTitle("Medicamente");

		QGridLayout* grid = new QGridLayout;
		QVBoxLayout* stangLy = new QVBoxLayout;
		QHBoxLayout* lyBtnD = new QHBoxLayout;
		QHBoxLayout* lySort = new QHBoxLayout;
		QHBoxLayout* lyReteta = new QHBoxLayout;
		QHBoxLayout* lyRetetaNou = new QHBoxLayout;

		QHBoxLayout* ly = new QHBoxLayout{};	/// main layout in video
		setLayout(ly);

		lyBtnD->addWidget(btnFiltrarePret);
		lyBtnD->addWidget(btnFiltrareSubst);
		lySort->addWidget(btnSorteazaDen);
		lySort->addWidget(btnSorteazaProd);
		lySort->addWidget(btnSorteazaPret);

		stangLy->addWidget(lst);
		stangLy->addLayout(lyBtnD);
		stangLy->addLayout(lySort);
		ly->addLayout(stangLy);				

		auto dreptLy = new QVBoxLayout;

		/// casuta de adaugare
		auto formLy = new QFormLayout;
		formLy->addRow("Denumire", txtDenumire);
		formLy->addRow("Producator", txtProducator);
		formLy->addRow("Pret", txtPret);
		formLy->addRow("Substanta activa", txtSubstantaActiva);
		formLy->addRow("Numar adaugari random", txtadaugarirandom);
		dreptLy->addLayout(formLy);

		auto lyBtnS = new QHBoxLayout{};
		
		/// butoanele 

		QPalette pal = btnRetetaRead->palette();
		pal.setColor(QPalette::Button, QColor(Qt::blue));
		btnRetetaRead->setAutoFillBackground(true);
		btnRetetaRead->setPalette(pal);
		btnRetetaRead->update();

		QPalette pal2 = btnRetetaCRUD->palette();
		pal2.setColor(QPalette::Button, QColor(Qt::blue));
		btnRetetaCRUD->setAutoFillBackground(true);
		btnRetetaCRUD->setPalette(pal2);
		btnRetetaCRUD->update();

		QPalette pal1 = btnExit->palette();
		pal1.setColor(QPalette::Button, QColor(Qt::red));
		btnExit->setAutoFillBackground(true);
		btnExit->setPalette(pal1);
		btnExit->update();


		dreptLy->addWidget(btnAfiseaza);
		lyBtnS->addWidget(btnAdauga);
		lyBtnS->addWidget(btnActualizeaza);
		lyBtnS->addWidget(btnSterge);
		lyReteta->addWidget(btnRetetaCRUD);
		lyReteta->addWidget(btnRetetaRead);
		lyReteta->addWidget(btnUndo);
		lyRetetaNou->addWidget(btnAdaugaReteta);
		lyRetetaNou->addWidget(btnStergeReteta);
		lyRetetaNou->addWidget(btnRandomReteta);

		/// asamblam layout-urile
		dreptLy->addLayout(lyBtnS);
		dreptLy->addLayout(lyReteta);
		dreptLy->addLayout(lyRetetaNou);
		dreptLy->addWidget(btnExit);
		ly->addLayout(dreptLy);


		btnDyn->setLayout(lyBtnDyn);
		ly->addWidget(btnDyn);


	}

};
