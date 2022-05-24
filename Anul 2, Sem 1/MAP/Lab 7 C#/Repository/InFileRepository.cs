using System;
using System.Collections.Generic;
using System.Text;
using Lab7.Validator;
using Lab7.Domain;

namespace Lab7.Repository
{
    public delegate E CreateEntity<E>(string line);

    abstract class InFileRepository<ID, E> : InMemoryRepository<ID, E> where E : Entity<ID>
    {
        protected string fileName;
        protected CreateEntity<E> createEntity;

        public InFileRepository(String fileName, CreateEntity<E> createEntity)
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
