#pragma once
#include <qwidget.h>
#include <qtablewidget.h>
#include <qmessagebox.h>
#include <qlistwidget.h>
#include <qlineedit.h>
#include <qpushbutton.h>
#include <qformlayout.h>
#include <qtableview.h>
#include <qcheckbox.h>
#include "service.h"
#include "TableModel.h"

class TaskGUI : public QWidget {

private:
	
	Service& s;

	/// tabelul cu task-urile
	QTableView* table = new QTableView;
	TableModel* model;

	/// campurile de text
	QLineEdit* txtID = new QLineEdit;
	QLineEdit* txtDescriere = new QLineEdit;
	QLineEdit* txtProgramator = new QLineEdit;
	QLineEdit* txtStare = new QLineEdit;

	/// butoane
	QPushButton* btnAdaugare = new QPushButton{ "&Adauga" };
	QPushButton* btnExit = new QPushButton{ "&Exit" };
	QPushButton* btnStergere = new QPushButton{ "&Sterge" };

	QVBoxLayout* boxesLayout(vector <string> p, int nr);

	/// initializam interfata
	void initGUI();

	/// facem legatura dintre butoane si semnale
	void initConnect();

	/// incarca task-urile in tabel
	void loadData(vector <Task> tasks);

public:

	TaskGUI(Service& s): s{s}
	{
		model = new TableModel{ s.sorteazaDescriere() };
		table->setModel(model);
		initGUI();
		initConnect();
		loadData(s.sorteazaDescriere());
	}

	void reloadData()
	{
		loadData(s.sorteazaDescriere());
	}

};
