#include "TaskGUI.h"

QVBoxLayout* TaskGUI::boxesLayout(vector<string> p, int nr)
{
	QVBoxLayout* toate = new QVBoxLayout;
	for (int i = 0; i < nr; i++)
	{
		QCheckBox* prog = new QCheckBox;
		prog->setText(QString::fromStdString(p[i]));
		toate->addWidget(prog);
	}

	return toate;
}

void TaskGUI::initGUI()
{
	QHBoxLayout* mainLayout = new QHBoxLayout;
	setLayout(mainLayout);

	QFormLayout* formLayout = new QFormLayout;
	formLayout->addRow("Id: ", txtID);
	formLayout->addRow("Descriere: ", txtDescriere);
	formLayout->addRow("Programator: ", txtProgramator);
	formLayout->addRow("Stare: ", txtStare);

	QHBoxLayout* layoutButoane = new QHBoxLayout;
	layoutButoane->addWidget(btnAdaugare);
	layoutButoane->addWidget(btnStergere);
	layoutButoane->addWidget(btnExit);

	QVBoxLayout* layoutStanga = new QVBoxLayout;
	QVBoxLayout* layoutDreapta = new QVBoxLayout;

	layoutStanga->addWidget(table);
	layoutDreapta->addLayout(formLayout);
	layoutDreapta->addLayout(layoutButoane);

	QVBoxLayout* boxes = boxesLayout(s.getProg(), s.getProg().size());

	mainLayout->addLayout(layoutStanga);
	mainLayout->addLayout(layoutDreapta);
	mainLayout->addLayout(boxes);

}

void TaskGUI::initConnect()
{
	QObject::connect(btnExit, &QPushButton::clicked, [&]() {

		close();

		});

	QObject::connect(btnAdaugare, &QPushButton::clicked, [&]() {

		auto id = txtID->text().toInt();
		auto descriere = txtDescriere->text().toStdString();
		auto programator = txtProgramator->text().toStdString();
		auto stare = txtStare->text().toStdString();

		try {
			vector <string> p;
			p.clear();
			p.push_back(programator);
			s.adauga(id, descriere, p, stare);
			loadData(s.sorteazaDescriere());
			reloadData();
		}
		catch(RepoException& e){ 
			QMessageBox::warning(nullptr, "Id existent", QString::fromStdString(e.getMessage()));
		}
		catch (ValidException& e) {
			QMessageBox::warning(nullptr, "Date invalide", QString::fromStdString(e.getMessage()));
		}

		});

	QObject::connect(btnStergere, &QPushButton::clicked, [&]() {

		auto programator = txtProgramator->text().toStdString();

		auto rez = s.getTasksProg(programator);

		for (auto it : rez)
			s.sterge(it);

		loadData(s.sorteazaDescriere());
		reloadData();

		});
}

void TaskGUI::loadData(vector<Task> task)
{

	model->setTasks(task);
	model->refresh();

}
