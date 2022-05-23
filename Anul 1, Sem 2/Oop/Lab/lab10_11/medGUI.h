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
#include "Observer.h"

class RetetaCrud : public QWidget, public Observer {

public:
	RetetaCrud(Service& s, Ret& ret) : s{ s }, ret { ret } {
		initGui();
		loadData();
		initConnect();
	}

private:
	Service& s;
	Ret& ret;

	/// casuta cu produsele
	QListWidget* lst = new QListWidget;

	/// butoanele
	QPushButton* btnExit = new QPushButton{ "&Exit" };
	QPushButton* btnAfiseaza = new QPushButton{ "&Afiseaza" };
	QPushButton* btnAdauga = new QPushButton{ "&Adauga" };
	QPushButton* btnRandom = new QPushButton{ "&Adauga Random" };
	QPushButton* btnSterge = new QPushButton{ "&Golire" };
	QPushButton* btnUndo = new QPushButton{ "&Undo" };
	QPushButton* btnCSV = new QPushButton{ "&Export CSV" };
	QPushButton* btnHTML = new QPushButton{ "&Export HTML" };

	/// campurile de citire
	QLineEdit* txtDenumire = new QLineEdit;
	QLineEdit* txtProducator = new QLineEdit;
	QLineEdit* txtPret = new QLineEdit;
	QLineEdit* txtSubstantaActiva = new QLineEdit;
	QLineEdit* txtadaugarirandom = new QLineEdit;
	QLineEdit* txtnumefisier = new QLineEdit;

	void update() override
	{
		loadData();
	}

	void initConnect()
	{

		ret.addObserver(this);

		/// actiunea de exit
		QObject::connect(btnExit, &QPushButton::clicked, [&]() {

			QMessageBox::information(nullptr, "Info", "Exit buton apasat!!!");
			close();
			});

		/// actiunea de adaugare
		QObject::connect(btnAdauga, &QPushButton::clicked, [&]() {

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

		/// actiunea de stergere
		QObject::connect(btnSterge, &QPushButton::clicked, [&]() {

			s.clearAll();

			/// reincarca lista
			loadData();
			});

		/// actiunea de adaugare
		QObject::connect(btnRandom, &QPushButton::clicked, [&]() {

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

		QObject::connect(btnAfiseaza, &QPushButton::clicked, [&]() {

			loadData();

			});

		QObject::connect(btnCSV, &QPushButton::clicked, [&]() {

			auto fisier = txtnumefisier->text();
			s.exportCSV(fisier.toStdString());
			QMessageBox::information(nullptr, "Export complet!","Fisierul " + fisier + " a fost creat si actualizat!");

			/// reincarca lista
			loadData();
			});

		QObject::connect(btnHTML, &QPushButton::clicked, [&]() {

			auto fisier = txtnumefisier->text();
			s.exportHTML(fisier.toStdString());
			QMessageBox::information(nullptr, "Export complet!", "Fisierul " + fisier + " a fost creat si actualizat!");

			/// reincarca lista
			loadData();
			});

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
			loadData();
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

				auto meds = s.getAllReteta();
				for (auto med : meds)
					if (med.getDenumire() == den.toStdString())
					{
						txtProducator->setText(QString::fromStdString(med.getProducator()));
						txtPret->setText(QString::number((med.getPret())));
						txtSubstantaActiva->setText(QString::number((med.getSubstanta_activa())));
					}
			}

			});

	}

	void loadData()
	{
		lst->clear(); /// curatam lista inainte de fiecare reincarcare
		vector<Medicament> allProds = s.getAllReteta();

		for (const auto& numeProd : allProds)
			lst->addItem(QString::fromStdString(numeProd.getDenumire()));

	}


	void initGui()
	{

		this->setStyleSheet("background-image: url(fundalretetaGUI.jpg)");
		this->setWindowIcon(QApplication::style()->standardIcon(QStyle::SP_ToolBarHorizontalExtensionButton));
		this->setWindowTitle("Reteta CRUD");

		QVBoxLayout* stangLy = new QVBoxLayout;

		QHBoxLayout* ly = new QHBoxLayout{};	/// main layout in video
		setLayout(ly);

		stangLy->addWidget(lst);
		ly->addLayout(stangLy);

		auto dreptLy = new QVBoxLayout;

		/// casuta de adaugare
		auto formLy = new QFormLayout;
		formLy->addRow("Denumire", txtDenumire);
		formLy->addRow("Producator", txtProducator);
		formLy->addRow("Pret", txtPret);
		formLy->addRow("Substanta activa", txtSubstantaActiva);
		formLy->addRow("Numar adaugari random", txtadaugarirandom);
		formLy->addRow("Nume Fisier", txtnumefisier);
		dreptLy->addLayout(formLy);

		auto lyBtnS = new QHBoxLayout{};
		auto lyexport = new QHBoxLayout{};

		/// butoanele 

		///dreptLy->addWidget(btnAfiseaza);
		lyBtnS->addWidget(btnAdauga);
		lyBtnS->addWidget(btnRandom);
		lyBtnS->addWidget(btnSterge);
		lyexport->addWidget(btnCSV);
		lyexport->addWidget(btnHTML);

		/// asamblam layout-urile
		dreptLy->addLayout(lyBtnS);
		dreptLy->addLayout(lyexport);
		dreptLy->addWidget(btnUndo);
		dreptLy->addWidget(btnExit);
		ly->addLayout(dreptLy);

	}

};

class RetetaRead : public QWidget {

public:
	RetetaRead(Service& s) : s{ s } {
		initGui();
		loadData();
		initConnect();
	}

private:
	Service& s;

	/// casuta cu produsele
	QListWidget* lst = new QListWidget;

	/// butoanele
	QPushButton* btnExit = new QPushButton{ "&Exit" };
	QPushButton* btnAfiseaza = new QPushButton{ "&Afiseaza" };
	QPushButton* btnAdauga = new QPushButton{ "&Adauga" };
	QPushButton* btnRandom = new QPushButton{ "&Adauga Random" };
	QPushButton* btnSterge = new QPushButton{ "&Golire" };
	QPushButton* btnUndo = new QPushButton{ "&Undo" };
	QPushButton* btnCSV = new QPushButton{ "&Export CSV" };
	QPushButton* btnHTML = new QPushButton{ "&Export HTML" };

	/// campurile de citire
	QLineEdit* txtDenumire = new QLineEdit;
	QLineEdit* txtProducator = new QLineEdit;
	QLineEdit* txtPret = new QLineEdit;
	QLineEdit* txtSubstantaActiva = new QLineEdit;
	QLineEdit* txtadaugarirandom = new QLineEdit;
	QLineEdit* txtnumefisier = new QLineEdit;

	void initConnect()
	{

		/// actiunea de exit
		QObject::connect(btnExit, &QPushButton::clicked, [&]() {

			QMessageBox::information(nullptr, "Info", "Exit buton apasat!!!");
			close();
			});

		/// actiunea de adaugare in reteta
		QObject::connect(btnAdauga, &QPushButton::clicked, [&]() {

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
		QObject::connect(btnSterge, &QPushButton::clicked, [&]() {

			s.clearAll();

			/// reincarca lista
			loadData();
			});

		/// actiunea de adaugare random
		QObject::connect(btnRandom, &QPushButton::clicked, [&]() {

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

		QObject::connect(btnAfiseaza, &QPushButton::clicked, [&]() {

			loadData();

			});

		QObject::connect(btnCSV, &QPushButton::clicked, [&]() {

			auto fisier = txtnumefisier->text();
			s.exportCSV(fisier.toStdString());
			QMessageBox::information(nullptr, "Export complet!","Fisierul " + fisier + " a fost creat si actualizat!");

			/// reincarca lista
			loadData();
			});

		QObject::connect(btnHTML, &QPushButton::clicked, [&]() {

			auto fisier = txtnumefisier->text();
			s.exportHTML(fisier.toStdString());
			QMessageBox::information(nullptr, "Export complet!", "Fisierul " + fisier + " a fost creat si actualizat!");

			/// reincarca lista
			loadData();
			});

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
			loadData();
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

				auto meds = s.getAllReteta();
				for (auto med : meds)
					if (med.getDenumire() == den.toStdString())
					{
						txtProducator->setText(QString::fromStdString(med.getProducator()));
						txtPret->setText(QString::number((med.getPret())));
						txtSubstantaActiva->setText(QString::number((med.getSubstanta_activa())));
					}
			}

			});

	}

	void loadData()
	{
		lst->clear(); /// curatam lista inainte de fiecare reincarcare
		vector<Medicament> allProds = s.getAllReteta();

		for (const auto& numeProd : allProds)
			lst->addItem(QString::fromStdString(numeProd.getDenumire()));

	}


	void initGui()
	{

		this->setWindowIcon(QApplication::style()->standardIcon(QStyle::SP_ToolBarHorizontalExtensionButton));
		this->setWindowTitle("Reteta View");

		QVBoxLayout* stangLy = new QVBoxLayout;

		QHBoxLayout* ly = new QHBoxLayout{};	/// main layout in video
		setLayout(ly);

		stangLy->addWidget(lst);
		ly->addLayout(stangLy);

		auto dreptLy = new QVBoxLayout;

		/// casuta de adaugare
		auto formLy = new QFormLayout;
		formLy->addRow("Denumire", txtDenumire);
		formLy->addRow("Producator", txtProducator);
		formLy->addRow("Pret", txtPret);
		formLy->addRow("Substanta activa", txtSubstantaActiva);
		formLy->addRow("Numar adaugari random", txtadaugarirandom);
		formLy->addRow("Nume Fisier", txtnumefisier);
		dreptLy->addLayout(formLy);

		auto lyBtnS = new QHBoxLayout{};
		auto lyexport = new QHBoxLayout{};

		/// butoanele 

		///dreptLy->addWidget(btnAfiseaza);
		lyBtnS->addWidget(btnAdauga);
		lyBtnS->addWidget(btnRandom);
		lyBtnS->addWidget(btnSterge);
		lyexport->addWidget(btnCSV);
		lyexport->addWidget(btnHTML);

		/// asamblam layout-urile
		dreptLy->addLayout(lyBtnS);
		dreptLy->addLayout(lyexport);
		dreptLy->addWidget(btnUndo);
		dreptLy->addWidget(btnExit);
		ly->addLayout(dreptLy);

	}
};