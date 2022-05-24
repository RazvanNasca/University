using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Domain
{
    enum TipJucator
    {
        Rezerva, Participant
    }
    class JucatorActiv : Entity<int>
    {
        public int IdJucator { get; set; }

        public int IdMeci { get; set; }

        public int NrPuncteInscrise { get; set; }

        public TipJucator Tip { get; set; }

        public override string ToString()
        {
            return IdJucator + " - " + IdMeci + " - " + NrPuncteInscrise + " - " + Tip;
        }
    }
}
