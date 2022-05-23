#include "Notepad.h"
#include <QtWidgets\QFileDialog>
#include <QtWidgets\QMessageBox>
#include <QtCore\QTextStream>

void Notepad::open()
{
	QString fileName = QFileDialog::getOpenFileName(this, tr("Open File"), "",
		tr("Text Files (*.txt);;C++ Files (*.cpp *.h)"));

	if (fileName != "") {
		QFile file(fileName);
		if (!file.open(QIODevice::ReadOnly)) {
			QMessageBox::critical(this, tr("Error"), tr("Could not open file"));
			return;
		}
		QTextStream in(&file);
		textEdit->setText(in.readAll());
		file.close();
	}
}