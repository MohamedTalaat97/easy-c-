using System;
using System.Collections.Generic;
using System.Text;

namespace PpmdSharp
{
    /// <summary>
    /// Command line actions.
    /// </summary>
    internal enum Action
    {
        /// <summary>
        /// No action.
        /// </summary>
        None,

        /// <summary>
        /// Encode (compress) files into an archive.
        /// </summary>
        Encode,

        /// <summary>
        /// Decode (decompress) files from an archive.
        /// </summary>
        Decode
    }
}
