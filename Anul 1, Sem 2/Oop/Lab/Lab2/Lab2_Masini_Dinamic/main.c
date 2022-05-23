#define _CRTDBG_MAP_ALLOC
#include<stdlib.h>
#include<crtdbg.h>
#include<stdio.h>
#include "ui.h"
#include "teste.h"

int main()
{
	teste();
	//interfata();

	_CrtDumpMemoryLeaks();
	return 0;
}