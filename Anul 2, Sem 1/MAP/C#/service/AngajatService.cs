using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.repository;
using test.model;

namespace test.service
{
    class AngajatService
    {
        private IRepository<string, Angajat> Repo;

        public AngajatService(IRepository<string, Angajat> repository) 
        {
            Repo = repository;
        }

        public List<Angajat> FindAllAngajati()
        {
            return Repo.FindAll().ToList();
        }


    }
}
