using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace test.model
{
    enum KnowledgeLevel
    {
        JUNIOR, MEDIUM, SENIOR
    }
    class Angajat : Entity<String>
    {
        public String Nume { get; set; }
        public double VenitPeOra { get; set; }
        public KnowledgeLevel Nivel { get; set; }

        public override string ToString()
        {
            return ID + " " + Nume + " " + VenitPeOra + " " + Nivel;
        }


    }
}
