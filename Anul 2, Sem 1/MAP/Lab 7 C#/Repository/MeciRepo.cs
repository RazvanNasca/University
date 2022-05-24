using Lab7.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Repository
{
    class MeciRepo : InFileRepository<int, Meci>
    {
        public MeciRepo(string fileName) : base(fileName, EntityToFile.CreareMeci)
        {

        }
    }
}
