using System;
using System.Collections.Generic;
using System.Text;

namespace PpmdSharp
{
    /// <summary>
    /// Command line options that modify the manner in which the command line <see cref="Action"/> is performed.
    /// </summary>
    [Flags]
    internal enum Options
    {
        /// <summary>
        /// No options specified.
        /// </summary>
        None = 0,

        /// <summary>
        /// Calculate the CRC (ie. checksum) for files.
        /// </summary>
        Checksum = 1,

        /// <summary>
        /// Delete files (if encoding then delete the file after it has been compressed into the archive; otherwise,
        /// if decoding then delete the archive after all files have been decompressed from the archive).
        /// </summary>
        Delete = 2,

        /// <summary>
        /// Do not prompt for files to be deleted or overwritten.
        /// </summary>
        Quiet = 4,

        /// <summary>
        /// Compress files based on the content of other files (typically produces smaller archives).
        /// </summary>
        Solid = 8,
    }
}
