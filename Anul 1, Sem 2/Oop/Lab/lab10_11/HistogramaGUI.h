#pragma once
#include <qwidget.h>
#include <qpainter.h>
#include <qcolor.h>
#include <random>

class HistogramGUI : public QWidget, public Observer {

	Ret& ret;

public:
	
	HistogramGUI(Ret& ret) : ret{ ret } {
	
		this->setStyleSheet("background-image: url(fundalProductGUI.jpg)");
		ret.addObserver(this);
	
	}

	void update() override {
		repaint();
	}

	void paintEvent(QPaintEvent* ev) override
	{
		QPainter p {this};


		for (auto u : ret.getAllReteta())
		{
			int x = rand() % 2000;
			int y = rand() % 1000;
			p.fillRect(x, y, 50, 50, QColor(rand() % 256, rand() % 256, rand() % 256));
		}

	}

};