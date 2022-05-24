using System;
using System.Collections.Generic;
using System.IO;
using System.Text;


namespace Lab7.Repository
{
    class DataReader
    {
        public static List<T> ReadData<T>(string fileName, CreateEntity<T> createEntity)
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
