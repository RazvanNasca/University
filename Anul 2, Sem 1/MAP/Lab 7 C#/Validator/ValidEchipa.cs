using Lab7.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Validator
{
    class ValidEchipa
    {
        public void Validate(Echipa E)
        {
            bool valid = true;
            //TODO
            if (valid == false)
                throw new ValidationException("Echipa nu e valida!");
        }
    }
}
