using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace test.service
{
    class Student
    {

        public int StudentID { get; set; }
        public string StudentName { get; set; }
        public int Age { get; set; }


        public Student(int sid, string name, int age)
        {
            this.StudentID = sid;
            this.StudentName = name;
            this.Age = age;
        }

        public Student()
        {
        }

        public override string ToString()
        {
            return this.StudentID + " " + this.StudentName + " " + this.Age;
        }


    }
}
