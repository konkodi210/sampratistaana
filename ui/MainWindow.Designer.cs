﻿namespace ui
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
            this.memberStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.donationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ಪಸತಕಮರಟToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ಖರಚToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ವರದToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ತಗಳವರದToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ವರಷಕವರದToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            resources.ApplyResources(this.menuStrip1, "menuStrip1");
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.membershipToolStripMenuItem,
            this.ಖರಚToolStripMenuItem,
            this.ವರದToolStripMenuItem});
            this.menuStrip1.Name = "menuStrip1";
            // 
            // membershipToolStripMenuItem
            // 
            resources.ApplyResources(this.membershipToolStripMenuItem, "membershipToolStripMenuItem");
            this.membershipToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.memberStripMenuItem,
            this.donationToolStripMenuItem,
            this.ಪಸತಕಮರಟToolStripMenuItem});
            this.membershipToolStripMenuItem.Name = "membershipToolStripMenuItem";
            // 
            // memberStripMenuItem
            // 
            resources.ApplyResources(this.memberStripMenuItem, "memberStripMenuItem");
            this.memberStripMenuItem.Name = "memberStripMenuItem";
            this.memberStripMenuItem.Click += new System.EventHandler(this.memberStripMenuItem_Click);
            // 
            // donationToolStripMenuItem
            // 
            resources.ApplyResources(this.donationToolStripMenuItem, "donationToolStripMenuItem");
            this.donationToolStripMenuItem.Name = "donationToolStripMenuItem";
            this.donationToolStripMenuItem.Click += new System.EventHandler(this.donationToolStripMenuItem_Click);
            // 
            // ಪಸತಕಮರಟToolStripMenuItem
            // 
            resources.ApplyResources(this.ಪಸತಕಮರಟToolStripMenuItem, "ಪಸತಕಮರಟToolStripMenuItem");
            this.ಪಸತಕಮರಟToolStripMenuItem.Name = "ಪಸತಕಮರಟToolStripMenuItem";
            // 
            // ಖರಚToolStripMenuItem
            // 
            resources.ApplyResources(this.ಖರಚToolStripMenuItem, "ಖರಚToolStripMenuItem");
            this.ಖರಚToolStripMenuItem.Name = "ಖರಚToolStripMenuItem";
            // 
            // ವರದToolStripMenuItem
            // 
            resources.ApplyResources(this.ವರದToolStripMenuItem, "ವರದToolStripMenuItem");
            this.ವರದToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ತಗಳವರದToolStripMenuItem,
            this.ವರಷಕವರದToolStripMenuItem});
            this.ವರದToolStripMenuItem.Name = "ವರದToolStripMenuItem";
            // 
            // ತಗಳವರದToolStripMenuItem
            // 
            resources.ApplyResources(this.ತಗಳವರದToolStripMenuItem, "ತಗಳವರದToolStripMenuItem");
            this.ತಗಳವರದToolStripMenuItem.Name = "ತಗಳವರದToolStripMenuItem";
            // 
            // ವರಷಕವರದToolStripMenuItem
            // 
            resources.ApplyResources(this.ವರಷಕವರದToolStripMenuItem, "ವರಷಕವರದToolStripMenuItem");
            this.ವರಷಕವರದToolStripMenuItem.Name = "ವರಷಕವರದToolStripMenuItem";
            // 
            // MainWindow
            // 
            resources.ApplyResources(this, "$this");
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.menuStrip1);
            this.IsMdiContainer = true;
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
        private System.Windows.Forms.ToolStripMenuItem memberStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem donationToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem ಪಸತಕಮರಟToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem ಖರಚToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem ವರದToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem ತಗಳವರದToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem ವರಷಕವರದToolStripMenuItem;
    }
}

