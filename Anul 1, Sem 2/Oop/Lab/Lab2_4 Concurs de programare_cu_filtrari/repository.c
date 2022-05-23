#include "repository.h"


DynamicArray* createDynamicArray(DestroyFct f)
{
	DynamicArray* arr = malloc(sizeof(DynamicArray));
	if (arr != NULL)
	{
		arr->elements = malloc(INIT_DYNAMIC_ARRAY_DIM * sizeof(TElem));
		arr->size = 0;
		arr->dimension = INIT_DYNAMIC_ARRAY_DIM;
		arr->dfnc = f;

		return arr;
	}
	return NULL;
}


void destroyDynamicArray(DynamicArray* arr)
{
	int i;
	for (i = 0; i < arr->size; i++)
	{
		arr->dfnc(arr->elements[i]);
	}
	free(arr->elements);
	free(arr);
}


int add(DynamicArray* arr, TElem p)
{
	// redimensionare
	if (arr->size == arr->dimension)
	{
		arr->dimension *= 2;
		TElem* new_elems = malloc(arr->dimension * sizeof(TElem));
		
		if (new_elems != NULL)
		{
			int i;
			for (i = 0; i < arr->size; i++)
				new_elems[i] = (TElem*)arr->elements[i];

			for (i = 0; i < arr->size; i++)
			{
				arr->dfnc(arr->elements[i]);
			}
			free(arr->elements);
			arr->elements = new_elems;
		}
		else return 1;
	}

	arr->elements[arr->size] = p;
	arr->size++;

	return 0;
}


int updateGrade(DynamicArray* arr, int poz, int index, int value)
{

	int score = getScore(arr->elements[poz]);
	score += value - getProblem(arr->elements[poz], index);
	setScore(arr->elements[poz], score);
	setProblem(arr->elements[poz], index, value);

	//arr->elements[poz].score += value - arr->elements[poz].problem_grades[index];
	//arr->elements[poz].problem_grades[index] = value;

	return 0;

}


int rm(DynamicArray* arr, int poz)
{
	
	// elibereaza memoria ocupata de elementul care va fi sters
	arr->dfnc(arr->elements[poz]);
	int j;
	if (arr != NULL && arr->elements != NULL)
	{
		// muta restul elementelor cu o pozitie la stanga
		for (j = poz; j < arr->size - 1; j++)
			arr->elements[j] = arr->elements[j + 1];
		arr->size--;
	}
	else return 1;

	return 0;
}


DynamicArray* filter(DynamicArray* arr, ConditionFunction condition, void* value)
{
	DynamicArray* a = createDynamicArray(destroyParticipant);

	int i;
	for (i = 0; i < arr->size; i++)
	{
		if ((*condition)(arr->elements[i], value))
			add(a, &arr->elements[i]);
	}

	return a;
}

DynamicArray* filter2(DynamicArray* arr, int val, char* surname)
{
	DynamicArray* a = createDynamicArray(destroyParticipant);

	int i;
	for (i = 0; i < arr->size; i++)
	{
		if (getScore(arr->elements[i]) >= val && strcmp(getSurname(arr->elements[i]), surname) == 0)
			add(a, &arr->elements[i]);
	}

	return a;
}

DynamicArray* filter3(DynamicArray* arr, int val)
{
	DynamicArray* a = createDynamicArray(destroyParticipant);

	int i;
	for (i = 0; i < arr->size; i++)
	{
		if (strlen(getName(arr->elements[i])) >= val && strlen(getSurname(arr->elements[i])) >=val)
			add(a, &arr->elements[i]);
	}

	return a;
}


void quickSort(DynamicArray* arr, int left, int right, ComparisonFunction compare, int reverse)
{
	int i = left, j = right;
	TElem pivot = arr->elements[(left + right) / 2];
	TElem tmp;

	while (i <= j)
	{
		// daca se cere ordonare in ordine inversa, se inmulteste valoarea returnata de functia compare cu -1
		while ((reverse ? -1 : 1) * (*compare)(arr->elements[i], &pivot) < 0) i++;
		while ((reverse ? -1 : 1) * (*compare)(arr->elements[j], &pivot) > 0) j--;

		if (i <= j)
		{
			tmp = arr->elements[i];
			arr->elements[i] = arr->elements[j];
			arr->elements[j] = tmp;
			i++;
			j--;
		}
	}

	if (left < j)
		quickSort(arr, left, j, compare, reverse);
	if (right > i)
		quickSort(arr, i, right, compare, reverse);
}


void sort(DynamicArray* arr, ComparisonFunction compare, int reverse)
{
	quickSort(arr, 0, arr->size - 1, compare, reverse);
}


TElem removeLast(DynamicArray* l) {			
	TElem rez = l->elements[l->size - 1];
	l->size -= 1;
	return rez;
}

TElem get(DynamicArray* l, int poz) {
	return l->elements[poz];
}

DynamicArray* copyList(DynamicArray* l, CopyFct cf) {  
	DynamicArray* rez = createDynamicArray(l->dfnc);	
	for (int i = 0; i < l->size; i++) {
		TElem p = get(l, i);
		add(rez, cf(p));				
	}
	return rez;
}
