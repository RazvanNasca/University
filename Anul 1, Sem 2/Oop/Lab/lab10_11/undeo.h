#pragma once
#include "medicament.h"
#include "repo.h"

class ActiuneUndo
{
public:
	virtual void doUndo() = 0;

	virtual ~ActiuneUndo() = default;
};

class UndoAdauga : public ActiuneUndo
{
private:
	Medicament med;
	RepoVirtual& r;
public:
	UndoAdauga(RepoVirtual& r, const Medicament& med) :r{ r }, med{ med } {}

	void doUndo() override
	{
		r.stergere(med);
	}
};

class UndoSterge : public ActiuneUndo
{
private:
	Medicament med;
	RepoVirtual& r;
public:
	UndoSterge(RepoVirtual& r, const Medicament& med) :r{ r }, med{ med } {}

	void doUndo() override
	{
		r.store(med);
	}
};

class UndoModifica : public ActiuneUndo
{
private:
	Medicament medInitial, medModificat;
	RepoVirtual& r;
public:
	UndoModifica(RepoVirtual& r, const Medicament& medInitial, const Medicament& medModificat) : r{ r }, medInitial{ medInitial }, medModificat{ medModificat } {}

	void doUndo() override
	{
		r.stergere(medModificat);
		r.store(medInitial);
	}
};

class UndoAdaugaReteta : public ActiuneUndo
{
private:
	Medicament med;
	//RepoVirtual& r;
	Ret& rete;
public:
	UndoAdaugaReteta(Ret& r, const Medicament& mede) :rete{ r }, med{ med }{}

	void doUndo() override
	{
		rete.stergereReteta(med);
	}
};
