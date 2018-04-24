#region Using

using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.IO;

#endregion

namespace PpmdSharp
{
    /// <summary>
    /// The starting point for this program (the <see cref="Main"/> method is automatically called when the
    /// executable is run).
    /// </summary>
    /// <remarks>
    /// <para>
    /// <b>January 12th, 2007</b> This is a port of Dmitry Shkarin's PPMd Variant I Revision 1.  Ported by
    /// Michael Bone (mjbone03@yahoo.com.au).  Added a checksum option "-c" (this adds a four byte CRC to
    /// the header of each file in an archive; the resulting archive is incompatible with Dmitry's original PPMd.exe
    /// and can only be decompressed using this port: the "-c" option must be used when decompressing).  Added
    /// a solid mode option "-s" (this automatically compresses files based on earlier encountered files, resulting in
    /// better compression).  The solid mode option should be used in conjunction with the "-f" option (so that all
    /// the compressed files are placed into a single archive, rather than separate files, which would cause problems
    /// when decompressing).  Added a quiet option "-q" to avoid prompting during compression or decompression.
    /// Added a progress option "-p" to the command line (however this has not currently been implemented).
    /// Changed the defaults so that the default memory used is 256 Mb and the default model order is 16.
    /// </para>
    /// <note>
    /// This was edited using Visual Studio 2005 with the font set to Tahoma 8 point, with tabs automatically
    /// converted to spaces, with spaces after casts, and with editor guides set using the following registry
    /// setting.  Note that some .NET 2.0 features have been used (such as generics and partial classes).
    /// <code>
    /// REGEDIT4
    /// 
    /// [HKEY_CURRENT_USER\Software\Microsoft\VisualStudio\8.0\Text Editor] 
    /// "Guides"="RGB(192,192,192) 110"
    /// </code>
    /// </note>
    /// </remarks>
    internal class Program
    {
        private const string ArchiveExtension = ".ppmd";
        private const int BufferSize = 4096;
        private static bool yesToAll;

        /// <summary>
        /// The main entry point.
        /// </summary>
        /// <param name="parameters">The command line parameters.</param>
        /// <returns>0 if compression or decompression was successfully performed; otherwise -1.</returns>
        /// 

      static string[] compress(int i)
        {
            string[] com = new string[2];
            com[0] = "e";
            com[1]= "DataSet_" + i + ".tsv";

            return com;
        }


        static string[] decompress(int i)
        {
            string[] com = new string[2];
            com[0] = "d";
            com[1] = "DataSet_" + i + ".pmd";

            return com;
        }
        private static int Main(string[] parameters)
          
        {
            string[] parameter;
            for (int i = 1; i <= 20; i++)
            {
                parameter = compress(i);
                try
                {
                    //return Perform(parameters);
                    if (Perform(parameter) != -1)
                    {
                        if (parameter[0] == "e")
                            Console.WriteLine("compressed successfully file : " + parameter[1]);
                        else
                            Console.WriteLine("decompressed successfully file : " + parameter[1]);

                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine();
                    Console.WriteLine();
                    Console.WriteLine("Error");
                    Console.WriteLine("-----");
                    Console.WriteLine(ex.Message);
                    Console.WriteLine();
                    Console.WriteLine();
                    Console.WriteLine("More Information");
                    Console.WriteLine("----------------");
                    Console.WriteLine("{0}", ex);
                    return -1;

                }
            }
            return 0;
        }

        /// <summary>
        /// The main processing.
        /// </summary>
        /// <param name="parameters">The command line parameters.</param>
        /// <returns>0 if compression or decompression was successfully performed; otherwise -1.</returns>
        private static int Perform(string[] parameters)
        {
            yesToAll = false;
            int allocatorSize = 256;
            int modelOrder = Model.MaximumOrder;
            ModelRestorationMethod modelRestorationMethod = ModelRestorationMethod.Restart;
            string targetPath = null;
            string progressPath = null;

            // The command line must include at least two parameters.  For example, to compress a file,
            //
            //     ppmd.exe e myfile.txt
            //     ppmd.exe e -c -s -fmyfiles.pmd *
            //
            // or to decompress an archive,
            //
            //     ppmd.exe d myarchive.pmd
            //     ppmd.exe d -c myfiles.pmd

            if (parameters.Length < 2)
            {
                Console.WriteLine("Fast PPMII compressor for textual data, variant {0}.", Model.Variant);
                Console.WriteLine("Designed and written by Dmitry Shkarin (dmitry.shkarin@mtu-net.ru).");
                Console.WriteLine("Ported to .NET by Michael Bone (mjbone03@yahoo.com.au).");
                Console.WriteLine();
                Console.WriteLine("Usage: ppmd.exe [e|d] [switches] [filenames|wildcards]");
                Console.WriteLine();
                Console.WriteLine("Switches (for encoding only):");
                Console.WriteLine("    -c - store a checksum (produces archives incompatible with original PPMd)");
                Console.WriteLine("    -d - delete files after processing, default: disabled");
                Console.WriteLine("    -f<filename> - set target file name to <filename>");
                Console.WriteLine("    -mN - limit to N Mb of memory (1 to 256), default: {0}", allocatorSize);
                Console.WriteLine("    -oN - model of order N (2 to {0}), default: {0}", Model.MaximumOrder, modelOrder);
                Console.WriteLine("    -q - quiet mode (automatically overwrites files)");
                Console.WriteLine("    -rN - model restoration method when memory limit reached:");
                Console.WriteLine("          0 - restart model from scratch{0}", (modelRestorationMethod == ModelRestorationMethod.Restart) ? " (default)" : "");
                Console.WriteLine("          1 - cut off model (slow){0}", (modelRestorationMethod == ModelRestorationMethod.CutOff) ? " (default)" : "");
                Console.WriteLine("          2 - freeze model{0}", (modelRestorationMethod == ModelRestorationMethod.Freeze) ? " (default)" : "");
                Console.WriteLine("    -s - solid mode");
                Console.WriteLine();
                Console.WriteLine("Switches (for decoding only):");
                Console.WriteLine("    -c - verify the checksum (must be used if was used when encoding)");
                Console.WriteLine("    -q - quiet mode (automatically overwrites files)");
                Console.WriteLine("    -p<filename> - write percentage progress to <filename>");
                return -1;
            }

            // Determine the action.  The action is either encoding (ie. compression) or decoding (ie. decompression).

            Action action = Action.None;

            if (parameters[0].StartsWith("e", StringComparison.OrdinalIgnoreCase))
                action = Action.Encode;
            else if (parameters[0].StartsWith("d", StringComparison.OrdinalIgnoreCase))
                action = Action.Decode;
            else
            {
                Console.WriteLine("Unrecognised command: \"{0}\".", parameters[0]);
                return -1;
            }

            // Determine the options associated with the action.

            Options options = Options.None;

            int index = 1;
            while (index < parameters.Length)
            {
                string parameter = parameters[index];
                if (!parameter.StartsWith("-") && !parameter.StartsWith("/"))
                {
                    break;
                }
                else if (parameter.Length < 2)
                {
                    Console.WriteLine("Unrecognised switch: \"{0}\".", parameter);
                    return -1;
                }

                index++;
                char option = char.ToLower(parameter[1]);
                string optionText = parameter.Substring(2).Trim();

                if (option == 'c')
                    options |= Options.Checksum;
                else if (option == 'd')
                    options |= Options.Delete;
                else if (option == 'f')
                    targetPath = optionText;
                else if (option == 'm')
                    allocatorSize = Clamp(optionText, 1, 256);
                else if (option == 'o')
                    modelOrder = Clamp(optionText, 2, Model.MaximumOrder);
                else if (option == 'p')
                    progressPath = optionText;
                else if (option == 'q')
                    options |= Options.Quiet;
                else if (option == 'r')
                    modelRestorationMethod = (ModelRestorationMethod) Clamp(optionText, 0, 2);
                else if (option == 's')
                    options |= Options.Solid;
                else
                {
                    Console.WriteLine("Unrecognised switch: \"{0}\".", parameter);
                    return -1;
                }
            }

            // Attempt to lower the priority of the current process (this is so that during long running compression
            // and decompression the user may move on to other tasks and remain relatively unhindered by the
            // resource-intensive current process).

            Process.GetCurrentProcess().PriorityClass = ProcessPriorityClass.BelowNormal;

            // Find all matching files.

            List<string> paths = new List<string>();
            while (index < parameters.Length)
            {
                string searchPattern = Path.GetFileName(parameters[index]);
                string directoryName = Path.GetDirectoryName(parameters[index]);
                if (string.IsNullOrEmpty(directoryName))
                    directoryName = Environment.CurrentDirectory;

                index++;
                paths.AddRange(Directory.GetFiles(directoryName, searchPattern, SearchOption.TopDirectoryOnly));
            }

            if (paths.Count == 0)
            {
                Console.WriteLine("No matching files were found.");
                return -1;
            }

            // Start the memory allocator now so that encoding and decoding can operate in solid mode.

            if (action == Action.Encode && (options & Options.Solid) != 0)
            {
                Allocator.Start(allocatorSize);
                if (!CanCreate(targetPath, options))
                    return -1;
            }

            // Process all files.

            foreach (string path in paths)
            {
                if (action == Action.Encode)
                {
                    if ((options & Options.Solid) == 0)
                        Allocator.Start(allocatorSize);
                    Encode(path, targetPath, allocatorSize, modelOrder, modelRestorationMethod, options);
                    if ((options & Options.Solid) != 0)
                        modelOrder = 1;
                    if ((options & Options.Delete) != 0)
                        File.Delete(path);
                }
                else if (action == Action.Decode)
                {
                    if ((options & Options.Solid) == 0)
                        Allocator.Start(allocatorSize);
                    Decode(path, progressPath, options);
                    if ((options & Options.Delete) != 0)
                        File.Delete(path);
                }
            }

            // Stop the memory allocator.

            Allocator.Stop();
            return 0;
        }

        /// <summary>
        /// Encode (compress) a single file into an archive.
        /// </summary>
        /// <param name="path">The path to the file which will be compressed.</param>
        /// <param name="targetPath">The path to the archive into which the compressed file will be placed (if null then the <see cref="path"/> will be used to derive an archive file name automatically).</param>
        /// <param name="allocatorSize">The amount of memory used by the <see cref="Allocator"/> (in Mb).</param>
        /// <param name="modelOrder">The model order (typically between 1 to 16, inclusive).</param>
        /// <param name="modelRestorationMethod">The <see cref="ModelRestorationMethod"/> (the method used to adjust the model when the memory limit is reached).</param>
        /// <param name="options"><see cref="Options"/> that modify the manner in which the compression is performed.</param>
        private static void Encode(string sourcePath, string targetPath, int allocatorSize, int modelOrder, ModelRestorationMethod modelRestorationMethod, Options options)
        {
            if (string.IsNullOrEmpty(targetPath))
                targetPath = Path.ChangeExtension(sourcePath, ArchiveExtension);

            if ((options & Options.Solid) == 0 && !CanCreate(targetPath, options))
                return;
            
            using (FileStream reader = new FileStream(sourcePath, FileMode.Open, FileAccess.Read, FileShare.Read, BufferSize, FileOptions.SequentialScan))
            using (FileStream writer = new FileStream(targetPath, FileMode.Append, FileAccess.Write, FileShare.Read, BufferSize, FileOptions.SequentialScan))
            {
                string sourceFileName = Path.GetFileName(sourcePath);
                long position = writer.Position;

                ArchiveInfo archiveInfo = new ArchiveInfo((options & Options.Checksum) != 0, sourceFileName);
                archiveInfo.Attributes = File.GetAttributes(sourceFileName);
                archiveInfo.LastWriteTime = File.GetLastWriteTimeUtc(sourceFileName);
                archiveInfo.Signature = Model.Signature;
                archiveInfo.Variant = Model.Variant;
                archiveInfo.AllocatorSize = allocatorSize;
                archiveInfo.ModelOrder = modelOrder;
                archiveInfo.ModelRestorationMethod = modelRestorationMethod;
                archiveInfo.FileName = sourceFileName;
                archiveInfo.Write(writer);

                archiveInfo.Checksum = Model.Encode(writer, reader, modelOrder, modelRestorationMethod, options);

                if ((options & Options.Checksum) != 0)
                {
                    writer.Seek(position, SeekOrigin.Begin);
                    archiveInfo.Write(writer);
                }
            }
        }

        /// <summary>
        /// Decode (decompress) an archive.  This will possibly produce multiple files.
        /// </summary>
        /// <param name="sourcePath">The path to the archive which will be decompressed.</param>
        /// <param name="progressPath">The path to the option progress file (null if not required).</param>
        /// <param name="options"><see cref="Options"/> that modify the manner in which the decompression is performed.</param>
        private static void Decode(string sourcePath, string progressPath, Options options)
        {
            using (FileStream reader = new FileStream(sourcePath, FileMode.Open, FileAccess.Read, FileShare.Read, BufferSize, FileOptions.SequentialScan))
            {
                while (true)
                {
                    ArchiveInfo archiveInfo = new ArchiveInfo((options & Options.Checksum) != 0);
                    if (!archiveInfo.Read(reader))
                        break;
                    string targetPath = archiveInfo.FileName;

                    // Verify that the compressed data is the correct version.

                    if (archiveInfo.Signature != Model.Signature || archiveInfo.Variant != Model.Variant)
                    {
                        try
                        {
                            Console.WriteLine("Cannot extract {0} from archive {1} (incorrect version: expected signature 0x{2:X8} and variant \"{3}\" but found signature 0x{4:X8} and variant \"{5}\").", Path.GetFileName(targetPath), Path.GetFileName(sourcePath), Model.Signature, Model.Variant, archiveInfo.Signature, archiveInfo.Variant);
                        }
                        catch (ArgumentException)
                        {
                            Console.WriteLine();
                            Console.WriteLine("The archive may have been created using the \"-c\" (checksum) option.  Try decompressing again, this time with \"-c\" on the command line after the letter \"d\".");
                            throw;
                        }
                        return;
                    }

                    // Decode the current file from the archive and calculate its CRC (ie. checksum).

                    if (!CanCreate(targetPath, options))
                        return;

                    uint checksum;
                    Allocator.Start(archiveInfo.AllocatorSize);
                    using (FileStream writer = new FileStream(targetPath, FileMode.Create, FileAccess.Write, FileShare.Read, BufferSize, FileOptions.SequentialScan))
                        checksum = Model.Decode(writer, reader, archiveInfo.ModelOrder, archiveInfo.ModelRestorationMethod, options);

                  //  File.SetAttributes(targetPath, archiveInfo.Attributes);
                    File.SetLastWriteTimeUtc(targetPath, archiveInfo.LastWriteTime);

                    // Verify the checksum.

                    if ((options & Options.Checksum) != 0 && checksum != archiveInfo.Checksum)
                    {
                        Console.WriteLine("Warning: {0} extracted from archive {1} has an incorrect checksum (expected checksum 0x{2:X8} but calculated checksum 0x{3:X8}).", Path.GetFileName(targetPath), Path.GetFileName(sourcePath), archiveInfo.Checksum, checksum);
                        return;
                    }
                }
            }
        }

        /// <summary>
        /// Determine if a file can be created or overwritten (if the file already exists then permission from the user is
        /// requested).
        /// </summary>
        /// <param name="path">The path of the file to examine.</param>
        /// <param name="options">Command line options.</param>
        /// <returns>true if the file can be created or overwritten; otherwise false.</returns>
        private static bool CanCreate(string path, Options options)
        {
            if (!File.Exists(path))
                return true;

            if (yesToAll || (options & Options.Quiet) != 0)
            {
                File.Delete(path);
                return true;
            }

            Console.WriteLine("{0} already exists, overwrite? [Y]es, [N]o, [A]ll, [Q]uit?", Path.GetFileName(path));

            // Continue to examine key presses until either Y, N, A, Q, Escape or Enter is pressed.

            while (true)
            {
                ConsoleKey key = Console.ReadKey().Key;
                if (key == ConsoleKey.A)
                {
                    yesToAll = true;
                    File.Delete(path);
                    return true;
                }
                else if (key == ConsoleKey.Y || key == ConsoleKey.Enter)
                {
                    File.Delete(path);
                    return true;
                }
                else if (key == ConsoleKey.Q || key == ConsoleKey.Escape)
                {
                    Console.WriteLine("Cancelled by the user.");
                    throw new OperationCanceledException("Cancelled by the user.");
                }
                else if (key == ConsoleKey.N)
                {
                    return false;
                }
            }
        }

        /// <summary>
        /// Convert the provided text to a number and ensure that its value is within the given limits.
        /// </summary>
        /// <param name="text">A string containing text which can potentially be converted to a number.</param>
        /// <param name="lowerLimit">The upper limit of the resulting number.</param>
        /// <param name="upperLimit">The lower limit of the resulting number.</param>
        /// <returns>
        /// The number converted from the text if the number is within the limits.  If the converted number is outside
        /// the limits then either the upper or lower limit will be returned (whichever is closest to the number).
        /// </returns>
        private static int Clamp(string text, int lowerLimit, int upperLimit)
        {
            int number;
            int.TryParse(text, out number);
            return Math.Min(Math.Max(number, lowerLimit), upperLimit);
        }
    }
}
