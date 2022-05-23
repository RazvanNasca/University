#pragma once
#include "service.h"
#include <QAbstractTableModel>

class TableModel : public QAbstractTableModel {

private:

	vector <Task> tasks;

public:

	TableModel(vector <Task> tasks): tasks{ tasks } {}

	int rowCount(const QModelIndex& parent = QModelIndex()) const override
	{
		return tasks.size();
	}

	int columnCount(const QModelIndex& parent = QModelIndex()) const override
	{
		return 4;
	}

	QVariant data(const QModelIndex& index, int role) const override
	{
		if (role == Qt::DisplayRole)
		{
			Task t = tasks[index.row()];

			if (index.column() == 0)
				return QString::number(t.getId());

			if (index.column() == 1)
				return QString::fromStdString(t.getDescriere());

			string programatori = "";
			for (auto p : t.getProgramatori())
			{
				programatori += p;
				programatori += ", ";
			}
			if (index.column() == 2)
				return QString::fromStdString(programatori);

			if (index.column() == 3)
				return QString::fromStdString(t.getStare());

		}

		return QVariant();
	}

	void setTasks(vector <Task>& tasks)
	{
		this->tasks = tasks;
		auto topLeft = createIndex(0, 0);
		auto bottomRight = createIndex(rowCount(), columnCount());
		emit dataChanged(topLeft, bottomRight);
	}

	void refresh()
	{
		emit layoutChanged();
	}

	QVariant headerData(int section, Qt::Orientation orientation, int role) const
	{
		if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
		{
			if (section == 0)
				return "ID";
			if (section == 1)
				return "DESCRIERE";
			if (section == 2)
				return "PROGRAMATORI";
			if (section == 3)
				return "STARE";
		}

		return QVariant();
	}

};