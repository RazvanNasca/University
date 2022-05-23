#include "MyList.h"

#include <assert.h>
#include <string.h>
#include <stdlib.h>
/*
Create an empty list
*/
MyList* createEmpty(DestroyFct f) { /// NOU  o pus steluta
	MyList* rez = malloc(sizeof(MyList));
	rez->lg = 0;
	rez->cap = 2;
	rez->elems = malloc(sizeof(ElemType)*rez->cap);
	rez->dfnc = f; /// NOU
	return rez;
}

/*
Destroy list
*/
void destroyList(MyList* l) {
	//dealocate pets
	for (int i = 0; i < l->lg; i++) {
		l->dfnc(l->elems[i]);	 /// NOU
	}
	free(l->elems);		
	free(l);			/// NOU
}

/*
Get an element from the list
poz - position of the element, need to be valid
return element on the given position
*/
ElemType get(MyList* l, int poz) {
	return l->elems[poz];
}

/*
  Modify the element on the given pozition
  return the overwrited element
*/
ElemType set(MyList* l, int poz, ElemType p) {
	ElemType rez= l->elems[poz];
	l->elems[poz] = p;
	return rez;
}

/*
return number of elements in the list
*/
int size(MyList* l) {
	return l->lg;
}

/*
  Allocate more memory if needed
*/
void ensureCapacity(MyList* l) {
	if (l->lg < l->cap) {
		return; //we have room
	}
	//alocate more memory
	ElemType* nElems = malloc(sizeof(ElemType)*(l->cap + 2));
	//copy elems
	for (int i = 0; i < l->lg; i++) {
		nElems[i] = l->elems[i];
	}
	//dealocate old vector
	free(l->elems);
	l->elems = nElems;
	l->cap += 2;
}
/*
Add element into the list
post: element is added to the end of the list
*/
void add(MyList* l, ElemType el) {
	ensureCapacity(l);
	l->elems[l->lg] = el;
	l->lg++;
}
/*
Remove last element from the list
!!!! do not destroy removed element
return the removed element
*/
ElemType removeLast(MyList* l) {			/// NOU tot
	ElemType rez = l->elems[l->lg - 1];
	l->lg -= 1;	
	return rez;
}

/*
Make a shallow copy of the list
return Mylist containing the same elements as l
*/
MyList* copyList(MyList* l,CopyFct cf) {   /// NOU functia CopyFct
	MyList* rez = createEmpty(l->dfnc);	/// NOU e apelata cu parametru
	for (int i = 0; i < size(l); i++) {
		ElemType p = get(l, i);		
		add(rez, cf(p));				/// NOU fara referinta si cu alta functie ca param 2
	}
	return rez;
}

void testCreateList() {
	MyList* l = createEmpty(destroyPet);
	assert(size(l) == 0);
	destroyList(l);
}
void testIterateList() {
	MyList* l = createEmpty(destroyPet);
	add(l, createPet("a", "b", 10));
	add(l, createPet("a2", "b2", 20));
	assert(size(l) == 2);
	Pet* p = get(l,0);

	assert(strcmp(p->type,"a") == 0);
	p = get(l, 1);
	assert(strcmp(p->species, "b2") == 0);
	destroyList(l);
}

void testCopyList() {
	MyList* l = createEmpty(destroyPet);
	add(l, createPet("a", "b", 10));
	add(l, createPet("a2", "b2", 20));
	MyList* l2 = copyList(l,copyPet);
	assert(size(l2) == 2);
	Pet* p = get(l2, 0);
	assert(strcmp(p->type, "a") == 0);
	destroyList(l);
	destroyList(l2);
}

void testResize() {
	MyList* l = createEmpty(destroyPet);
	for (int i = 0; i < 10; i++) {
		add(l, createPet("a", "b", 10));
	}
	assert(size(l) == 10);
	destroyList(l);	
}


void testListOfLists() {
	MyList* listOfLists = createEmpty(destroyList);
	MyList* listOfPets = createEmpty(destroyPet);
	add(listOfPets, createPet("a", "b", 1));
	add(listOfPets, createPet("a", "b", 1));
	add(listOfPets, createPet("a", "b", 1));
	add(listOfLists, listOfPets);
	MyList* cpy = copyList(listOfPets, copyPet);
	add(listOfLists, cpy);
	assert(size(listOfLists) == 2);
	assert(size(listOfPets) == 3);

	MyList* el2 = get(listOfLists, 1);
	assert(size(el2) == 3);

	destroyList(listOfLists);
}

/*
  create and initialize an int on the heap
*/
int* createIntOnHeap(int value) {
	int* pi = malloc(sizeof(int));
	*pi = value;
	return pi;
}
void testListOfInts() {	
	MyList* l = createEmpty(free);//use stdlib free to free memory
	
	add(l, createIntOnHeap(0));
	add(l, createIntOnHeap(1));
	add(l, createIntOnHeap(2));
	add(l, createIntOnHeap(3));
	add(l, createIntOnHeap(4));
	add(l, createIntOnHeap(0));
	assert(size(l) == 6);

	int* nr = get(l, 3);
	assert(*nr == 3);

	destroyList(l);
}

void testRemoveLast() {
	MyList* listOfPets = createEmpty(destroyPet);
	add(listOfPets, createPet("a", "b", 1));
	add(listOfPets, createPet("a", "b", 1));
	assert(size(listOfPets) == 2);
	Pet* el = removeLast(listOfPets);
	assert(size(listOfPets) == 1);
	destroyPet(el);

	el = removeLast(listOfPets);
	assert(size(listOfPets) == 0);
	destroyPet(el);

	destroyList(listOfPets);
}