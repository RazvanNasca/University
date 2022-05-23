#include<stdio.h>
#include<assert.h>
#include "teste.h"
#include "service.h"
#include "domain.h"

void test_serviceAdaugare()
{
	MyList allCars = creareLista();
	assert(serviceAdaugare("bm47rzv", "wv", "sport", &allCars) == 0);
	assert(serviceAdaugare("bm47rzv", "wv", "sport", &allCars) == 4);
	assert(serviceAdaugare("", "wv", "sport", &allCars) == 1);
	assert(serviceAdaugare("as12d", "wv", "sport", &allCars) == 1);
	assert(serviceAdaugare("bn47rzv", "w2v", "sport", &allCars) == 2);
	assert(serviceAdaugare("bn47rzv", "", "sport", &allCars) == 2);
	assert(serviceAdaugare("bn47rzv", "wv", "spor", &allCars) == 3);
	distrugeStore(&allCars);
}

void test_serviceActualizare()
{
	MyList allCars = creareLista();
	serviceAdaugare("kk12asd", "da", "suv", &allCars);
	assert(serviceActualizare("kk12asd", "nu", "sport", &allCars) == 0);
	assert(serviceActualizare("mm12asd", "da", "suv", &allCars) == 4);
	assert(serviceActualizare("mm1sd", "dif", "suv", &allCars) == 1);
	assert(serviceActualizare("mm12asd", "", "min", &allCars) == 2);
	assert(serviceActualizare("mm12asd", "li", "lol", &allCars) == 3);
	assert(serviceActualizare("mm12lsd", "da", "sport", &allCars) == 4);
	distrugeStore(&allCars);
}


void test_serviceActiune()
{
	MyList allCars = creareLista();
	serviceAdaugare("GG12asd", "da", "sport", &allCars);
	assert(serviceActiune("inchiriere", "GG12asd", &allCars) == 0);
	assert(serviceActiune("inchiriere", "GG12asd", &allCars) == 1);
	assert(serviceActiune("restituire", "GG12asd", &allCars) == 0);
	assert(serviceActiune("restituire", "GG12asd", &allCars) == 2);
	assert(serviceActiune("inchi", "GG12asd", &allCars) == 4);
	assert(serviceActiune("restituire", "GM12asd", &allCars) == 3);
	distrugeStore(&allCars);

}

void test_serviceCriteriu()
{
	MyList allCars = creareLista();
	serviceAdaugare("GG12asd", "da", "suv", &allCars);
	serviceAdaugare("GG12asb", "da", "suv", &allCars);
	serviceActiune("inchiriere", "GG12asb", &allCars);
	assert(serviceCriteriu("model", "da", &allCars) == 0);
	assert(serviceCriteriu("model", "da1", &allCars) == 1);
	assert(serviceCriteriu("del", "wv", &allCars) == 1);
	assert(serviceCriteriu("del", "wsdv", &allCars) == 1);
	assert(serviceCriteriu("categorie", "suv", &allCars) == 0);
	assert(serviceCriteriu("categorie", "sport", &allCars) == 2);
	assert(serviceCriteriu("categorie", "cal", &allCars) == 1);
	assert(serviceCriteriu("catorie", "mini", &allCars) == 1);
	distrugeStore(&allCars);
}

void test_serviceSortare()
{
	// 1 - criteriu inorect
	// 2 - ordine inorecta
	// 0 - succes

	MyList allCars = creareLista();
	serviceAdaugare("GG12asd", "da", "suv", &allCars);
	serviceAdaugare("kk12asd", "mmm", "suv", &allCars);
	serviceAdaugare("pp12asd", "ggg", "mini", &allCars);
	serviceAdaugare("pp12asd", "zzz", "sport", &allCars);
	assert(serviceSortare("model", "crescator", &allCars) == 0);
	assert(serviceSortare("categorie", "crescator", &allCars) == 0);
	assert(serviceSortare("categorie", "descrescator", &allCars) == 0);
	assert(serviceSortare("model", "descrescator", &allCars) == 0);
	assert(serviceSortare("mel", "crescator", &allCars) == 1);
	assert(serviceSortare("catrie", "crescator", &allCars) == 1);
	assert(serviceSortare("model", "descator", &allCars) == 2);
	assert(serviceSortare("categorie", "descator", &allCars) == 2);
	distrugeStore(&allCars);
}

void teste()
{
	test_serviceAdaugare();
	test_serviceActualizare();
	test_serviceActiune();
	test_serviceCriteriu();
	test_serviceSortare();
}