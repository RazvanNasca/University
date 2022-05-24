using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Domain
{
    class EntityToFile
    {
        public static Echipa CreareEchipa(string linie)
        {
            string[] fields = linie.Split(',');
            Echipa echipa = new Echipa()
            {
                ID = int.Parse(fields[0]),
                Nume = fields[1],
            };
            return echipa;
        }

        public static Jucator CreareJucator(string linie)
        {
            string[] fields = linie.Split(',');
            Jucator jucator = new Jucator()
            {
                ID = int.Parse(fields[0]),
                Nume = fields[1],
                Scoala = fields[2],
                Echipa = new Echipa() { ID = int.Parse(fields[3]), Nume = fields[4]},
            };
            return jucator;
        }

        public static Meci CreareMeci(string linie)
        {
            string[] fields = linie.Split(',');
            Meci meci = new Meci()
            {
                ID = int.Parse(fields[0]),
                Echipa1 = new Echipa() { ID = int.Parse(fields[1]), Nume = fields[2] },
                Echipa2 = new Echipa() { ID = int.Parse(fields[3]), Nume = fields[4] },
                Data = DateTime.Parse(fields[5]),
            };
            return meci;
        }

        public static JucatorActiv CreareJucatorActiv(string linie)
        {
            string[] fields = linie.Split(',');
            JucatorActiv jucatorActiv = new JucatorActiv()
            {
                ID = int.Parse(fields[0]),
                IdJucator = int.Parse(fields[1]),
                IdMeci = int.Parse(fields[2]),
                NrPuncteInscrise = int.Parse(fields[3]),
            };

            if (fields[4] == "Rezerva")
                jucatorActiv.Tip = TipJucator.Rezerva;
            else
                jucatorActiv.Tip = TipJucator.Participant;

            return jucatorActiv;
        }
    }
}
