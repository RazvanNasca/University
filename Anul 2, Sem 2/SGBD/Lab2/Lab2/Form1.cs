using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace Lab2
{
    public partial class Form1 : Form
    {

        SqlConnection connection = new SqlConnection(ConfigurationManager.ConnectionStrings["cn"].ConnectionString);

        DataSet dsParent = new DataSet();
        DataSet dsChild = new DataSet();
        SqlDataAdapter adapterParent = new SqlDataAdapter();
        SqlDataAdapter adapterChild = new SqlDataAdapter();
        int idParent;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                connection.Open();

                string SELECT = ConfigurationManager.AppSettings["selectQuery"];
                adapterParent.SelectCommand = new SqlCommand(SELECT, connection);

                string parent = ConfigurationManager.AppSettings["parentName"];
                parentName.Text = parent;

                string child = ConfigurationManager.AppSettings["childName"];
                childName.Text = child;

                dsParent.Clear();
                adapterParent.Fill(dsParent);
                parentTable.DataSource = dsParent.Tables[0];

                List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["columnChildNames"].Split(','));
                int pointX = 0;
                int pointY = 0;

                textBoxesPanel.Controls.Clear();
                foreach(string column in columnNames)
                {
                    Label label = new Label();
                    label.Text = column;
                    label.Location = new Point(pointX, pointY);
                    label.Visible = true;
                    label.Parent = textBoxesPanel;

                    TextBox textBox = new TextBox();
                    textBox.Name = column + "TextBox";
                    textBox.Location = new Point(pointX + 200, pointY);
                    textBox.Visible = true;
                    textBox.Parent = textBoxesPanel;

                    textBoxesPanel.Show();
                    pointY += 30;
                }
                        

                connection.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void parentTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            string id = ConfigurationManager.AppSettings["columnParentNames"];
            idParent = Convert.ToInt32(parentTable.CurrentRow.Cells[0].Value.ToString());

            addButton.Enabled = true;
            deleteButton.Enabled = false;
            updateButton.Enabled = false;

            parentTable.CurrentRow.Selected = true;

            TextBox textBox = (TextBox)textBoxesPanel.Controls[id + "TextBox"];
            textBox.Text = idParent.ToString();
            textBox.Enabled = false;

            string SELECT = ConfigurationManager.AppSettings["selectQueryChild"];
            adapterChild.SelectCommand = new SqlCommand(SELECT, connection);
            string selectQueryChildParameter = ConfigurationManager.AppSettings["selectQueryChildParameter"];
            adapterChild.SelectCommand.Parameters.AddWithValue(selectQueryChildParameter, idParent);
           
            dsChild.Clear();
            adapterChild.Fill(dsChild);
            childTable.DataSource = dsChild.Tables[0];
        }

        private void childTable_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (childTable.Rows[e.RowIndex].Cells[e.ColumnIndex].Value != null)
                {
                    childTable.CurrentRow.Selected = true;

                    addButton.Enabled = false;
                    deleteButton.Enabled = true;
                    updateButton.Enabled = true;

                    List<string> idNames = new List<string>(ConfigurationManager.AppSettings["deleteParameters"].Split(','));

                    foreach (string column in idNames)
                    {
                        TextBox textBox = (TextBox)textBoxesPanel.Controls[column + "TextBox"];
                        textBox.Enabled = false;
                    }

                    List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["columnChildNames"].Split(','));

                    foreach (string column in columnNames)
                    {
                        TextBox textBox = (TextBox)textBoxesPanel.Controls[column + "TextBox"];
                        textBox.Text = childTable.Rows[e.RowIndex].Cells[column].FormattedValue.ToString();
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void addButton_Click(object sender, EventArgs e)
        {
            try
            {
                string INSERT = ConfigurationManager.AppSettings["insertQuery"];
                adapterChild.InsertCommand = new SqlCommand(INSERT, connection);

                List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["insertParameters"].Split(','));

                foreach (string column in columnNames)
                {
                    TextBox textBox = (TextBox)textBoxesPanel.Controls[column + "TextBox"];
                    adapterChild.InsertCommand.Parameters.AddWithValue("@"+column, textBox.Text);
                }


                connection.Open();
                adapterChild.InsertCommand.ExecuteNonQuery();
                connection.Close();

                MessageBox.Show("Inserted Succesfully to the Database");

                dsChild.Clear();
                adapterChild.Fill(dsChild);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            try
            {
                string DELETE = ConfigurationManager.AppSettings["deleteQuery"];
                adapterChild.DeleteCommand = new SqlCommand(DELETE, connection);


                List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["deleteParameters"].Split(','));

                foreach (string column in columnNames)
                {
                    TextBox textBox = (TextBox)textBoxesPanel.Controls[column + "TextBox"];
                    adapterChild.DeleteCommand.Parameters.Add("@" + column , SqlDbType.Int).Value = Int32.Parse(textBox.Text);
                }

                connection.Open();
                adapterChild.DeleteCommand.ExecuteNonQuery();
                connection.Close();

                MessageBox.Show("Deleted succesfully to the Database");

                dsChild.Clear();
                adapterChild.Fill(dsChild);

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            try
            {
                string UPDATE = ConfigurationManager.AppSettings["updateQuery"];
                adapterChild.UpdateCommand = new SqlCommand(UPDATE, connection);

                List<string> columnNames = new List<string>(ConfigurationManager.AppSettings["updateParameters"].Split(','));

                foreach (string column in columnNames)
                {
                    TextBox textBox = (TextBox)textBoxesPanel.Controls[column + "TextBox"];
                    adapterChild.UpdateCommand.Parameters.Add("@" + column, SqlDbType.VarChar).Value = textBox.Text;
                }
                

                connection.Open();
                adapterChild.UpdateCommand.ExecuteNonQuery();
                connection.Close();

                MessageBox.Show("Updated Succesfully to the Database");

                dsChild.Clear();
                adapterChild.Fill(dsChild);


            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                connection.Close();
            }
        }
    }
}
