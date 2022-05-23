#pragma once
#include <QtWidgets\QMainWindow>
#include <QtWidgets\QAction>
#include <QtWidgets\QTextEdit>
#include <QtWidgets\QMenu>
#include <QtWidgets\QMenuBar>
#include <QtWidgets\QStatusBar>
#include <QtWidgets\QToolBar>

class Notepad: public QMainWindow{
	Q_OBJECT
private:
	QAction* openAction;
	QAction* saveAction;
	QAction* exitAction;
	QTextEdit* textEdit;
public:
	Notepad() {
		openAction = new QAction(tr("&Load"), this);
		saveAction = new QAction(tr("&Save"), this);
		exitAction = new QAction(tr("E&xit"), this);
		QMenu* fileMenu = menuBar()->addMenu("&File");
		fileMenu->addAction(openAction);
		fileMenu->addAction(saveAction);
		fileMenu->addSeparator();
		fileMenu->addAction(exitAction);

		QToolBar* fileToolBar = addToolBar("&File");
		fileToolBar->addAction(openAction);
		fileToolBar->addAction(saveAction);


		textEdit = new QTextEdit;
		setCentralWidget(textEdit);
		setWindowTitle("Notepad--");
		statusBar()->showMessage("Welcome to Notepad--");

		connect(openAction, SIGNAL(triggered()), this, SLOT(open()));
		connect(saveAction, SIGNAL(triggered()), this, SLOT(save()));
		connect(exitAction, SIGNAL(triggered()), this, SLOT(quit()));
	}

private slots:
	void open();
	void save() {};
	void quit() { close(); };
};