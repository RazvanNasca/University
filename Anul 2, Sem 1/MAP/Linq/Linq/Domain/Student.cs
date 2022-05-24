using System;
using System.Collections.Generic;
using System.Text;

namespace Linq.Domain
{
    class Student
    {
        public int StudentID { get; set; }
        public string StudentName { get; set; }
        public int Age{ get; set; }

        public override string ToString()
        {
            return this.StudentID + " " + this.StudentName + " " + this.Age;
        }
    }
}
