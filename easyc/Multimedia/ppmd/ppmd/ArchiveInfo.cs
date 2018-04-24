#region Using

using System;
using System.IO;
using System.Text;

#endregion

namespace PpmdSharp
{
    /// <summary>
    /// Information stored in an archive describing a single file.
    /// </summary>
    internal class ArchiveInfo
    {
        private bool shouldUseChecksum;
        private uint signature = Model.Signature;
        private char variant = Model.Variant;
        private string fileName;
        private FileAttributes attributes;
        private DateTime lastWriteTime;
        private uint checksum;
        private int allocatorSize;
        private int modelOrder;
        private ModelRestorationMethod modelRestorationMethod;

        #region Constructors

        /// <summary>
        /// Initializes a new instance of the <see cref="ArchiveInfo"/> class.
        /// </summary>
        /// <param name="shouldUseChecksum"></param>
        public ArchiveInfo(bool shouldUseChecksum)
        {
            ShouldUseChecksum = shouldUseChecksum;
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="ArchiveInfo"/> class.
        /// </summary>
        /// <param name="shouldUseChecksum"></param>
        /// <param name="fileName"></param>
        public ArchiveInfo(bool shouldUseChecksum, string fileName) : this (shouldUseChecksum)
        {
            FileName = fileName;
        }

        #endregion

        #region Public Properties

        /// <summary>
        /// Gets or sets whether a CRC (ie. checksum) value should be stored in archives.  An archive that includes
        /// CRC values is incompatible with the original ppmd.exe (as downloaded from Dmitry Shkarin's web site).
        /// </summary>
        public bool ShouldUseChecksum
        {
            get { return shouldUseChecksum; }
            set { shouldUseChecksum = value; }
        }

        /// <summary>
        /// Gets or sets the four byte signature.
        /// </summary>
        public uint Signature
        {
            get { return signature; }
            set { signature = value; }
        }

        /// <summary>
        /// Gets or sets the compression algorithm variant.
        /// </summary>
        public char Variant
        {
            get { return variant; }
            set { variant = value; }
        }

        /// <summary>
        /// Gets or sets the file attributes.
        /// </summary>
        public FileAttributes Attributes
        {
            get { return attributes; }
            set { attributes = value; }
        }

        /// <summary>
        /// Gets or sets the CRC (ie. checksum) value for the file content.
        /// </summary>
        public uint Checksum
        {
            get { return checksum; }
            set { checksum = value; }
        }

        /// <summary>
        /// Gets or sets the model order.
        /// </summary>
        public int ModelOrder
        {
            get { return modelOrder; }
            set { modelOrder = value; }
        }

        /// <summary>
        /// Gets or sets the memory allocator size.
        /// </summary>
        public int AllocatorSize
        {
            get { return allocatorSize; }
            set { allocatorSize = value; }
        }

        /// <summary>
        /// Gets or sets the model restoration method.
        /// </summary>
        public ModelRestorationMethod ModelRestorationMethod
        {
            get { return modelRestorationMethod; }
            set { modelRestorationMethod = value; }
        }

        /// <summary>
        /// Gets or sets the file last write time.
        /// </summary>
        public DateTime LastWriteTime
        {
            get { return lastWriteTime; }
            set { lastWriteTime = value; }
        }

        /// <summary>
        /// Gets or sets the file name.
        /// </summary>
        public string FileName
        {
            get { return fileName; }
            set { fileName = value; }
        }

        #endregion

        #region Private Properties

        /// <summary>
        /// Gets or sets the <see cref="FileAttributes"/> using an integer.  This translates between Win32 file
        /// attributes and <see cref="FileAttributes"/> instances.
        /// </summary>
        private uint SerializedAttributes
        {
            get
            {
                uint value = 0;
                if ((attributes & FileAttributes.ReadOnly) != 0)
                    value |= 0x01;
                if ((attributes & FileAttributes.Hidden) != 0)
                    value |= 0x02;
                if ((attributes & FileAttributes.System) != 0)
                    value |= 0x04;
                if ((attributes & FileAttributes.Directory) != 0)
                    value |= 0x10;
                if ((attributes & FileAttributes.Archive) != 0)
                    value |= 0x20;
                if ((attributes & FileAttributes.Normal) != 0)
                    value |= 0x80;
                if ((attributes & FileAttributes.Temporary) != 0)
                    value |= 0x100;
                if ((attributes & FileAttributes.Compressed) != 0)
                    value |= 0x800;
                return value;
            }
            set
            {
                attributes = 0;
                if ((value & 0x01) != 0)
                    attributes |= FileAttributes.ReadOnly;
                if ((value & 0x02) != 0)
                    attributes |= FileAttributes.Hidden;
                if ((value & 0x04) != 0)
                    attributes |= FileAttributes.System;
                if ((value & 0x10) != 0)
                    attributes |= FileAttributes.Directory;
                if ((value & 0x20) != 0)
                    attributes |= FileAttributes.Archive;
                if ((value & 0x80) != 0)
                    attributes |= FileAttributes.Normal;
                if ((value & 0x100) != 0)
                    attributes |= FileAttributes.Temporary;
                if ((value & 0x800) != 0)
                    attributes |= FileAttributes.Compressed;
            }
        }

        /// <summary>
        /// Gets or sets the file last write date using a two byte integer.  This mimics FileTimeToDosDateTime.
        /// </summary>
        private ushort SerializedLastWriteDate
        {
            get
            {
                if (DosTime.Year < 1980)
                    return 1 | (1 << 5) | (0 << 9);  // January 1st, 1980
                else if (DosTime.Year > 2107)
                    return 31 | (12 << 5) | (127 << 9);  // December 31st, 2107
                else
                    return (ushort) (DosTime.Day | (DosTime.Month << 5) | ((DosTime.Year - 1980) << 9));
            }
            set
            {
                int year = 1980 + ((value >> 9) & 0x7f);
                int month = Math.Min(Math.Max((value >> 5) & 0x0f, 1), 12);
                int day = Math.Min(Math.Max(value & 0x1f, 1), DateTime.DaysInMonth(year, month));
                lastWriteTime = new DateTime(year, month, day, lastWriteTime.Hour, lastWriteTime.Minute, lastWriteTime.Second, lastWriteTime.Millisecond);
            }
        }

        /// <summary>
        /// Gets or sets the file last write time using a two byte integer.  This mimics FileTimeToDosDateTime.
        /// </summary>
        private ushort SerializedLastWriteTime
        {
            get
            {
                return (ushort) ((DosTime.Second / 2) | (DosTime.Minute << 5) | (DosTime.Hour << 11));
            }
            set
            {
                int hour = Math.Min((value >> 11) & 0x1f, 23);
                int minute = Math.Min((value >> 5) & 0x3f, 59);
                int second = Math.Min(2 * (value & 0x1f), 59);
                lastWriteTime = new DateTime(lastWriteTime.Year, lastWriteTime.Month, lastWriteTime.Day, hour, minute, second);
            }
        }

        /// <summary>
        /// Determine the date/time value that would be constructed by first performing FileTimeToDosDateTime
        /// and then performing DosDateTimeToFileTime (ie. round-tripping a date/time value).
        /// </summary>
        /// <remarks>
        /// This is partly based on experimentally running the following C++ code on a Windows XP machine.
        /// <code>
        /// <![CDATA[
        /// int mistmatchCount = 0;
        /// SYSTEMTIME systemTime;
        /// FILETIME fileTime;
        /// WORD dosDate;
        /// WORD dosTime;
        ///
        /// for (int hour = 0; hour < 24; hour++)
        ///     for (int minute = 0; minute < 60; minute++)
        ///         for (int second = 0; second < 60; second++)
        ///             for (int millisecond = 0; millisecond < 1000; millisecond++)
        ///             {
        ///                 systemTime.wDay = 1;
        ///                 systemTime.wMonth = 1;
        ///                 systemTime.wYear = 2000;
        ///                 systemTime.wHour = hour;
        ///                 systemTime.wMinute = minute;
        ///                 systemTime.wSecond = second;
        ///                 systemTime.wMilliseconds = millisecond;
        ///                 SystemTimeToFileTime(&systemTime, &fileTime);
        ///                 FileTimeToDosDateTime(&fileTime, &dosDate, &dosTime);
        ///                 int derivedSecond = (systemTime.wMilliseconds == 0) ? ((systemTime.wSecond + 1) / 2) : ((systemTime.wSecond / 2) + 1);
        ///                 if ((derivedSecond % 30) != (dosTime & 0x1f))
        ///                 {
        ///                     printf("%2.2d:%2.2d:%2.2d.%3.3d    FileTimeToDosDateTime: %2.2X    Derived: %2.2X\n", systemTime.wHour, systemTime.wMinute, systemTime.wSecond, systemTime.wMilliseconds, dosTime & 0x1f, derivedSecond);
        ///                     mistmatchCount++;
        ///                 }
        ///             }
        /// 
        /// printf("Mismatches: %d\n", mistmatchCount);
        /// getchar();
        /// ]]>
        /// </code>
        /// </remarks>
        private DateTime DosTime
        {
            get
            {
                DateTime dosTime = lastWriteTime;
                int dosSecond = 2 * (int) ((dosTime.Millisecond == 0) ? ((dosTime.Second + 1) / 2) : ((dosTime.Second / 2) + 1));

                // The number of seconds may have just been adjusted to a value of 60 or greater.  If this is the case
                // then the minute will be incremented (possibly resulting in the hour, day, month and year automatically
                // being incremented).

                if (dosSecond >= 60)
                    dosTime = dosTime.AddMinutes(1);
                dosTime = dosTime.AddMilliseconds(-dosTime.Millisecond);
                dosTime = dosTime.AddSeconds(-dosTime.Second);
                dosTime = dosTime.AddSeconds(dosSecond % 60);

                return dosTime;
            }
        }

        #endregion

        #region Public Methods

        /// <summary>
        /// Write the file information to an archive.
        /// </summary>
        /// <param name="writer"></param>
        public void Write(Stream writer)
        {
            BinaryWriter binaryWriter = new BinaryWriter(writer);
            binaryWriter.Write(Signature);
            binaryWriter.Write(SerializedAttributes);
            if (ShouldUseChecksum)
                binaryWriter.Write(Checksum);
            binaryWriter.Write((ushort) (((ModelOrder - 1) & 0x000f) | (((AllocatorSize - 1) << 4) & 0x0ff0) | ((Variant - 'A') << 12) & 0xf000));
            binaryWriter.Write((ushort) (Math.Min(fileName.Length, (1 << 14) - 1) | ((ushort) ModelRestorationMethod << 14)));
            binaryWriter.Write(SerializedLastWriteTime);
            binaryWriter.Write(SerializedLastWriteDate);
            binaryWriter.Write(fileName.ToCharArray());
        }

        /// <summary>
        /// Read the file information from an archive.
        /// </summary>
        /// <param name="reader"></param>
        /// <returns>true if the information was read; otherwise, if the end of stream was passed, false.</returns>
        public bool Read(Stream reader)
        {
            BinaryReader binaryReader = new BinaryReader(reader);

            try
            {
                // Read the compression algorithm signature, the file attributes and CRC (ie. checksum).

                Signature = binaryReader.ReadUInt32();
                SerializedAttributes = binaryReader.ReadUInt32();
                if (ShouldUseChecksum)
                    Checksum = binaryReader.ReadUInt32();

                // Read the model order, allocator size and compression algorithm variant.

                ushort information = binaryReader.ReadUInt16();
                ModelOrder = (information & 0x000f) + 1;
                AllocatorSize = ((information & 0x0ff0) >> 4) + 1;
                Variant = Encoding.ASCII.GetChars(new byte[] { (byte) (((information & 0xf000) >> 12) + 'A') })[0];

                // Read the model restoration method and the length of the file name.

                ushort lengthAndMethod = binaryReader.ReadUInt16();
                ModelRestorationMethod = (ModelRestorationMethod) (lengthAndMethod >> 14);
                int length = lengthAndMethod & 0x3fff;

                // Read the file last write date/time (accurate to within 2 seconds).

                SerializedLastWriteTime = binaryReader.ReadUInt16();
                SerializedLastWriteDate = binaryReader.ReadUInt16();

                // Read the file name (using the file name length that was determined earlier).

                char[] fileName = binaryReader.ReadChars(length);
                FileName = new string(fileName);
                return true;
            }
            catch (EndOfStreamException)
            {
                return false;
            }
        }

        #endregion
    }
}
