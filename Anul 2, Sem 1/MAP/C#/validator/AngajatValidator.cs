using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.model;

namespace test.validator
{
    class AngajatValidator : IValidator<Angajat>
    {
        
        public void Validate(Angajat E)
        {
            bool valid = true;
            //TODO
            if (valid == false)
                throw new ValidationException("Obiectul nu e valid!");
        }
    }
}
