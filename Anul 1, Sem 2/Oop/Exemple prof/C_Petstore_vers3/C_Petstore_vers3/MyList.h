#pragma once
#include "pet.h"
typedef void* ElemType;                 /// NOU nu mai e Pet e void*

/*
function type for realeasing an element
*/
typedef void(*DestroyFct) (ElemType);       /// NOU nu era deloc

/*
  function type for copy an element
*/
typedef ElemType(*CopyFct) (ElemType);      /// NOU nu era deloc

typedef struct {
	ElemType* elems;
	int lg;
	int cap; //capacity
	DestroyFct dfnc;//used on destroy to release elements       /// NOU nu era deloc
} MyList;


/*
  Create an empty list
*/
MyList* createEmpty(DestroyFct f);          /// NOU nu avea parametru

/*
  Destroy list
*/
void destroyList(MyList* l);


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
ElemType set(MyList* l, int poz, ElemType p);       /// NOU ElemType in loc de Pet

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
Remove last element from the list
!!!! do not destroy removed element
return the removed element
*/
ElemType removeLast(MyList* l);                     /// NOU nu era
/*
  Make a shallow copy of the list
  return Mylist containing the same elements as l
*/
MyList* copyList(MyList* l, CopyFct cf);            /// NOU nu avea steluta si al 2lea parametru

void testCreateList();
void testIterateList();
void testCopyList();
void testResize();
void testListOfLists();
void testListOfInts();
void testRemoveLast();