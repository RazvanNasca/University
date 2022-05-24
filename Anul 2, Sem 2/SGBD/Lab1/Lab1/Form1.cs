using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace Lab1
{
    public partial class Form1 : Form
    {

        DataSet dsMarca = new DataSet();
        DataSet dsModel = new DataSet();
        SqlDataAdapter adapterMarca = new SqlDataAdapter();
        SqlDataAdapter adapterModel = new SqlDataAdapter();
        SqlConnection connection = new SqlConnection("Server=DESKTOP-8H8A507\\SQLEXPRESS;Database=GarajMasini;Integrated Security=true;");

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            parentTable.Text = "Marca";
            childTable.Text = "Model";

        }

        private void connection_Click(object sender, EventArgs e)
        {
            try
            {
                connection.Open();

                adapterMarca.SelectCommand = new SqlCommand("SELECT * FROM Marca;", connection);
                dsMarca.Clear();
                adapterMarca.Fill(dsMarca);
                parentTable.DataSource = dsMarca.Tables[0];

                start.Enabled = false;

                connection.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void ADD_Click(object sender, EventArgs e)
        {
            try
            {
                adapterModel.InsertCommand = new SqlCommand("INSERT INTO Model (idModel, idMarca, nume, an) VALUES (@model,@marca, @nume, @an)", connection);
                
                adapterModel.InsertCommand.Parameters.AddWithValue("@model", idModelText.Text);
                adapterModel.InsertCommand.Parameters.AddWithValue("@marca",idMarcaText.Text);
                adapterModel.InsertCommand.Parameters.AddWithValue("@nume", numeText.Text);
                adapterModel.InsertCommand.Parameters.AddWithValue("@an", anText.Text);

                connection.Open();
                adapterModel.InsertCommand.ExecuteNonQuery();     
                connection.Close();

                MessageBox.Show("Inserted Succesfully to the Database");

                dsModel.Clear();
                adapterModel.Fill(dsModel);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void DELETE_Click(object sender, EventArgs e)
        {
            try
            {
               adapterModel.DeleteCommand = new SqlCommand("DELETE FROM Model WHERE idModel = @model AND idMarca = @marca", connection);
               adapterModel.DeleteCommand.Parameters.Add("@model", SqlDbType.Int).Value = Int32.Parse(idModelText.Text);
               adapterModel.DeleteCommand.Parameters.Add("@marca", SqlDbType.Int).Value = Int32.Parse(idMarcaText.Text);
                   
               connection.Open();
               adapterModel.DeleteCommand.ExecuteNonQuery();
               connection.Close();

               MessageBox.Show("Deleted succesfully to the Database");

               dsModel.Clear();
               adapterModel.Fill(dsModel);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void UPDATE_Click(object sender, EventArgs e)
        {
            try
            {
               adapterModel.UpdateCommand = new SqlCommand("UPDATE Model SET nume = @nume, an = @an WHERE idModel = @model AND idMarca = @marca", connection);
               adapterModel.UpdateCommand.Parameters.Add("@model", SqlDbType.Int).Value = Int32.Parse(idModelText.Text);
               adapterModel.UpdateCommand.Parameters.Add("@marca", SqlDbType.Int).Value = Int32.Parse(idMarcaText.Text);
               adapterModel.UpdateCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = numeText.Text;
               adapterModel.UpdateCommand.Parameters.Add("@an", SqlDbType.Int).Value = Int32.Parse(anText.Text);
                    
               connection.Open();
               adapterModel.UpdateCommand.ExecuteNonQuery();            
               connection.Close();

               MessageBox.Show("Updated Succesfully to the Database");

               dsModel.Clear();
               adapterModel.Fill(dsModel);


            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void childTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (childTable.Rows[e.RowIndex].Cells[e.ColumnIndex].Value != null)
                {
                    childTable.CurrentRow.Selected = true;

                    idModelText.Text = childTable.Rows[e.RowIndex].Cells["idModel"].FormattedValue.ToString();
                    numeText.Text = childTable.Rows[e.RowIndex].Cells["nume"].FormattedValue.ToString();
                    anText.Text = childTable.Rows[e.RowIndex].Cells["an"].FormattedValue.ToString();

                    idModelText.Enabled = false;
                    idMarcaText.Enabled = false;
                    ADD.Enabled = false;
                    DELETE.Enabled = true;
                    UPDATE.Enabled = true;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void parentTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (parentTable.Rows[e.RowIndex].Cells[e.ColumnIndex].Value != null)
                {
                    idModelText.Text = "";
                    idMarcaText.Text = "";
                    numeText.Text = "";
                    anText.Text = "";

                    idModelText.Enabled = true;
                    idMarcaText.Enabled = false;
                    ADD.Enabled = true;
                    DELETE.Enabled = false;
                    UPDATE.Enabled = false;

                    parentTable.CurrentRow.Selected = true;

                    idMarcaText.Text = parentTable.Rows[e.RowIndex].Cells["idMarca"].FormattedValue.ToString();

                    adapterModel.SelectCommand = new SqlCommand("SELECT * FROM Model WHERE idMarca = @idMarca", connection);
                    adapterModel.SelectCommand.Parameters.AddWithValue("@idMarca", idMarcaText.Text);
                    dsModel.Clear();
                    adapterModel.Fill(dsModel);
                    childTable.DataSource = dsModel.Tables[0];
                }

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

    }
}
