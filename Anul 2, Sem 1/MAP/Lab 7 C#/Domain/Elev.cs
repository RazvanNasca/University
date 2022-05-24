using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Domain
{
    class Elev : Entity<int>
    {
        public string Nume { get; set; }

        public string Scoala { get; set; }
    }
}
