using Lab7.Domain;
using Lab7.Repository;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace Lab7.Service
{
    class JucatorActivService
    {
        private IRepository<int, JucatorActiv> repo;

        public JucatorActivService(IRepository<int, JucatorActiv> repository)
        {
            repo = repository;
        }

        public IEnumerable<JucatorActiv> GetAll()
        {
            return repo.FindAll();
        }

        public JucatorActiv FindOne(int id)
        {
            return repo.FindOne(id);
        }

        public IEnumerable<JucatorActiv> JucatoriActiviDinMeci(Meci meci)
        {
            return GetAll().ToList().Where(x => x.IdMeci == meci.ID);
        }

       
    }
}
