using Lab7.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Repository
{
    class JucatorActivRepo : InFileRepository<int, JucatorActiv>
    {
        public JucatorActivRepo(string fileName) : base(fileName, EntityToFile.CreareJucatorActiv)
        {

        }
    }
}
