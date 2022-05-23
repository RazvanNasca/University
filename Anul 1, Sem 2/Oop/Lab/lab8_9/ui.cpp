#include "ui.h"
#include <iostream>
#include <vector>
using std::cin;
using std::cout;

void Ui::startUi()
{
	while (true)
	{
		cout << "\n1 Adauga\n";
		cout << "2 Afiseaza\n";
		cout << "3 Cauta medicament\n";
		cout << "4 Actualizare date\n";
		cout << "5 Sterge\n";
		cout << "6 Filtrare\n";
		cout << "7 Sortare\n";
		cout << "8 Raport\n";
		cout << "9 Total\n";
		cout << "10 Reteta\n";
		cout << "11 Undo\n";
		cout << "0 Exit\n";
		cout << "Comanda:\n";

		int cmd = 0;
		cin >> cmd;
		if (cmd == 0)
			break;
		if (cmd == 1)
		{
			/// citire
			string denumire;
			string producator;
			double pret;
			int substanta_activa;
			cout << "Denumirea produsului: ";
			cin >> denumire;
			cout << "Denumirea producatorului: ";
			cin >> producator;
			cout << "Pretul produsului: ";
			cin >> pret;
			cout << "Cantitatea de substanta activa: ";
			cin >> substanta_activa;

			try {
				s.adauga(denumire, producator, pret, substanta_activa);
				cout << "Medicament adaugat!\n";
			}
			catch (RepoException & e) {
				cout << e.getMessage();
			}
			catch (ValidException & e) {
				cout << e.getMessage();
			}

		}
		else
			if (cmd == 2)
			{
				/// afisare
				cout << "Lista de medicamente:\n";
				const auto& meds = s.getAll();
				for (const auto& med : meds)
					cout << med.getDenumire() << " " << med.getProducator() << " " << med.getPret() << " " << med.getSubstanta_activa() << "\n";
				cout << "\n";
			}
			else
				if (cmd == 3)
				{
					/// cautare
					string denumire;
					string producator;
					constexpr double pret = 10;
					constexpr int substanta_activa = 2;

					cout << "Denumirea produsului: ";
					cin >> denumire;
					cout << "Denumirea producatorului: ";
					cin >> producator;

					try {
						s.cauta(denumire, producator, pret, substanta_activa);
						cout << "Medicamentul a fost deja adaugat!\n";
					}
					catch (RepoException & e) {
						cout << e.getMessage();
					}
					catch (ValidException & e) {
						cout << e.getMessage();
					}
				}
				else
					if (cmd == 4)
					{
						/// actualizeaza
						string denumire;
						string producator;
						double pret;
						int substanta_activa;
						cout << "Denumirea produsului care urmeaza sa fie actualizat: ";
						cin >> denumire;
						cout << "Denumirea producatorului care urmeaza sa fie actializat: ";
						cin >> producator;
						cout << "Noul pret al produsului: ";
						cin >> pret;
						cout << "Noua cantitatea de substanta activa: ";
						cin >> substanta_activa;

						try {
							s.actualizare(denumire, producator, pret, substanta_activa);
							cout << "Medicament actualizat!\n";
						}
						catch (RepoException & e) {
							cout << e.getMessage();
						}
						catch (ValidException & e) {
							cout << e.getMessage();
						}
					}
					else
						if (cmd == 5)
						{
							/// stergere
							const auto& meds = s.getAll();
							int cnt = 0;
							for (const auto& med : meds)
								cnt++;

							if (cnt == 0)
								cout << "Nu sunt medicamente de sters!\n";
							else
							{
								string denumire;
								string producator;
								constexpr double pret = 12.2;
								constexpr int substanta_activa = 1;
								cout << "Denumirea produsului care urmeaza sa fie sters: ";
								cin >> denumire;
								cout << "Denumirea producatorului care urmeaza sa fie sters: ";
								cin >> producator;

								try {
									s.stergere(denumire, producator, pret, substanta_activa);
									cout << "Medicament sters!\n";
								}
								catch (RepoException & e) {
									cout << e.getMessage();
								}
								catch (ValidException & e) {
									cout << e.getMessage();
								}
							}

						}
						else
							if (cmd == 6)
							{
								/// filtrari
								int criteriu = 0;
								cout << "1 - Filtreaza dupa pret\n";
								cout << "2 - Filtreaza dupa substanta activa\n";
								cin >> criteriu;

								vector <Medicament> rez;

								if (criteriu == 1)
								{
									cout << "Introuceti pretul maxim al medicamentelor filtrate:";
									double pret_ref = 0;
									cin >> pret_ref;
									rez = s.filtreazaPret(pret_ref);
								}
								else
									if (criteriu == 2)
									{
										cout << "Introuceti cantitatea de substanta activa maxim al medicamentelor filtrate:";
										int subst_ref = 0;
										cin >> subst_ref;
										rez = s.filtreazaSubst(subst_ref);
									}
									else
										cout << "Criteriu invalid! Reincearca!\n";

								if (rez.size() == 0)
									cout << "Nu exista astfel de medicamente!\n";
								else
								{
									cout << "Lista filtrata este:\n";
									for (const auto& med : rez)
										cout << med.getDenumire() << " " << med.getProducator() << " " << med.getPret() << " " << med.getSubstanta_activa() << "\n";
									cout << "\n";
								}
							}
							else
								if (cmd == 7)
								{
									/// sortari
									int criteriu = 0;
									cout << "1 - Sorteaza dupa Denumire\n";
									cout << "2 - Sorteaza dupa numele producatorului\n";
									cout << "3 - Sorteaza dupa pret si substanta activa\n";
									cin >> criteriu;

									vector <Medicament> rez;
									if (criteriu == 1)
										rez = s.sorteazaDenumire();
									else
										if (criteriu == 2)
											rez = s.sorteazaProducator();
										else
											if (criteriu == 3)
												rez = s.sorteazaPretSubst();
											else
												cout << "Criteriu invalid! Reincearca!\n";

									if (rez.size() == 0)
										cout << "Nu exista astfel de medicamente!\n";
									else
									{
										cout << "Lista filtrata este:\n";
										for (const auto& med : rez)
											cout << med.getDenumire() << " " << med.getProducator() << " " << med.getPret() << " " << med.getSubstanta_activa() << "\n";
										cout << "\n";
									}

								}
								else
									if (cmd == 8)
									{
										/// raport
										vector<DTO> raport = s.raport();
										for (auto aux : raport)
											cout << "Nume Produs: " << aux.get_denumire() << "---Producator: " << aux.get_producator() << "---Tip substanta activa: " << aux.get_subst() << "---Count: " << aux.contor() << '\n';

									}
									else
										if (cmd == 9)
										{
											/// pret total
											cout << s.total() << "\n";
										}
										else 
											if (cmd == 10)
											{
												/// reteta
												int optiune = 1;

												while (optiune != 0)
												{
													cout << "\t 1: Adauga medicament in reteta \n";
													cout << "\t 2: Goleste reteta \n";
													cout << "\t 3: Adauga aleator in reteta \n";
													cout << "\t 4: Afiseaza reteta \n";
													cout << "\t 5: Export reteta in fisier CSV \n";
													cout << "\t 6: Export reteta in fisier HTML \n";
													cout << "\t 7: Undo adaugare \n";
													cout << "\t 0: Exit \n";
													cout << "\t Introduceti comanda: ";
													cin >> optiune;

													if (optiune == 1)
													{
														/// adauga in reteta
														string denumire, producator;
														double pret;
														int substantaActiva;

														cout << "\t Introduceti denumirea: ";
														cin >> denumire;
														cout << "\t Introduceti producatorul: ";
														cin >> producator;
														cout << "\t Introduceti pretul: ";
														cin >> pret;
														cout << "\t Introduceti cantitatea de substanta activa: ";
														cin >> substantaActiva;

														s.adaugaReteta(denumire, producator, pret, substantaActiva);
													}
													else
														if (optiune == 2)
														{
															/// golire reteta
															s.clearAll();
														}
														else
															if (optiune == 3)
															{
																/// adauga aleator 
																int nrMeds;
																cout << "\t Introduceti numarul de medicamente pe care vreti sa le adaugati: ";
																cin >> nrMeds;
																s.adaugaRandom(nrMeds);
																cout << "\t Adaugarea s-a facut cu succes!";
															}
															else
																if (optiune == 4)
																{
																	///afiseaza reteta
																	cout << "\t Lista de medicamente din reteta:\n";
																	const auto& meds = s.getAllReteta();
																	for (const auto& med : meds)
																		cout << med.getDenumire() << " " << med.getProducator() << " " << med.getPret() << " " << med.getSubstanta_activa() << "\n";
																	cout << "\n";
																}
																else
																	if (optiune == 5)
																	{
																		/// export CSV
																		string filename;
																		cout << "\t Introduceti numele fisierului CSV: ";
																		cin >> filename;

																		s.exportCSV(filename);
																	}
																	else
																		if (optiune == 6)
																		{
																			/// export HTML
																			string filename;
																			cout << "\t Introduceti numele fisierului HTML: ";
																			cin >> filename;

																			s.exportHTML(filename);
																		}
																		else
																			if (optiune == 7)
																			{
																				/// undo adauga reteta
																				s.undo();
																			}
																			else
																				cout << "\t Optiune invalida!\n";

													cout << "\n";
													cout << "\t Numarul de medicamente este: " << s.getAllReteta().size() << "\n\n";
			
												}

												if (optiune == 0)
													s.clearAll();

											}
											else
												if (cmd == 11)
												{
													/// undo
													s.undo();
												}
												else 
													cout << "Comanda invalida! Reincercati!\n";
	}
}