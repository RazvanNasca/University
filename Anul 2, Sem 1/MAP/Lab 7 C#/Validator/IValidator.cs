using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Validator
{
    interface IValidator<E>
    {
        void Validate(E e);
    }
}
