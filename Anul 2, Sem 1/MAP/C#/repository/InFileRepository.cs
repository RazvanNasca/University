using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using test.validator;
using test.model;

namespace test.repository
{
    public delegate E CreateEntity<E>(string line);

    abstract class InFileRepository<ID, E> : InMemoryRepository<ID, E> where E : Entity<ID>
    {
        protected string fileName;
        protected CreateEntity<E> createEntity;

        public InFileRepository(IValidator<E> vali, String fileName, CreateEntity<E> createEntity) : base(vali)
        {
            this.fileName = fileName;
            this.createEntity = createEntity;
            if (createEntity != null)
                loadFromFile();
        }

        protected virtual void loadFromFile()
        {
            List<E> list = DataReader.ReadData(fileName, createEntity);
            list.ForEach(x => entities[x.ID] = x);
        }
    }
}
