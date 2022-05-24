using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.model;

namespace test.validator
{
    class SarcinaValidator:IValidator<Sarcina>
    {
        public void Validate(Sarcina sar)
        {
            if (sar.NrOreEstimate < 0)
                throw new ValidationException("Numarul de ore nu poate fi negativ!!");
        }
    }
}
