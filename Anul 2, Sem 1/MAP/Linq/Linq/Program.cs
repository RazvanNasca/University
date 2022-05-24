using System;
using System.Collections.Generic;
using Linq.Domain;
using System.Linq;

namespace Linq
{
    class Program
    {
        static void Main(string[] args)
        {
            List<Student> studentList = new List<Student>();
            studentList.Add(new Student() { StudentID = 1, StudentName = "John", Age = 18 });
            studentList.Add(new Student() { StudentID = 2, StudentName = "Steve", Age = 21 });
            studentList.Add(new Student() { StudentID = 3, StudentName = "Bill", Age = 25 });
            studentList.Add(new Student() { StudentID = 4, StudentName = "Ram", Age = 20 });
            studentList.Add(new Student() { StudentID = 5, StudentName = "Ron", Age = 31 });
            studentList.Add(new Student() { StudentID = 6, StudentName = "Chris", Age = 17 });
            studentList.Add(new Student() { StudentID = 7, StudentName = "Rob", Age = 19 });

            List<Student> teenAgerStudents = studentList.Where(s => s.Age > 12 && s.Age < 20).ToList<Student>();
            Console.Out.WriteLine("First interrogation:");
            teenAgerStudents.ForEach(Console.Out.WriteLine);

            List<Student> studentStartsWith = studentList.Where(s => s.StudentName.StartsWith("R")).ToList<Student>();
            Console.Out.WriteLine("Second interrogation:");
            studentStartsWith.ForEach(Console.Out.WriteLine);
        }
    }
}
