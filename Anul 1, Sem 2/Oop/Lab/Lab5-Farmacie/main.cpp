#include "medicament.h"
#include "teste.h"
#include "ui.h"
#include "validator.h"

int main()
{
	Test t;
	t.runAllTests();

	Repo r;
	Valid v;
	Service s{ r , v };
	Ui ui{ s };
	ui.startUi();


	return 0;
}