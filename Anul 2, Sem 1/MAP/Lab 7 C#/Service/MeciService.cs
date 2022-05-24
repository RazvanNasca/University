using Lab7.Domain;
using Lab7.Repository;
using Lab7.Validator;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace Lab7.Service
{
    class MeciService
    {
        private IRepository<int, Meci> repo;

        ///private IValidator<Meci> vali; , IValidator<Meci> valid

        public MeciService(IRepository<int, Meci> repository)
        {
            repo = repository;
            ///vali = valid;
        }

        public IEnumerable<Meci> GetAll()
        {
            return repo.FindAll();
        }

        public Meci FindOne(int id)
        {
            return repo.FindOne(id);
        }

        public IEnumerable<Meci> MeciuriDinPerioada(DateTime d1, DateTime d2)
        {
            return GetAll().ToList().Where(x => x.Data >= d1 && x.Data <= d2);
        }
    }
}
