using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7.Validator
{
    class ValidationException : ApplicationException
    {
        public ValidationException(String message) : base(message) { }
    }
}
