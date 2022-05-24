using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Domain
{
    class Echipa : Entity<int>
    {
        public string Nume { get; set; }
        public override string ToString()
        {
            return Nume;
        }
    }
}
