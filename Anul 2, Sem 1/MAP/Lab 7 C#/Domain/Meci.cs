using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Domain
{
    class Meci : Entity<int>
    {
        public Echipa Echipa1 { get; set; }

        public Echipa Echipa2 { get; set; }

        public DateTime Data { get;set; }

        public override string ToString()
        {
            return Echipa1.Nume + " - " + Echipa2.Nume + " - " + Data.ToString();
        }
    }
}
