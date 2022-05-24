namespace Interfete
{
    partial class MainPage
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.addToCart = new System.Windows.Forms.Button();
            this.removeFromCart = new System.Windows.Forms.Button();
            this.finishOrder = new System.Windows.Forms.Button();
            this.allProducesText = new System.Windows.Forms.Label();
            this.orderedProducesText = new System.Windows.Forms.Label();
            this.logoutMainbutton = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.searchProdusButton = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.Nume = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Pret = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Cantitate = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.Produs = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.CantitateComanda = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // addToCart
            // 
            this.addToCart.Location = new System.Drawing.Point(344, 324);
            this.addToCart.Name = "addToCart";
            this.addToCart.Size = new System.Drawing.Size(111, 23);
            this.addToCart.TabIndex = 2;
            this.addToCart.Text = "Adauga in cos";
            this.addToCart.UseVisualStyleBackColor = true;
            // 
            // removeFromCart
            // 
            this.removeFromCart.Location = new System.Drawing.Point(599, 334);
            this.removeFromCart.Name = "removeFromCart";
            this.removeFromCart.Size = new System.Drawing.Size(110, 23);
            this.removeFromCart.TabIndex = 3;
            this.removeFromCart.Text = "Elimina din cos";
            this.removeFromCart.UseVisualStyleBackColor = true;
            // 
            // finishOrder
            // 
            this.finishOrder.Location = new System.Drawing.Point(555, 393);
            this.finishOrder.Name = "finishOrder";
            this.finishOrder.Size = new System.Drawing.Size(154, 23);
            this.finishOrder.TabIndex = 4;
            this.finishOrder.Text = "Finalizeaza comanda";
            this.finishOrder.UseVisualStyleBackColor = true;
            // 
            // allProducesText
            // 
            this.allProducesText.AutoSize = true;
            this.allProducesText.Location = new System.Drawing.Point(24, 81);
            this.allProducesText.Name = "allProducesText";
            this.allProducesText.Size = new System.Drawing.Size(112, 17);
            this.allProducesText.TabIndex = 5;
            this.allProducesText.Text = "Toate produsele";
            // 
            // orderedProducesText
            // 
            this.orderedProducesText.AutoSize = true;
            this.orderedProducesText.Location = new System.Drawing.Point(536, 81);
            this.orderedProducesText.Name = "orderedProducesText";
            this.orderedProducesText.Size = new System.Drawing.Size(131, 17);
            this.orderedProducesText.TabIndex = 6;
            this.orderedProducesText.Text = "Cos de cumparaturi";
            // 
            // logoutMainbutton
            // 
            this.logoutMainbutton.Location = new System.Drawing.Point(748, 24);
            this.logoutMainbutton.Name = "logoutMainbutton";
            this.logoutMainbutton.Size = new System.Drawing.Size(84, 33);
            this.logoutMainbutton.TabIndex = 7;
            this.logoutMainbutton.Text = "LogOut";
            this.logoutMainbutton.UseVisualStyleBackColor = true;
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(102, 370);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(164, 22);
            this.textBox1.TabIndex = 8;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(24, 373);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(57, 17);
            this.label1.TabIndex = 9;
            this.label1.Text = "Produs:";
            // 
            // searchProdusButton
            // 
            this.searchProdusButton.Location = new System.Drawing.Point(194, 415);
            this.searchProdusButton.Name = "searchProdusButton";
            this.searchProdusButton.Size = new System.Drawing.Size(72, 23);
            this.searchProdusButton.TabIndex = 10;
            this.searchProdusButton.Text = "Cauta";
            this.searchProdusButton.UseVisualStyleBackColor = true;
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Nume,
            this.Pret,
            this.Cantitate});
            this.dataGridView1.Location = new System.Drawing.Point(27, 145);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersWidth = 51;
            this.dataGridView1.RowTemplate.Height = 24;
            this.dataGridView1.Size = new System.Drawing.Size(428, 153);
            this.dataGridView1.TabIndex = 11;
            // 
            // Nume
            // 
            this.Nume.HeaderText = "Nume";
            this.Nume.MinimumWidth = 6;
            this.Nume.Name = "Nume";
            this.Nume.Width = 125;
            // 
            // Pret
            // 
            this.Pret.HeaderText = "Pret";
            this.Pret.MinimumWidth = 6;
            this.Pret.Name = "Pret";
            this.Pret.Width = 125;
            // 
            // Cantitate
            // 
            this.Cantitate.HeaderText = "Cantitate";
            this.Cantitate.MinimumWidth = 6;
            this.Cantitate.Name = "Cantitate";
            this.Cantitate.Width = 125;
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Produs,
            this.CantitateComanda});
            this.dataGridView2.Location = new System.Drawing.Point(529, 149);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.RowHeadersWidth = 51;
            this.dataGridView2.RowTemplate.Height = 24;
            this.dataGridView2.Size = new System.Drawing.Size(303, 148);
            this.dataGridView2.TabIndex = 12;
            // 
            // Produs
            // 
            this.Produs.HeaderText = "Produs";
            this.Produs.MinimumWidth = 6;
            this.Produs.Name = "Produs";
            this.Produs.Width = 125;
            // 
            // CantitateComanda
            // 
            this.CantitateComanda.HeaderText = "Cantitate";
            this.CantitateComanda.MinimumWidth = 6;
            this.CantitateComanda.Name = "CantitateComanda";
            this.CantitateComanda.Width = 125;
            // 
            // MainPage
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(864, 450);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.searchProdusButton);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.logoutMainbutton);
            this.Controls.Add(this.orderedProducesText);
            this.Controls.Add(this.allProducesText);
            this.Controls.Add(this.finishOrder);
            this.Controls.Add(this.removeFromCart);
            this.Controls.Add(this.addToCart);
            this.Name = "MainPage";
            this.Text = "MainPage";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button addToCart;
        private System.Windows.Forms.Button removeFromCart;
        private System.Windows.Forms.Button finishOrder;
        private System.Windows.Forms.Label allProducesText;
        private System.Windows.Forms.Label orderedProducesText;
        private System.Windows.Forms.Button logoutMainbutton;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button searchProdusButton;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridViewTextBoxColumn Nume;
        private System.Windows.Forms.DataGridViewTextBoxColumn Pret;
        private System.Windows.Forms.DataGridViewTextBoxColumn Cantitate;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.DataGridViewTextBoxColumn Produs;
        private System.Windows.Forms.DataGridViewTextBoxColumn CantitateComanda;
    }
}