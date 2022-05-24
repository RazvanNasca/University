using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.model;

namespace test.repository
{
    interface IRepository<ID, E> where E:Entity<ID>
    {
        E FindOne(ID id);
        IEnumerable<E> FindAll();
        E Save(E entity);
        E Delete(ID id);
        E Update(E entity);
    }
}
