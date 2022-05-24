using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.model;
using test.validator;

namespace test.repository
{
    class SarcinaInFileRepository:InFileRepository<string,Sarcina>
    {
        public SarcinaInFileRepository(IValidator<Sarcina> validator, string filename) : base(validator, filename, EntityToFileMapping.CreateSarcina) { }
    }
}
