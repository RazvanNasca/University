using System;
using System.Collections.Generic;
using System.Text;
using Lab7.Domain;
using Lab7.Repository;
using Lab7.Validator;
using System.Linq;

namespace Lab7.Service
{
    class JucatorService
    {
        private IRepository<int, Jucator> repo;
        ///private IValidator<Jucator> vali;, IValidator<Jucator> valid

        public JucatorService(IRepository<int, Jucator> repository)
        {
            repo = repository;
            ///vali = valid;
        }

        public IEnumerable<Jucator> GetAll()
        {
            return repo.FindAll();
        }

        public IEnumerable<Jucator> JucatoriEchipa(Echipa e)
        {
            return GetAll().ToList().Where(x => x.Echipa.Nume.Equals(e.Nume));
        }

        public IEnumerable<Jucator> Transforma(IEnumerable<JucatorActiv> listJA)
        {
            IEnumerable<Jucator> all = GetAll();
            return all.ToList().Join(listJA,
                                      str1 => str1.ID,
                                      str2 => str2.IdJucator,
                                      (str1, str2) => str1
                                     ); 
        }

        public IEnumerable<Jucator> JucatoriiUneiEchipe(IEnumerable<Jucator> jucatori, Echipa echipa)
        {
            return jucatori.ToList().Where(x => x.Echipa.ID == echipa.ID);
        }

        public Tuple<int,int> Scor(IEnumerable<JucatorActiv> listaJucatoriActivi, IEnumerable<Jucator> echipa1, IEnumerable<Jucator> echipa2)
        {
            int scor1 = listaJucatoriActivi.Join(echipa1,
                                                 str1 => str1.IdJucator,
                                                 str2 => str2.ID,
                                                 (str1, str2) => str1).Sum(x => x.NrPuncteInscrise);
            int scor2 = listaJucatoriActivi.Join(echipa2,
                                                 str1 => str1.IdJucator,
                                                 str2 => str2.ID,
                                                 (str1, str2) => str1).Sum(x => x.NrPuncteInscrise);

            Tuple<int, int> rezultat = new Tuple<int, int>(scor1, scor2);
            return rezultat;
        }
    }
}
