#include <assert.h>

#include "DO.h"
#include "Iterator.h"

#include <exception>
using namespace std;

bool relatie1(TCheie cheie1, TCheie cheie2) {
    if (cheie1 <= cheie2) {
        return true;
    }
    else {
        return false;
    }
}

void testAll() {
    DO dictOrd = DO(relatie1);
    assert(dictOrd.dim() == 0);
    assert(dictOrd.vid());
    dictOrd.adauga(1, 2);
    assert(dictOrd.dim() == 1);
    assert(!dictOrd.vid());
    assert(dictOrd.cauta(1) != NULL_TVALOARE);
    TValoare v = dictOrd.adauga(1, 3);
    assert(v == 2);
    assert(dictOrd.cauta(1) == 3);

    Iterator it = dictOrd.iterator();
    it.prim();
    while (it.valid()) {
        TElem e = it.element();
        assert(e.second != NULL_TVALOARE);
        it.urmator();
    }

    assert(dictOrd.sterge(1) == 3);
    assert(dictOrd.vid());


    dictOrd.adauga(1, 2);
    dictOrd.adauga(2, 2);
    dictOrd.adauga(3, 2);
    dictOrd.adauga(4, 2);
    dictOrd.adauga(5, 2);
    dictOrd.adauga(6, 2);

    Iterator ite = dictOrd.iterator();
    
    try {
        ite.revinoKPasi(1);
        assert(false);
     }
    catch (...) {
        assert(true);
    }

    try {
        ite.revinoKPasi(0);
        assert(false);
    }
    catch (...) {
        assert(true);
    }

    ite.urmator();
    ite.urmator();

    ite.revinoKPasi(1);

    ite.urmator();
    ite.urmator();
    ite.urmator();

    try {
        ite.revinoKPasi(5);
        assert(false);
    }
    catch (...) {
        assert(true);
    }
}
