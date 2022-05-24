using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace test.model
{
    class AverageDTO
    {

        public Dificultate Dificultate { get; set; }
        public double Media { get; set; }

        public AverageDTO() { }


        public override string ToString()
        {
            return Dificultate + " " + Media;
        }


    }
}
