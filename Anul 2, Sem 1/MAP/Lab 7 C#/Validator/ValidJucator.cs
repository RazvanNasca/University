using Lab7.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Validator
{
    class ValidJucator
    {
        public void Validate(Jucator E)
        {
            bool valid = true;
            //TODO
            if (valid == false)
                throw new ValidationException("Jucatorul nu e valid!");
        }
    }
}
