#pragma once
#include "pet.h"
#include "petrepo.h"
class ActiuneUndo {
public:
	virtual void doUndo() = 0;	
	//destructorul e virtual pentru a ne asigura ca daca dau delete se apeleaza destructorul din clasa care trebuie
	virtual ~ActiuneUndo()= default;
};

class UndoAdauga : public ActiuneUndo {
	Pet petAdaugat;
	PetRepo& rep;
public:
	UndoAdauga(PetRepo& rep,const  Pet& p) :rep{ rep }, petAdaugat{ p } {}

	void doUndo() override {
		rep.sterge(petAdaugat);
	}
};

class UndoSterge : public ActiuneUndo {
	Pet petSters;
	PetRepo& rep;
public:
	UndoSterge(PetRepo& rep, const  Pet& p) :rep{ rep }, petSters{ p } {}
	void doUndo() override {
		rep.store(petSters);
	}
};