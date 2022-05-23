#pragma once
#include"service.h"

class Ui
{
	Service& s;
public:
	Ui(Service& s)noexcept :s{ s } {}
	void startUi();
};