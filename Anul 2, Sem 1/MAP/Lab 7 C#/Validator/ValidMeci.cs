using Lab7.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Validator
{
    class ValidMeci
    {
        public void Validate(Meci E)
        {
            bool valid = true;
            //TODO
            if (valid == false)
                throw new ValidationException("Meciul nu e valid!");
        }
    }
}
