#include "medicament.h"
#include "teste.h"
#include "ui.h"
#include "validator.h"
#define _CRTDBG_MAP_ALLOC 
#include <stdlib.h>
#include <crtdbg.h> 

int main()
{
	Test t;
	t.runAllTests();
	{
	Repo r;
	Valid v;
	Service s{ r , v };
	Ui ui{ s };
	ui.startUi();
	}
	

	_CrtDumpMemoryLeaks();

	return 0;
}