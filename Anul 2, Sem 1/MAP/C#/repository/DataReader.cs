using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace test.repository
{
    class DataReader
    {
        public static List<T> ReadData<T> (string fileName, CreateEntity<T> createEntity)
        {
            List<T> lista = new List<T>();
            using (StreamReader reader = new StreamReader(fileName))
            {
                string s;
                while ((s = reader.ReadLine()) != null)
                {
                    T entity = createEntity(s);
                    lista.Add(entity);
                }
            }
            return lista;
        }
    }
}
