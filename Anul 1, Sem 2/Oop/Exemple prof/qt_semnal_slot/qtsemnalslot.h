#ifndef QTSEMNALSLOT_H
#define QTSEMNALSLOT_H

#include <QtWidgets/QMainWindow>
#include "ui_qtsemnalslot.h"

class QTSemnalSlot : public QMainWindow
{
	Q_OBJECT

public:
	QTSemnalSlot(QWidget *parent = 0);
	~QTSemnalSlot();

private:
	Ui::QTSemnalSlotClass ui;
};

#endif // QTSEMNALSLOT_H
