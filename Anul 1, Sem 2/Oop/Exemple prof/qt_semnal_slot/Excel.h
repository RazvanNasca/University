#pragma once

#include <QtWidgets\QMainWindow>
#include <QtWidgets\QStatusBar>
#include <QtWidgets\QTableWidget>

class Excel :public QMainWindow {
public:
	Excel() {
		setWindowTitle("MyExcel");
		statusBar()->showMessage("Welcome....");
		QTableWidget* middle = new QTableWidget(10, 10, this);
		setCentralWidget(middle);
	}
};