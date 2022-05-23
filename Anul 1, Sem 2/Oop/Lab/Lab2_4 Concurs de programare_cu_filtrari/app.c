#include <stdio.h>
#include <crtdbg.h>

#include "tests.h"
#include "ui.h"


int main()
{
	testAll();
	//run();
	_CrtDumpMemoryLeaks();
	return 0;
}