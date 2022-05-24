using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.model;
using test.repository;
using test.service;
using test.validator;
using System.Configuration;
namespace test
{

    class Program
    {
        static void Main(string[] args)
        {

            ///* Angajat a1 = new Angajat()
            // {
            //     ID = "a1",
            //     Nume = "Marinescu Ion",
            //     VenitPeOra = 9,
            //     Nivel = KnowledgeLevel.Medium
            // };
            // Console.WriteLine(a1);*/

            //IValidator<Angajat> validator = new AngajatValidator();
            //string filename = ConfigurationManager.AppSettings["angajatiFilename"];
            //IRepository<string, Angajat> repoFile = new AngajatiInFileRepository(validator, filename);
            //AngajatService serv = new AngajatService(repoFile);

            //List<Angajat> list = serv.FindAllAngajati();

            //list.Sort((a, b) =>
            //{
            //    return (int)(b.VenitPeOra - a.VenitPeOra);
            //});

            ////list.ForEach(x => Console.WriteLine(x));

            ////afisare angajati de tip JUNIOR

            ///* var juniorList = list.Where(x => x.Nivel == KnowledgeLevel.JUNIOR);

            // foreach (var e in juniorList)
            // {
            //     Console.WriteLine(e);
            // }*/

            //listSarcini.ForEach(x => Console.WriteLine(x));

            //List <Student> studentList = new List<Student>();
            //studentList.Add(new Student() { StudentID = 1, StudentName = "John", Age = 18 });
            //studentList.Add(new Student() { StudentID = 2, StudentName = "Steve", Age = 21 });
            //studentList.Add(new Student() { StudentID = 3, StudentName = "Bill", Age = 25 }); 
            //studentList.Add(new Student() { StudentID = 4, StudentName = "Ram", Age = 20 }); 
            //studentList.Add(new Student() { StudentID = 5, StudentName = "Ron", Age = 31 });
            //studentList.Add(new Student() { StudentID = 6, StudentName = "Chris", Age = 17 });
            //studentList.Add(new Student() { StudentID = 7, StudentName = "Rob", Age = 19 });

            //List<Student> Teenagers = studentList.Where(s => s.Age > 12 && s.Age < 20).ToList<Student>();

            ////Teenagers.ForEach(s => Console.WriteLine(s));

            //List<Student> studentsStartsWith = studentList.Where(s => s.StudentName.StartsWith("R")).ToList<Student>();

            //studentsStartsWith.ForEach(s => Console.WriteLine(s));

            IValidator<Sarcina> validatorSarcina = new SarcinaValidator();
            string filenameSarcina = ConfigurationManager.AppSettings["sarciniFilename"];
            IRepository<string, Sarcina> repoSarcina = new SarcinaInFileRepository(validatorSarcina, filenameSarcina);
            SarcinaService serviceSarcina = new SarcinaService(repoSarcina);
            List<Sarcina> listSarcini = serviceSarcina.FindAllSarcini();

            List<AverageDTO> medieSarcini = serviceSarcina.GetAverageTimePerDifficulty();
            medieSarcini.ForEach(x => Console.WriteLine(x));

            Console.WriteLine(serviceSarcina.GetLongestTask());

            
        }
    }
}
