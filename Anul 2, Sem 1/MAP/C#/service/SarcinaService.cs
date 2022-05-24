using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;
using test.model;
using test.repository;

namespace test.service
{
    class SarcinaService
    {
        private IRepository<string, Sarcina> repoSarcina;

        public SarcinaService(IRepository<string,Sarcina> repo)
        {
            this.repoSarcina = repo;
        }

        public List<Sarcina> FindAllSarcini()
        {
            return repoSarcina.FindAll().ToList();
        }

        public List<AverageDTO> GetAverageTimePerDifficulty()
        {

            List<Sarcina> sarcini = repoSarcina.FindAll().ToList();

            var result = from s in sarcini
                         group s by s.TipDificultate into g
                         select new AverageDTO() { Dificultate = g.Key, Media = g.Average(x => x.NrOreEstimate) };


            return result.ToList();

        }

        //Sortam sarcinile dupa durata descrescator, si afisam primul element (cel mai lung task)
        public Sarcina GetLongestTask()
        {
            List<Sarcina> sarcini = repoSarcina.FindAll().ToList();

            var result = from s in sarcini
                         orderby s.NrOreEstimate descending
                         select s; 
            return result.FirstOrDefault(); 

        }
       
    }
}
