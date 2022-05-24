using Lab7.Domain;
using Lab7.Validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Repository
{
    class EchipaRepo : InFileRepository<int, Echipa>
    {
        public EchipaRepo(string fileName) : base(fileName, EntityToFile.CreareEchipa)
        {

        }
    }
}
