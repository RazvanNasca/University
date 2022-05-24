namespace Lab1
{
    partial class Form1
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
            this.idModelText = new System.Windows.Forms.TextBox();
            this.idMarcaText = new System.Windows.Forms.TextBox();
            this.parentTable = new System.Windows.Forms.DataGridView();
            this.childTable = new System.Windows.Forms.DataGridView();
            this.ADD = new System.Windows.Forms.Button();
            this.DELETE = new System.Windows.Forms.Button();
            this.UPDATE = new System.Windows.Forms.Button();
            this.numeText = new System.Windows.Forms.TextBox();
            this.anText = new System.Windows.Forms.TextBox();
            this.idModel = new System.Windows.Forms.Label();
            this.idMarca = new System.Windows.Forms.Label();
            this.nume = new System.Windows.Forms.Label();
            this.an = new System.Windows.Forms.Label();
            this.start = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.parentTable)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.childTable)).BeginInit();
            this.SuspendLayout();
            // 
            // idModelText
            // 
            this.idModelText.Location = new System.Drawing.Point(824, 242);
            this.idModelText.Name = "idModelText";
            this.idModelText.Size = new System.Drawing.Size(199, 22);
            this.idModelText.TabIndex = 0;
            // 
            // idMarcaText
            // 
            this.idMarcaText.Location = new System.Drawing.Point(824, 283);
            this.idMarcaText.Name = "idMarcaText";
            this.idMarcaText.Size = new System.Drawing.Size(199, 22);
            this.idMarcaText.TabIndex = 1;
            // 
            // parentTable
            // 
            this.parentTable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.parentTable.Location = new System.Drawing.Point(48, 30);
            this.parentTable.Name = "parentTable";
            this.parentTable.RowHeadersWidth = 51;
            this.parentTable.RowTemplate.Height = 24;
            this.parentTable.Size = new System.Drawing.Size(467, 191);
            this.parentTable.TabIndex = 2;
            this.parentTable.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.parentTable_CellClick);
            // 
            // childTable
            // 
            this.childTable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.childTable.Location = new System.Drawing.Point(553, 29);
            this.childTable.Name = "childTable";
            this.childTable.RowHeadersWidth = 51;
            this.childTable.Size = new System.Drawing.Size(470, 192);
            this.childTable.TabIndex = 3;
            this.childTable.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.childTable_CellClick);
            // 
            // ADD
            // 
            this.ADD.Location = new System.Drawing.Point(48, 286);
            this.ADD.Name = "ADD";
            this.ADD.Size = new System.Drawing.Size(75, 24);
            this.ADD.TabIndex = 4;
            this.ADD.Text = "Add";
            this.ADD.UseVisualStyleBackColor = true;
            this.ADD.Click += new System.EventHandler(this.ADD_Click);
            // 
            // DELETE
            // 
            this.DELETE.Location = new System.Drawing.Point(48, 328);
            this.DELETE.Name = "DELETE";
            this.DELETE.Size = new System.Drawing.Size(75, 28);
            this.DELETE.TabIndex = 5;
            this.DELETE.Text = "Delete";
            this.DELETE.UseVisualStyleBackColor = true;
            this.DELETE.Click += new System.EventHandler(this.DELETE_Click);
            // 
            // UPDATE
            // 
            this.UPDATE.Location = new System.Drawing.Point(50, 367);
            this.UPDATE.Name = "UPDATE";
            this.UPDATE.Size = new System.Drawing.Size(73, 28);
            this.UPDATE.TabIndex = 6;
            this.UPDATE.Text = "Update";
            this.UPDATE.UseVisualStyleBackColor = true;
            this.UPDATE.Click += new System.EventHandler(this.UPDATE_Click);
            // 
            // numeText
            // 
            this.numeText.Location = new System.Drawing.Point(824, 326);
            this.numeText.Name = "numeText";
            this.numeText.Size = new System.Drawing.Size(199, 22);
            this.numeText.TabIndex = 7;
            // 
            // anText
            // 
            this.anText.Location = new System.Drawing.Point(824, 365);
            this.anText.Name = "anText";
            this.anText.Size = new System.Drawing.Size(199, 22);
            this.anText.TabIndex = 8;
            // 
            // idModel
            // 
            this.idModel.AutoSize = true;
            this.idModel.Location = new System.Drawing.Point(550, 247);
            this.idModel.Name = "idModel";
            this.idModel.Size = new System.Drawing.Size(57, 17);
            this.idModel.TabIndex = 9;
            this.idModel.Text = "idModel";
            // 
            // idMarca
            // 
            this.idMarca.AutoSize = true;
            this.idMarca.Location = new System.Drawing.Point(550, 286);
            this.idMarca.Name = "idMarca";
            this.idMarca.Size = new System.Drawing.Size(58, 17);
            this.idMarca.TabIndex = 10;
            this.idMarca.Text = "idMarca";
            // 
            // nume
            // 
            this.nume.AutoSize = true;
            this.nume.Location = new System.Drawing.Point(550, 331);
            this.nume.Name = "nume";
            this.nume.Size = new System.Drawing.Size(43, 17);
            this.nume.TabIndex = 11;
            this.nume.Text = "nume";
            // 
            // an
            // 
            this.an.AutoSize = true;
            this.an.Location = new System.Drawing.Point(550, 370);
            this.an.Name = "an";
            this.an.Size = new System.Drawing.Size(24, 17);
            this.an.TabIndex = 12;
            this.an.Text = "an";
            // 
            // start
            // 
            this.start.Location = new System.Drawing.Point(48, 246);
            this.start.Name = "start";
            this.start.Size = new System.Drawing.Size(75, 25);
            this.start.TabIndex = 13;
            this.start.Text = "Connect";
            this.start.UseVisualStyleBackColor = true;
            this.start.Click += new System.EventHandler(this.connection_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1048, 497);
            this.Controls.Add(this.start);
            this.Controls.Add(this.an);
            this.Controls.Add(this.nume);
            this.Controls.Add(this.idMarca);
            this.Controls.Add(this.idModel);
            this.Controls.Add(this.anText);
            this.Controls.Add(this.numeText);
            this.Controls.Add(this.UPDATE);
            this.Controls.Add(this.DELETE);
            this.Controls.Add(this.ADD);
            this.Controls.Add(this.childTable);
            this.Controls.Add(this.parentTable);
            this.Controls.Add(this.idMarcaText);
            this.Controls.Add(this.idModelText);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.parentTable)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.childTable)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox idModelText;
        private System.Windows.Forms.TextBox idMarcaText;
        private System.Windows.Forms.DataGridView parentTable;
        private System.Windows.Forms.DataGridView childTable;
        private System.Windows.Forms.Button ADD;
        private System.Windows.Forms.Button DELETE;
        private System.Windows.Forms.Button UPDATE;
        private System.Windows.Forms.TextBox numeText;
        private System.Windows.Forms.TextBox anText;
        private System.Windows.Forms.Label idModel;
        private System.Windows.Forms.Label idMarca;
        private System.Windows.Forms.Label nume;
        private System.Windows.Forms.Label an;
        private System.Windows.Forms.Button start;
    }
}

