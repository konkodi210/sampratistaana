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
    public partial class MembershipForm : Form
    {
        public MembershipForm()
        {
            InitializeComponent();
            this.dobPicker.CustomFormat = "dd-MMM-yyyy";
        }

        protected override void OnResize(EventArgs e)
        {
            this.WindowState = FormWindowState.Maximized;
        }
    }
}
