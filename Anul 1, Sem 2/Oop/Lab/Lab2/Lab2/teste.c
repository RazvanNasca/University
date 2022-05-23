#include<stdio.h>
#include<assert.h>
#include "teste.h"
#include "service.h"
#include "domain.h"

void test_serviceAdaugare()
{
	assert(serviceAdauga("bm47rzv", "wv", "sport") == 0);
	assert(serviceAdauga("bm47rzv", "wv", "sport") == 4);
	assert(serviceAdauga("", "wv", "sport") == 1);
	assert(serviceAdauga("as12d", "wv", "sport") == 1);
	assert(serviceAdauga("bn47rzv", "w2v", "sport") == 2);
	assert(serviceAdauga("bn47rzv", "", "sport") == 2);
	assert(serviceAdauga("bn47rzv", "wv", "spor") == 3);

}

void test_serviceActualizare()
{
	serviceAdauga("kk12asd", "da", "suv");
	assert(serviceActualizare("kk12asd", "nu", "sport") == 0);
	assert(serviceActualizare("mm12asd","da","suv") == 4);
	assert(serviceActualizare("mm1sd","dif","suv") == 1);
	assert(serviceActualizare("mm12asd","","min") == 2);
	assert(serviceActualizare("mm12asd","li","lol") == 3);
	assert(serviceActualizare("mm12lsd","da","sport") == 4);
}

void test_serviceActiune()
{

	serviceAdauga("GG12asd", "da", "sport");
	assert(serviceActiune("inchiriere","GG12asd") == 0);
	assert(serviceActiune("inchiriere","GG12asd") == 1);
	assert(serviceActiune("restituire","GG12asd") == 0);
	assert(serviceActiune("restituire","GG12asd") == 2);
	assert(serviceActiune("inchi","GG12asd") == 4);
	assert(serviceActiune("restituire","GM12asd") == 3);


}

void test_serviceCriteriu()
{
	assert(serviceCriteriu("model", "wv") == 0);
	assert(serviceCriteriu("del", "wv") == 1);
	assert(serviceCriteriu("del", "wsdv") == 1);
	assert(serviceCriteriu("categorie", "sport") == 0);
	assert(serviceCriteriu("categorie", "suv") == 2);
	assert(serviceCriteriu("categorie", "cal") == 1);
	assert(serviceCriteriu("catorie", "mini") == 1);
}

void test_serviceSortare()
{
	// 1 - criteriu inorect
	// 2 - ordine inorecta
	// 0 - succes

	assert(serviceSortare("model","crescator") == 0);
	assert(serviceSortare("categorie","crescator") == 0);
	assert(serviceSortare("model","descrescator") == 0);
	assert(serviceSortare("categorie", "descrescator") == 0);
	assert(serviceSortare("mel", "crescator") == 1);
	assert(serviceSortare("catrie", "crescator") == 1);
	assert(serviceSortare("model", "descator") == 2);
	assert(serviceSortare("categorie", "descator") == 2);

}

void teste()
{
	test_serviceAdaugare();
	test_serviceActualizare();
	test_serviceActiune();
	test_serviceCriteriu();
	test_serviceSortare();
}