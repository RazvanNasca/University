#include "export.h"
#include "repo.h"

void exportToCSV(const string& filename, const vector<Medicament>& meds)
{
	ofstream fout(filename, ios::trunc);
	if (!fout.is_open())
	{
		throw RepoException{ "Eroare la deschiderea fisierului!" };
	}

	for (const auto& med : meds)
	{
		fout << med.getDenumire() << ", ";
		fout << med.getProducator() << ", ";
		fout << med.getPret() << ", ";
		fout << med.getSubstanta_activa() << '\n';
	}

	fout.close();
}

void exportToHTML(const string& filename, const vector<Medicament>& meds)
{
	int count = 0;
	ofstream fout(filename, ios::trunc);
	if (!fout.is_open())
	{
		throw RepoException{ "Eroare la deschiderea fisierului!" };
	}

	fout << "<html><body>" << "\n";
	fout << "<table border=\"1\" bgcolor = black style=\"width: 100% \">" << "\n";

	fout << "<tr bgcolor = #0099ff>" << "\n";
	fout << "<td>" << " <b> NR </b> " << "</td>" << "\n";
	fout << "<td>" << " <b> DENUMIRE </b> " << "</td>" << "\n";
	fout << "<td>" << " <b> PRODUCATOR </b> " << "</td>" << "\n";
	fout << "<td>" << " <b> PRET </b> " << "</td>" << "\n";
	fout << "<td>" << " <b> SUBSTANTA ACTIVA </b> " << "</td>" << "\n";
	fout << "</tr>" << "\n";

	for (const auto& med : meds)
	{
		count++;
		fout << "<tr bgcolor = #99ffff text-align=center>" << "\n";
		fout << "<td>" << count << "</td>" << "\n";
		fout << "<td>" << med.getDenumire() << "</td>" << "\n";
		fout << "<td>" << med.getProducator() << "</td>" << "\n";
		fout << "<td>" << med.getPret() << "</td>" << "\n";
		fout << "<td>" << med.getSubstanta_activa() << "</td>" << "\n";
		fout << "</tr>" << "\n";
	}

	fout << "</table>" << "\n";
	fout << "</body></html>" << "\n";
	fout.close();
}