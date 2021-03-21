using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.IO;


namespace BMPTS_Console
{
    class Program
    {
        [DllImport("kernel32.dll", SetLastError = true)]
        static extern bool AllocConsole();

        [DllImport("kernel32.dll")]
        static extern IntPtr GetConsoleWindow();

        [DllImport("user32.dll")]
        static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

        const int SW_HIDE = 0;
        const int SW_SHOW = 5;

        public static void ShowConsoleWindow()
        {
            var handle = GetConsoleWindow();

            if (handle == IntPtr.Zero)
            {
                AllocConsole();
            }
            else
            {
                ShowWindow(handle, SW_HIDE);
            }
        }

        static void Main(string[] args)
        {
            ShowConsoleWindow();

            StreamWriter outputStream = new StreamWriter("output.txt");



            //ProcessStartInfo ProcessInfo = new ProcessStartInfo("cmd.exe", "/c " + " bin\\sampratistaana.bat");
            //ProcessInfo.CreateNoWindow = false;
            //ProcessInfo.UseShellExecute = false;

            Process process = new Process();
            process.StartInfo.CreateNoWindow = false;
            process.StartInfo.UseShellExecute = false;
            process.StartInfo.FileName = "bin\\sampratistaana.bat";
            //process.StartInfo = ProcessInfo;

            process.StartInfo.RedirectStandardOutput = true;
            process.OutputDataReceived += new DataReceivedEventHandler((sender, e) =>
            {
                if (!String.IsNullOrEmpty(e.Data))
                {
                    outputStream.WriteLine(e.Data);
                    outputStream.Flush();
                }
            });

            process.Start();
            process.BeginOutputReadLine();

            //Process Process = Process.Start(ProcessInfo);
            process.WaitForExit();

            process.Close();
            outputStream.Close();
        }
    }
}
