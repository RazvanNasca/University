#pragma once
#include "pet.h"
typedef Pet ElemType;

typedef struct {
	ElemType* elems;
	int lg;
	int cap; //capacity
} MyList;

/*
  Create an empty list
*/
MyList createEmpty();

/*
  Destroy list
*/
void destroy(MyList* l);

/*
  Get an element from the list
  poz - position of the element, need to be valid
  return element on the given position
*/
ElemType get(MyList* l, int poz);

/*
Modify the element on the given pozition
return the overwrited element
*/
ElemType set(MyList* l, int poz, Pet p);

/*
  return number of elements in the list
*/
int size(MyList* l);

/*
  Add element into the list
  post: element is added to the end of the list
*/
void add(MyList* l, ElemType el);

/*
  Make a shallow copy of the list
  return Mylist containing the same elements as l
*/
MyList copyList(MyList* l);

void testCreateList();
void testIterateList();
void testCopyList();
void testResize();