using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Domain
{
    class Jucator : Elev 
    {
        public Echipa Echipa { get; set; }

        public override string ToString()
        {
            return Nume + " - " + Scoala + " - " + Echipa.Nume;
        }
    }
}
