using Lab7.Domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Validator
{
    class ValidJucatorActiv
    {
        public void Validate(JucatorActiv E)
        {
            bool valid = true;
            //TODO
            if (valid == false)
                throw new ValidationException("JucatorulActiv nu e valid!");
        }
    }
}
