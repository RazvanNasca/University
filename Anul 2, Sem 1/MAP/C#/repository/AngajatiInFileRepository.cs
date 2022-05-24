using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.model;
using test.validator;

namespace test.repository
{
    class AngajatiInFileRepository :InFileRepository<string,Angajat>
    {
        public  AngajatiInFileRepository(IValidator<Angajat> validator,string fileName):base(validator,fileName,EntityToFileMapping.CreateAngajat)
        {

        }
    }
}
