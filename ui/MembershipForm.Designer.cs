namespace ui
{
    partial class MembershipForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MembershipForm));
            this.label1 = new System.Windows.Forms.Label();
            this.serialNoLb = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.dateValue = new System.Windows.Forms.Label();
            this.nameLbl = new System.Windows.Forms.Label();
            this.nameTxt = new System.Windows.Forms.TextBox();
            this.phoneNoTxt = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.mobileNoLbl = new System.Windows.Forms.Label();
            this.mobileNoTxt = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.dobLbl = new System.Windows.Forms.Label();
            this.emailText = new System.Windows.Forms.TextBox();
            this.emailLbl = new System.Windows.Forms.Label();
            this.dobPicker = new System.Windows.Forms.DateTimePicker();
            this.serialNoTxt = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.regular = new System.Windows.Forms.RadioButton();
            this.lifetimeBtn = new System.Windows.Forms.RadioButton();
            this.label5 = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            resources.ApplyResources(this.label1, "label1");
            this.label1.Name = "label1";
            // 
            // serialNoLb
            // 
            resources.ApplyResources(this.serialNoLb, "serialNoLb");
            this.serialNoLb.Name = "serialNoLb";
            // 
            // label2
            // 
            resources.ApplyResources(this.label2, "label2");
            this.label2.Name = "label2";
            // 
            // dateValue
            // 
            resources.ApplyResources(this.dateValue, "dateValue");
            this.dateValue.ForeColor = System.Drawing.Color.MediumBlue;
            this.dateValue.Name = "dateValue";
            // 
            // nameLbl
            // 
            resources.ApplyResources(this.nameLbl, "nameLbl");
            this.nameLbl.Name = "nameLbl";
            // 
            // nameTxt
            // 
            resources.ApplyResources(this.nameTxt, "nameTxt");
            this.nameTxt.ForeColor = System.Drawing.Color.Black;
            this.nameTxt.Name = "nameTxt";
            // 
            // phoneNoTxt
            // 
            resources.ApplyResources(this.phoneNoTxt, "phoneNoTxt");
            this.phoneNoTxt.ForeColor = System.Drawing.Color.Black;
            this.phoneNoTxt.Name = "phoneNoTxt";
            // 
            // label3
            // 
            resources.ApplyResources(this.label3, "label3");
            this.label3.Name = "label3";
            // 
            // mobileNoLbl
            // 
            resources.ApplyResources(this.mobileNoLbl, "mobileNoLbl");
            this.mobileNoLbl.Name = "mobileNoLbl";
            // 
            // mobileNoTxt
            // 
            resources.ApplyResources(this.mobileNoTxt, "mobileNoTxt");
            this.mobileNoTxt.ForeColor = System.Drawing.Color.Black;
            this.mobileNoTxt.Name = "mobileNoTxt";
            // 
            // label4
            // 
            resources.ApplyResources(this.label4, "label4");
            this.label4.Name = "label4";
            // 
            // dobLbl
            // 
            resources.ApplyResources(this.dobLbl, "dobLbl");
            this.dobLbl.Name = "dobLbl";
            // 
            // emailText
            // 
            resources.ApplyResources(this.emailText, "emailText");
            this.emailText.ForeColor = System.Drawing.Color.Black;
            this.emailText.Name = "emailText";
            // 
            // emailLbl
            // 
            resources.ApplyResources(this.emailLbl, "emailLbl");
            this.emailLbl.Name = "emailLbl";
            // 
            // dobPicker
            // 
            resources.ApplyResources(this.dobPicker, "dobPicker");
            this.dobPicker.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.dobPicker.MaxDate = new System.DateTime(2100, 12, 31, 0, 0, 0, 0);
            this.dobPicker.MinDate = new System.DateTime(1920, 1, 1, 0, 0, 0, 0);
            this.dobPicker.Name = "dobPicker";
            // 
            // serialNoTxt
            // 
            resources.ApplyResources(this.serialNoTxt, "serialNoTxt");
            this.serialNoTxt.ForeColor = System.Drawing.Color.Red;
            this.serialNoTxt.Name = "serialNoTxt";
            // 
            // groupBox1
            // 
            resources.ApplyResources(this.groupBox1, "groupBox1");
            this.groupBox1.Controls.Add(this.regular);
            this.groupBox1.Controls.Add(this.lifetimeBtn);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.TabStop = false;
            // 
            // regular
            // 
            resources.ApplyResources(this.regular, "regular");
            this.regular.Name = "regular";
            this.regular.TabStop = true;
            this.regular.UseVisualStyleBackColor = true;
            // 
            // lifetimeBtn
            // 
            resources.ApplyResources(this.lifetimeBtn, "lifetimeBtn");
            this.lifetimeBtn.Name = "lifetimeBtn";
            this.lifetimeBtn.TabStop = true;
            this.lifetimeBtn.UseVisualStyleBackColor = true;
            // 
            // label5
            // 
            resources.ApplyResources(this.label5, "label5");
            this.label5.Name = "label5";
            // 
            // MembershipForm
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ControlBox = false;
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.serialNoTxt);
            this.Controls.Add(this.dobPicker);
            this.Controls.Add(this.dobLbl);
            this.Controls.Add(this.emailText);
            this.Controls.Add(this.emailLbl);
            this.Controls.Add(this.mobileNoTxt);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.mobileNoLbl);
            this.Controls.Add(this.phoneNoTxt);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.nameTxt);
            this.Controls.Add(this.nameLbl);
            this.Controls.Add(this.dateValue);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.serialNoLb);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "MembershipForm";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label serialNoLb;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label dateValue;
        private System.Windows.Forms.Label nameLbl;
        private System.Windows.Forms.TextBox nameTxt;
        private System.Windows.Forms.TextBox phoneNoTxt;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label mobileNoLbl;
        private System.Windows.Forms.TextBox mobileNoTxt;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label dobLbl;
        private System.Windows.Forms.TextBox emailText;
        private System.Windows.Forms.Label emailLbl;
        private System.Windows.Forms.DateTimePicker dobPicker;
        private System.Windows.Forms.Label serialNoTxt;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton regular;
        private System.Windows.Forms.RadioButton lifetimeBtn;
        private System.Windows.Forms.Label label5;
    }
}