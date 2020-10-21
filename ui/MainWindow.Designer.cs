namespace ui
{
    partial class MainWindow
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainWindow));
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.membershipToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.lifeTimeToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.yearlyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.donationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            resources.ApplyResources(this.menuStrip1, "menuStrip1");
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.membershipToolStripMenuItem});
            this.menuStrip1.Name = "menuStrip1";
            // 
            // membershipToolStripMenuItem
            // 
            resources.ApplyResources(this.membershipToolStripMenuItem, "membershipToolStripMenuItem");
            this.membershipToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.lifeTimeToolStripMenuItem,
            this.yearlyToolStripMenuItem,
            this.donationToolStripMenuItem});
            this.membershipToolStripMenuItem.Name = "membershipToolStripMenuItem";
            // 
            // lifeTimeToolStripMenuItem
            // 
            resources.ApplyResources(this.lifeTimeToolStripMenuItem, "lifeTimeToolStripMenuItem");
            this.lifeTimeToolStripMenuItem.Name = "lifeTimeToolStripMenuItem";
            // 
            // yearlyToolStripMenuItem
            // 
            resources.ApplyResources(this.yearlyToolStripMenuItem, "yearlyToolStripMenuItem");
            this.yearlyToolStripMenuItem.Name = "yearlyToolStripMenuItem";
            // 
            // donationToolStripMenuItem
            // 
            resources.ApplyResources(this.donationToolStripMenuItem, "donationToolStripMenuItem");
            this.donationToolStripMenuItem.Name = "donationToolStripMenuItem";
            // 
            // MainWindow
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "MainWindow";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem membershipToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem lifeTimeToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem yearlyToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem donationToolStripMenuItem;
    }
}

