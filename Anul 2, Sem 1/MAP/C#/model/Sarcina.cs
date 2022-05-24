using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace test.model
{
    enum Dificultate
    {
        Usoara,Medie,Grea
    }
    class Sarcina:Entity<string>
    {
        public Dificultate TipDificultate { get; set; }
        public int NrOreEstimate { get; set; } 
        public override string ToString()
        {
            return ID + " " + TipDificultate + " " + NrOreEstimate;
        }

    }
}
