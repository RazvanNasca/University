#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_SimulareaSimularii.h"

class SimulareaSimularii : public QMainWindow
{
    Q_OBJECT

public:
    SimulareaSimularii(QWidget *parent = Q_NULLPTR);

private:
    Ui::SimulareaSimulariiClass ui;
};
