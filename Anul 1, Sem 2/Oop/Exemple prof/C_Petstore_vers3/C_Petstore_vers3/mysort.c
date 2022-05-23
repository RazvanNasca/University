#include "mysort.h"


/**
* Sorteaza in place
* cmpf - relatia dupa care se sorteaza
*/
void sort(MyList* l, FunctieComparare cmpF) {
	int i, j;
	for (i = 0; i < size(l); i++) {
		for (j = i + 1; j < size(l); j++) {
			void* p1 = get(l, i);
			void* p2 = get(l, j);
			if (cmpF(p1, p2) > 0) {
				//interschimbam
				set(l, i, p2);
				set(l, j, p1);
			}
		}
	}
}