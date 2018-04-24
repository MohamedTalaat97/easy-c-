#region Using

using System;
using System.IO;

#endregion

namespace PpmdSharp
{
    /// <summary>
    /// A simple range coder.
    /// </summary>
    /// <remarks>
    /// Note that in most cases fields are used rather than properties for performance reasons (for example,
    /// <see cref="Scale"/> is a field rather than a property).
    /// </remarks>
    internal static class Coder
    {
        private const uint RangeTop = 1 << 24;
        private const uint RangeBottom = 1 << 15;
        private static uint low;
        private static uint code;
        private static uint range;

        public static uint LowCount;
        public static uint HighCount;
        public static uint Scale;

        public static void RangeEncoderInitialize()
        {
            low = 0;
            range = uint.MaxValue;
        }

        public static void RangeEncoderNormalize(Stream stream)
        {
            while ((low ^ (low + range)) < RangeTop || range < RangeBottom && ((range = (uint) -low & (RangeBottom - 1)) != 0 || true))
            {
                stream.WriteByte((byte) (low >> 24));
                range <<= 8;
                low <<= 8;
            }
        }

        public static void RangeEncodeSymbol()
        {
            low += LowCount * (range /= Scale);
            range *= HighCount - LowCount;
        }

        public static void RangeShiftEncodeSymbol(int rangeShift)
        {
            low += LowCount * (range >>= rangeShift);
            range *= HighCount - LowCount;
        }

        public static void RangeEncoderFlush(Stream stream)
        {
            for (uint index = 0; index < 4; index++)
            {
                stream.WriteByte((byte) (low >> 24));
                low <<= 8;
            }
        }

        public static void RangeDecoderInitialize(Stream stream)
        {
            low = 0;
            code = 0;
            range = uint.MaxValue;
            for (uint index = 0; index < 4; index++)
                code = (code << 8) | (byte) stream.ReadByte();
        }

        public static void RangeDecoderNormalize(Stream stream)
        {
            while ((low ^ (low + range)) < RangeTop || range < RangeBottom && ((range = (uint) -low & (RangeBottom - 1)) != 0 || true))
            {
                code = (code << 8) | (byte) stream.ReadByte();
                range <<= 8;
                low <<= 8;
            }
        }

        public static uint RangeGetCurrentCount()
        {
            return (code - low) / (range /= Scale);
        }

        public static uint RangeGetCurrentShiftCount(int rangeShift)
        {
            return (code - low) / (range >>= rangeShift);
        }

        public static void RangeRemoveSubrange()
        {
            low += range * LowCount;
            range *= HighCount - LowCount;
        }
    }
}
