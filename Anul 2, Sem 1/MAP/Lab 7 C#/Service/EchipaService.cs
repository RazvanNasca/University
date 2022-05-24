using Lab7.Domain;
using Lab7.Repository;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace Lab7.Service
{
    class EchipaService
    {
        private IRepository<int, Echipa> repo;

        public EchipaService(IRepository<int, Echipa> repository)
        {
            repo = repository;
        }

        public IEnumerable<Echipa> GetAll()
        {
            return repo.FindAll();
        }

        public Echipa FindOne(int id)
        {
            return repo.FindOne(id);
        }
    }
}
