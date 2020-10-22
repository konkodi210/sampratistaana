using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ui
{
    public partial class MainWindow : Form
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void showForm(Form form)
        {
            if (this.ActiveMdiChild != null) 
            {
                this.ActiveMdiChild.Dispose();
            }            
            form.MdiParent = this;
            form.Show();
        }

        private void memberStripMenuItem_Click(object sender, EventArgs e)
        {
            showForm(new MembershipForm());            
        }

        private void donationToolStripMenuItem_Click(object sender, EventArgs e)
        {
            showForm(new DonationForm());
        }
    }
}
