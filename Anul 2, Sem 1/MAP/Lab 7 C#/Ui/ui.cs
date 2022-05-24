using System;
using System.Collections.Generic;
using System.Text;
using Lab7.Service;
using Lab7.Validator;
using Lab7.Repository;
using Lab7.Domain;
using System.Configuration;
using System.Linq;

namespace Lab7.Ui
{
    class ui
    {
        /*private EchipaService echipaService;
        private ValidEchipa echipaValid;
        private EchipaRepo echipaRepo;

        private JucatorActivService jucatorActivService;
        private ValidJucatorActiv jucatorActivValid;
        private JucatorActivRepo jucatorActivRepo;

        private JucatorService jucatorService;
        private ValidJucator jucatorValid;
        private JucatorRepo jucatorRepo;

        private MeciService meciService;
        private ValidMeci meciValid;
        private MeciRepo meciRepo;*/

        public void Meniu()
        {
            Console.WriteLine("Meniu:");
            Console.WriteLine("1. Afiseaza toti jucatorii ai unei echipe.");
            Console.WriteLine("2. Afiseaza toti jucatorii activi ai unei echipe de la un anumit meci.");
            Console.WriteLine("3. Afiseaza toate meciurile dintr-o anumita perioada calendaristica.");
            Console.WriteLine("4. Afiseaza scorul de la un anumit meci.");
        }

        public void Start()
        {

            //IValidator<Echipa> validatorEchipa;
            string filenameEchipa = ConfigurationManager.AppSettings["echipeFilename"];
            EchipaRepo echipaRepo = new EchipaRepo(filenameEchipa);
            IRepository<int, Echipa> repoEchipa = echipaRepo;
            EchipaService serviceEchipa = new EchipaService(repoEchipa);

            string filenameJucatorActiv = ConfigurationManager.AppSettings["jucatoriActiviFilename"];
            JucatorActivRepo jucatorActivRepo = new JucatorActivRepo(filenameJucatorActiv);
            IRepository<int, JucatorActiv> repoJucatorActiv = jucatorActivRepo;
            JucatorActivService serviceJucatorActiv = new JucatorActivService(repoJucatorActiv);

            string filenameJucator = ConfigurationManager.AppSettings["jucatoriFilename"];
            JucatorRepo jucatorRepo = new JucatorRepo(filenameJucator);
            IRepository<int, Jucator> repoJucator = jucatorRepo;
            JucatorService serviceJucator = new JucatorService(repoJucator);

            string filenameMeci = ConfigurationManager.AppSettings["meciuriFilename"];
            MeciRepo meciRepo = new MeciRepo(filenameMeci);
            IRepository<int, Meci> repoMeci = meciRepo;
            MeciService serviceMeci = new MeciService(repoMeci);


            while(true)
            {
                Meniu();
                Console.WriteLine("Comanda: ");
                string cmd = Console.ReadLine();

                if(cmd.Equals("1"))
                {
                    /// 1. Afiseaza toti jucatorii ai unei echipe.
                    Console.WriteLine("Introduceti id echipei: ");
                    string idEchipa = Console.ReadLine();

                    Echipa e = serviceEchipa.FindOne(int.Parse(idEchipa));
                    
                    if (e == null)
                        Console.WriteLine("Echipa inexistenta!");
                    else
                    {
                        IEnumerable<Jucator> rezultat = serviceJucator.JucatoriEchipa(e);
                        rezultat.ToList().ForEach(Console.WriteLine);
                    }
                }
                else
                    if(cmd.Equals("2"))
                    {
                        /// 2. Afiseaza toti jucatorii activi ai unei echipe de la un anumit meci.
                        Console.WriteLine("Introduceti id-ul meciului: ");
                        string idMeci = Console.ReadLine();
                        Console.WriteLine("Introduceti id-ul echipei: ");
                        string idEchipa = Console.ReadLine();

                    try {
                        Meci meci = serviceMeci.FindOne(int.Parse(idMeci));
                        Echipa echipa = serviceEchipa.FindOne(int.Parse(idEchipa));

                        IEnumerable<JucatorActiv> jucatoriActiviInMeci = serviceJucatorActiv.JucatoriActiviDinMeci(meci);
                        IEnumerable<Jucator> jucatoriDinEchipe = serviceJucator.Transforma(jucatoriActiviInMeci);
                        IEnumerable<Jucator> JucatoriiUneiEchipe = serviceJucator.JucatoriiUneiEchipe(jucatoriDinEchipe,echipa);
                       
                        JucatoriiUneiEchipe.ToList().ForEach(Console.WriteLine);

                    }catch(Exception)
                    {
                        Console.WriteLine("Date invalide!");
                    }
                        
                    }
                    else
                        if(cmd.Equals("3"))
                        {
                            /// 3.Afiseaza toate meciurile dintr - o anumita perioada calendaristica.
                            Console.WriteLine("Introduceti prima data calendaristica: ");
                            string data1 = Console.ReadLine();
                            Console.WriteLine("Introduceti a doua data calendaristica: ");
                            string data2 = Console.ReadLine();

                            DateTime data1Parse, data2Parse;

                            try
                            {
                                data1Parse = DateTime.Parse(data1);
                                data2Parse = DateTime.Parse(data2);
                                IEnumerable<Meci> rezultat = serviceMeci.MeciuriDinPerioada(data1Parse, data2Parse);
                                rezultat.ToList().ForEach(Console.WriteLine);
                            }
                            catch(Exception )
                            {
                                Console.WriteLine("Data incorecta!");
                            }
                        }
                        else
                            if(cmd.Equals("4"))
                            {
                                /// 4. Afiseaza scorul de la un anumit meci.
                                Console.WriteLine("Introduceti id-ul meciului: ");
                                string idMeci = Console.ReadLine();
                                
                                 try {
                                    Meci meci = serviceMeci.FindOne(int.Parse(idMeci));
                                    Echipa echipa1 = serviceEchipa.FindOne(meci.Echipa1.ID);  
                                    Echipa echipa2 = serviceEchipa.FindOne(meci.Echipa2.ID);  

                                    IEnumerable<JucatorActiv> jucatoriActiviInMeci = serviceJucatorActiv.JucatoriActiviDinMeci(meci);
                                    IEnumerable<Jucator> jucatoriDinEchipe = serviceJucator.Transforma(jucatoriActiviInMeci);
                                    IEnumerable<Jucator> JucatoriiEchipei1 = serviceJucator.JucatoriiUneiEchipe(jucatoriDinEchipe,echipa1);
                                    IEnumerable<Jucator> JucatoriiEchipei2 = serviceJucator.JucatoriiUneiEchipe(jucatoriDinEchipe,echipa2);
                                    Tuple<int, int> scorFinal = serviceJucator.Scor(jucatoriActiviInMeci, JucatoriiEchipei1, JucatoriiEchipei2);

                                    Console.WriteLine(scorFinal);
                                 }
                                  catch(Exception)
                                    {
                                        Console.WriteLine("Date invalide!");
                                    }
                                
                            }
                            else
                                if(cmd.Equals("0"))
                                    break;
                                else
                                    Console.WriteLine("Comanda invalida!");
            }
        }
    }
}
