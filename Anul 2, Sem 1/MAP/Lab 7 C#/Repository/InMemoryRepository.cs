using System;
using System.Collections.Generic;
using System.Text;
using Lab7.Validator;
using Lab7.Domain;
using System.Linq;

namespace Lab7.Repository
{
    class InMemoryRepository<ID, E> : IRepository<ID, E> where E : Entity<ID>
    {

        protected IDictionary<ID, E> entities = new Dictionary<ID, E>();

        public E Delete(ID id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<E> FindAll()
        {
            return entities.Values.ToList<E>();
        }

        public E FindOne(ID id)
        {
            return entities[id];
        }

        public E Save(E entity)
        {
            if (entity == null)
                throw new ArgumentNullException("entity must not be null");
            if (this.entities.ContainsKey(entity.ID))
            {
                return entity;
            }
            this.entities[entity.ID] = entity;
            return default(E);
        }

        public E Update(E entity)
        {
            throw new NotImplementedException();
        }
    }
}

