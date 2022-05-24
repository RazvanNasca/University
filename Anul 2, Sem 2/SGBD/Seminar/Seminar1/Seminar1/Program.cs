using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;

namespace Seminar1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Title = "Cosmetica pentru catei";
           

            string connectionString = @"Server=DESKTOP-8H8A507\SQLEXPRESS;Database=Seminar1225SGBD;Integrated Security=true;";

            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    Console.WriteLine( "Starea conexiunii: {0}", connection.State);
                    connection.Open();
                    Console.WriteLine( "Starea conexiunii: {0}", connection.State);

                    SqlCommand insertComand = new SqlCommand("INSERT INTO Caini (nume, rasa, data_nasterii, stapan) VALUES " +
                        "(@nume1, @rasa1, @data_nasterii1, @stapan1), (@nume2, @rasa2, @data_nasterii2, @stapan2);", connection);

                    insertComand.Parameters.AddWithValue("@nume1", "Rex");
                    insertComand.Parameters.AddWithValue("@rasa1", "labrador");
                    insertComand.Parameters.AddWithValue("@data_nasterii1", "2020-01-01");
                    insertComand.Parameters.AddWithValue("@stapan1", "Petru");

                    insertComand.Parameters.AddWithValue("@nume2", "Gusti");
                    insertComand.Parameters.AddWithValue("@rasa2", "bulldog");
                    insertComand.Parameters.AddWithValue("@data_nasterii2", "2021-01-01");
                    insertComand.Parameters.AddWithValue("@stapan2", "George");

                    int insertCount = insertComand.ExecuteNonQuery();
                    if (insertCount == 1)
                        Console.WriteLine("S-a adaugat o inregistrare");
                    else
                        Console.WriteLine("S-au adaugat {0} inregistrari", insertCount);

                    SqlCommand selectCommand = new SqlCommand("SELECT * FROM Caini;", connection);
                    SqlDataReader reader = selectCommand.ExecuteReader();
                    if(reader.HasRows)
                    {
                        Console.WriteLine("S-au returnat urmatoarele inregistrari: ");
                        while(reader.Read())
                        {
                            Console.WriteLine("{0}\t{1}\t{2}\t{3}\t{4}", reader.GetInt32(0), reader.GetString(1), reader.GetString(2),
                                reader.GetDateTime(3).ToString("dd-MM-yy"), reader.GetString(4));
                        }
                    }
                    reader.Close();

                    SqlCommand updateCommand = new SqlCommand("UPDATE Caini SET stapan = @stapanNou WHERE nume = @nume;", connection);
                    updateCommand.Parameters.AddWithValue("@stapanNou", "Teodor");
                    updateCommand.Parameters.AddWithValue("@nume", "Rex");
                    updateCommand.ExecuteNonQuery();

                    SqlCommand deleteCommand = new SqlCommand("DELETE FROM Caini WHERE stapan = @stapan;", connection);
                    deleteCommand.Parameters.AddWithValue("@stapan", "George");
                    deleteCommand.ExecuteNonQuery();

                    reader = selectCommand.ExecuteReader();
                    if (reader.HasRows)
                    {
                        Console.WriteLine("S-au returnat urmatoarele inregistrari: ");
                        while (reader.Read())
                        {
                            Console.WriteLine("{0}\t{1}\t{2}\t{3}\t{4}", reader.GetInt32(0), reader.GetString(1), reader.GetString(2),
                                reader.GetDateTime(3).ToString("dd-MM-yy"), reader.GetString(4));
                        }
                    }
                    reader.Close();

                }
            }
            catch(Exception ex)
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine("Mesajul erorii este {0}", ex.Message);
            }
            Console.ReadKey();

        }
    }
}
