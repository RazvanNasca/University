namespace Lab2
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
            this.textBoxesPanel = new System.Windows.Forms.Panel();
            this.childTable = new System.Windows.Forms.DataGridView();
            this.parentTable = new System.Windows.Forms.DataGridView();
            this.addButton = new System.Windows.Forms.Button();
            this.deleteButton = new System.Windows.Forms.Button();
            this.updateButton = new System.Windows.Forms.Button();
            this.parentName = new System.Windows.Forms.Label();
            this.childName = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.childTable)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.parentTable)).BeginInit();
            this.SuspendLayout();
            // 
            // textBoxesPanel
            // 
            this.textBoxesPanel.Location = new System.Drawing.Point(496, 299);
            this.textBoxesPanel.Name = "textBoxesPanel";
            this.textBoxesPanel.Size = new System.Drawing.Size(422, 202);
            this.textBoxesPanel.TabIndex = 0;
            // 
            // childTable
            // 
            this.childTable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.childTable.Location = new System.Drawing.Point(496, 66);
            this.childTable.Name = "childTable";
            this.childTable.RowHeadersWidth = 51;
            this.childTable.RowTemplate.Height = 24;
            this.childTable.Size = new System.Drawing.Size(422, 200);
            this.childTable.TabIndex = 1;
            this.childTable.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.childTable_CellClick);
            // 
            // parentTable
            // 
            this.parentTable.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.parentTable.Location = new System.Drawing.Point(27, 66);
            this.parentTable.Name = "parentTable";
            this.parentTable.RowHeadersWidth = 51;
            this.parentTable.RowTemplate.Height = 24;
            this.parentTable.Size = new System.Drawing.Size(422, 200);
            this.parentTable.TabIndex = 2;
            this.parentTable.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.parentTable_CellClick);
            // 
            // addButton
            // 
            this.addButton.Location = new System.Drawing.Point(27, 299);
            this.addButton.Name = "addButton";
            this.addButton.Size = new System.Drawing.Size(80, 28);
            this.addButton.TabIndex = 0;
            this.addButton.Text = "Add";
            this.addButton.UseVisualStyleBackColor = true;
            this.addButton.Click += new System.EventHandler(this.addButton_Click);
            // 
            // deleteButton
            // 
            this.deleteButton.Location = new System.Drawing.Point(27, 367);
            this.deleteButton.Name = "deleteButton";
            this.deleteButton.Size = new System.Drawing.Size(80, 28);
            this.deleteButton.TabIndex = 3;
            this.deleteButton.Text = "Delete";
            this.deleteButton.UseVisualStyleBackColor = true;
            this.deleteButton.Click += new System.EventHandler(this.deleteButton_Click);
            // 
            // updateButton
            // 
            this.updateButton.Location = new System.Drawing.Point(27, 442);
            this.updateButton.Name = "updateButton";
            this.updateButton.Size = new System.Drawing.Size(80, 28);
            this.updateButton.TabIndex = 4;
            this.updateButton.Text = "Update";
            this.updateButton.UseVisualStyleBackColor = true;
            this.updateButton.Click += new System.EventHandler(this.updateButton_Click);
            // 
            // parentName
            // 
            this.parentName.AutoSize = true;
            this.parentName.Location = new System.Drawing.Point(28, 32);
            this.parentName.Name = "parentName";
            this.parentName.Size = new System.Drawing.Size(50, 17);
            this.parentName.TabIndex = 5;
            this.parentName.Text = "Parent";
            // 
            // childName
            // 
            this.childName.AutoSize = true;
            this.childName.Location = new System.Drawing.Point(493, 32);
            this.childName.Name = "childName";
            this.childName.Size = new System.Drawing.Size(39, 17);
            this.childName.TabIndex = 6;
            this.childName.Text = "Child";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(994, 549);
            this.Controls.Add(this.childName);
            this.Controls.Add(this.parentName);
            this.Controls.Add(this.updateButton);
            this.Controls.Add(this.deleteButton);
            this.Controls.Add(this.addButton);
            this.Controls.Add(this.parentTable);
            this.Controls.Add(this.childTable);
            this.Controls.Add(this.textBoxesPanel);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.childTable)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.parentTable)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel textBoxesPanel;
        private System.Windows.Forms.DataGridView childTable;
        private System.Windows.Forms.DataGridView parentTable;
        private System.Windows.Forms.Button addButton;
        private System.Windows.Forms.Button deleteButton;
        private System.Windows.Forms.Button updateButton;
        private System.Windows.Forms.Label parentName;
        private System.Windows.Forms.Label childName;
    }
}

