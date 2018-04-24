#region Using

using System;
using System.IO;

#endregion

namespace PpmdSharp
{
    /// <summary>
    /// The model.
    /// </summary>
    internal static partial class Model
    {
        public const uint Signature = 0x84acaf8fU;
        public const char Variant = 'I';
        public const int MaximumOrder = 16;  // maximum allowed model order

        private const byte UpperFrequency = 5;
        private const byte IntervalBitCount = 7;
        private const byte PeriodBitCount = 7;
        private const byte TotalBitCount = IntervalBitCount + PeriodBitCount;
        private const uint Interval = 1 << IntervalBitCount;
        private const uint BinaryScale = 1 << TotalBitCount;
        private const uint MaximumFrequency = 124;
        private const uint OrderBound = 9;

        private static See2Context[,] see2Contexts;
        private static See2Context emptySee2Context;
        private static PpmContext maximumContext;
        private static ushort[,] binarySummary = new ushort[25, 64];  // binary SEE-contexts
        private static byte[] numberStatisticsToBinarySummaryIndex = new byte[256];
        private static byte[] probabilities = new byte[260];
        private static byte[] characterMask = new byte[256];
        private static byte escapeCount;
        private static int modelOrder;
        private static int orderFall;
        private static int initialEscape;
        private static int initialRunLength;
        private static int runLength;
        private static byte previousSuccess;
        private static byte numberMasked;
        private static ModelRestorationMethod method;
        private static PpmState foundState;  // found next state transition

        private static readonly ushort[] InitialBinaryEscapes = { 0x3CDD, 0x1F3F, 0x59BF, 0x48F3, 0x64A1, 0x5ABC, 0x6632, 0x6051 };
        private static readonly byte[] ExponentialEscapes = { 25, 14, 9, 7, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2 };
        private static readonly uint[] crcTable =
        {
            0x00000000U, 0x77073096U, 0xee0e612cU, 0x990951baU, 0x076dc419U, 0x706af48fU, 0xe963a535U, 0x9e6495a3U,
            0x0edb8832U, 0x79dcb8a4U, 0xe0d5e91eU, 0x97d2d988U, 0x09b64c2bU, 0x7eb17cbdU, 0xe7b82d07U, 0x90bf1d91U,
            0x1db71064U, 0x6ab020f2U, 0xf3b97148U, 0x84be41deU, 0x1adad47dU, 0x6ddde4ebU, 0xf4d4b551U, 0x83d385c7U,
            0x136c9856U, 0x646ba8c0U, 0xfd62f97aU, 0x8a65c9ecU, 0x14015c4fU, 0x63066cd9U, 0xfa0f3d63U, 0x8d080df5U,
            0x3b6e20c8U, 0x4c69105eU, 0xd56041e4U, 0xa2677172U, 0x3c03e4d1U, 0x4b04d447U, 0xd20d85fdU, 0xa50ab56bU,
            0x35b5a8faU, 0x42b2986cU, 0xdbbbc9d6U, 0xacbcf940U, 0x32d86ce3U, 0x45df5c75U, 0xdcd60dcfU, 0xabd13d59U,
            0x26d930acU, 0x51de003aU, 0xc8d75180U, 0xbfd06116U, 0x21b4f4b5U, 0x56b3c423U, 0xcfba9599U, 0xb8bda50fU,
            0x2802b89eU, 0x5f058808U, 0xc60cd9b2U, 0xb10be924U, 0x2f6f7c87U, 0x58684c11U, 0xc1611dabU, 0xb6662d3dU,
            0x76dc4190U, 0x01db7106U, 0x98d220bcU, 0xefd5102aU, 0x71b18589U, 0x06b6b51fU, 0x9fbfe4a5U, 0xe8b8d433U,
            0x7807c9a2U, 0x0f00f934U, 0x9609a88eU, 0xe10e9818U, 0x7f6a0dbbU, 0x086d3d2dU, 0x91646c97U, 0xe6635c01U,
            0x6b6b51f4U, 0x1c6c6162U, 0x856530d8U, 0xf262004eU, 0x6c0695edU, 0x1b01a57bU, 0x8208f4c1U, 0xf50fc457U,
            0x65b0d9c6U, 0x12b7e950U, 0x8bbeb8eaU, 0xfcb9887cU, 0x62dd1ddfU, 0x15da2d49U, 0x8cd37cf3U, 0xfbd44c65U,
            0x4db26158U, 0x3ab551ceU, 0xa3bc0074U, 0xd4bb30e2U, 0x4adfa541U, 0x3dd895d7U, 0xa4d1c46dU, 0xd3d6f4fbU,
            0x4369e96aU, 0x346ed9fcU, 0xad678846U, 0xda60b8d0U, 0x44042d73U, 0x33031de5U, 0xaa0a4c5fU, 0xdd0d7cc9U,
            0x5005713cU, 0x270241aaU, 0xbe0b1010U, 0xc90c2086U, 0x5768b525U, 0x206f85b3U, 0xb966d409U, 0xce61e49fU,
            0x5edef90eU, 0x29d9c998U, 0xb0d09822U, 0xc7d7a8b4U, 0x59b33d17U, 0x2eb40d81U, 0xb7bd5c3bU, 0xc0ba6cadU,
            0xedb88320U, 0x9abfb3b6U, 0x03b6e20cU, 0x74b1d29aU, 0xead54739U, 0x9dd277afU, 0x04db2615U, 0x73dc1683U,
            0xe3630b12U, 0x94643b84U, 0x0d6d6a3eU, 0x7a6a5aa8U, 0xe40ecf0bU, 0x9309ff9dU, 0x0a00ae27U, 0x7d079eb1U,
            0xf00f9344U, 0x8708a3d2U, 0x1e01f268U, 0x6906c2feU, 0xf762575dU, 0x806567cbU, 0x196c3671U, 0x6e6b06e7U,
            0xfed41b76U, 0x89d32be0U, 0x10da7a5aU, 0x67dd4accU, 0xf9b9df6fU, 0x8ebeeff9U, 0x17b7be43U, 0x60b08ed5U,
            0xd6d6a3e8U, 0xa1d1937eU, 0x38d8c2c4U, 0x4fdff252U, 0xd1bb67f1U, 0xa6bc5767U, 0x3fb506ddU, 0x48b2364bU,
            0xd80d2bdaU, 0xaf0a1b4cU, 0x36034af6U, 0x41047a60U, 0xdf60efc3U, 0xa867df55U, 0x316e8eefU, 0x4669be79U,
            0xcb61b38cU, 0xbc66831aU, 0x256fd2a0U, 0x5268e236U, 0xcc0c7795U, 0xbb0b4703U, 0x220216b9U, 0x5505262fU,
            0xc5ba3bbeU, 0xb2bd0b28U, 0x2bb45a92U, 0x5cb36a04U, 0xc2d7ffa7U, 0xb5d0cf31U, 0x2cd99e8bU, 0x5bdeae1dU,
            0x9b64c2b0U, 0xec63f226U, 0x756aa39cU, 0x026d930aU, 0x9c0906a9U, 0xeb0e363fU, 0x72076785U, 0x05005713U,
            0x95bf4a82U, 0xe2b87a14U, 0x7bb12baeU, 0x0cb61b38U, 0x92d28e9bU, 0xe5d5be0dU, 0x7cdcefb7U, 0x0bdbdf21U,
            0x86d3d2d4U, 0xf1d4e242U, 0x68ddb3f8U, 0x1fda836eU, 0x81be16cdU, 0xf6b9265bU, 0x6fb077e1U, 0x18b74777U,
            0x88085ae6U, 0xff0f6a70U, 0x66063bcaU, 0x11010b5cU, 0x8f659effU, 0xf862ae69U, 0x616bffd3U, 0x166ccf45U,
            0xa00ae278U, 0xd70dd2eeU, 0x4e048354U, 0x3903b3c2U, 0xa7672661U, 0xd06016f7U, 0x4969474dU, 0x3e6e77dbU,
            0xaed16a4aU, 0xd9d65adcU, 0x40df0b66U, 0x37d83bf0U, 0xa9bcae53U, 0xdebb9ec5U, 0x47b2cf7fU, 0x30b5ffe9U,
            0xbdbdf21cU, 0xcabac28aU, 0x53b39330U, 0x24b4a3a6U, 0xbad03605U, 0xcdd70693U, 0x54de5729U, 0x23d967bfU,
            0xb3667a2eU, 0xc4614ab8U, 0x5d681b02U, 0x2a6f2b94U, 0xb40bbe37U, 0xc30c8ea1U, 0x5a05df1bU, 0x2d02ef8dU
        };

        #region Public Methods

        static Model()
        {
            // Construct the conversion table for number statistics.  Initially it will contain the following values.
            //
            // 0 2 4 4 4 4 4 4 4 4 4 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
            // 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
            // 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
            // 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
            // 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
            // 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
            // 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6
            // 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6

            numberStatisticsToBinarySummaryIndex[0] = 2 * 0;
            numberStatisticsToBinarySummaryIndex[1] = 2 * 1;
            for (int index = 2; index < 11; index++)
                numberStatisticsToBinarySummaryIndex[index] = 2 * 2;
            for (int index = 11; index < 256; index++)
                numberStatisticsToBinarySummaryIndex[index] = 2 * 3;

            // Construct the probability table.  Initially it will contain the following values (depending on the value of
            // the upper frequency).
            //
            // 00 01 02 03 04 05 06 06 07 07 07 08 08 08 08 09 09 09 09 09 10 10 10 10 10 10 11 11 11 11 11 11
            // 11 12 12 12 12 12 12 12 12 13 13 13 13 13 13 13 13 13 14 14 14 14 14 14 14 14 14 14 15 15 15 15
            // 15 15 15 15 15 15 15 16 16 16 16 16 16 16 16 16 16 16 16 17 17 17 17 17 17 17 17 17 17 17 17 17
            // 18 18 18 18 18 18 18 18 18 18 18 18 18 18 19 19 19 19 19 19 19 19 19 19 19 19 19 19 19 20 20 20
            // 20 20 20 20 20 20 20 20 20 20 20 20 20 21 21 21 21 21 21 21 21 21 21 21 21 21 21 21 21 21 22 22
            // 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 22 23 23 23 23 23 23 23 23 23 23 23 23 23 23 23 23
            // 23 23 23 24 24 24 24 24 24 24 24 24 24 24 24 24 24 24 24 24 24 24 24 25 25 25 25 25 25 25 25 25
            // 25 25 25 25 25 25 25 25 25 25 25 25 26 26 26 26 26 26 26 26 26 26 26 26 26 26 26 26 26 26 26 26
            // 26 26 27 27

            uint count = 1;
            uint step = 1;
            uint probability = UpperFrequency;

            for (int index = 0; index < UpperFrequency; index++)
                probabilities[index] = (byte) index;

            for (int index = UpperFrequency; index < 260; index++)
            {
                probabilities[index] = (byte) probability;
                count--;
                if (count == 0)
                {
                    step++;
                    count = step;
                    probability++;
                }
            }

            // Create the context array.

            see2Contexts = new See2Context[24, 32];
            for (int index1 = 0; index1 < 24; index1++)
                for (int index2 = 0; index2 < 32; index2++)
                    see2Contexts[index1, index2] = new See2Context();

            // Set the signature (identifying the algorithm).

            emptySee2Context = new See2Context();
            emptySee2Context.Summary = (ushort) (Signature & 0x0000ffff);
            emptySee2Context.Shift = (byte) ((Signature >> 16) & 0x000000ff);
            emptySee2Context.Count = (byte) (Signature >> 24);
        }

        /// <summary>
        /// Encode (ie. compress) a given source stream, writing the encoded result to the target stream.
        /// </summary>
        /// <param name="target"></param>
        /// <param name="source"></param>
        /// <param name="modelOrder"></param>
        /// <param name="modelRestorationMethod"></param>
        /// <returns></returns>
        public static uint Encode(Stream target, Stream source, int modelOrder, ModelRestorationMethod modelRestorationMethod, Options options)
        {
            if (target == null)
                throw new ArgumentNullException("target");

            if (source == null)
                throw new ArgumentNullException("source");

            uint checksum = 0;

            Coder.RangeEncoderInitialize();
            StartModel(modelOrder, modelRestorationMethod);
            PpmContext minimumContext;

            while (true)
            {
                minimumContext = maximumContext;
                byte numberStatistics = minimumContext.NumberStatistics;

                int c = source.ReadByte();

                if ((options & Options.Checksum) != 0 && c != -1)
                    checksum = crcTable[(checksum ^ c) & 0xff] ^ (checksum >> 8);

                if (numberStatistics != 0)
                {
                    minimumContext.EncodeSymbol1(c);
                    Coder.RangeEncodeSymbol();
                }
                else
                {
                    minimumContext.EncodeBinarySymbol(c);
                    Coder.RangeShiftEncodeSymbol(TotalBitCount);
                }

                while (foundState == PpmState.Zero)
                {
                    Coder.RangeEncoderNormalize(target);
                    do
                    {
                        orderFall++;
                        minimumContext = minimumContext.Suffix;
                        if (minimumContext == PpmContext.Zero)
                            goto StopEncoding;
                    } while (minimumContext.NumberStatistics == numberMasked);
                    minimumContext.EncodeSymbol2(c);
                    Coder.RangeEncodeSymbol();
                }

                if (orderFall == 0 && (Pointer) foundState.Successor >= Allocator.BaseUnit)
                {
                    maximumContext = foundState.Successor;
                }
                else
                {
                    UpdateModel(minimumContext);
                    if (escapeCount == 0)
                        ClearMask();
                }

                Coder.RangeEncoderNormalize(target);
            }

        StopEncoding:
            Coder.RangeEncoderFlush(target);

            return checksum;
        }

        /// <summary>
        /// Dencode (ie. decompress) a given source stream, writing the decoded result to the target stream.
        /// </summary>
        /// <param name="target"></param>
        /// <param name="source"></param>
        /// <param name="modelOrder"></param>
        /// <param name="modelRestorationMethod"></param>
        /// <returns></returns>
        public static uint Decode(Stream target, Stream source, int modelOrder, ModelRestorationMethod modelRestorationMethod, Options options)
        {
            if (target == null)
                throw new ArgumentNullException("target");

            if (source == null)
                throw new ArgumentNullException("source");

            uint checksum = 0;

            Coder.RangeDecoderInitialize(source);
            StartModel(modelOrder, modelRestorationMethod);
            PpmContext minimumContext = maximumContext;
            byte numberStatistics = minimumContext.NumberStatistics;

            while (true)
            {
                if (numberStatistics != 0)
                    minimumContext.DecodeSymbol1();
                else
                    minimumContext.DecodeBinarySymbol();

                Coder.RangeRemoveSubrange();

                while (foundState == PpmState.Zero)
                {
                    Coder.RangeDecoderNormalize(source);
                    do
                    {
                        orderFall++;
                        minimumContext = minimumContext.Suffix;
                        if (minimumContext == PpmContext.Zero)
                            goto StopDecoding;
                    } while (minimumContext.NumberStatistics == numberMasked);
                    minimumContext.DecodeSymbol2();
                    Coder.RangeRemoveSubrange();
                }

                target.WriteByte(foundState.Symbol);

                if ((options & Options.Checksum) != 0)
                    checksum = crcTable[(checksum ^ foundState.Symbol) & 0xff] ^ (checksum >> 8);

                if (orderFall == 0 && (Pointer) foundState.Successor >= Allocator.BaseUnit)
                {
                    maximumContext = foundState.Successor;
                }
                else
                {
                    UpdateModel(minimumContext);
                    if (escapeCount == 0)
                        ClearMask();
                }

                minimumContext = maximumContext;
                numberStatistics = minimumContext.NumberStatistics;
                Coder.RangeDecoderNormalize(source);
            }

        StopDecoding:
            return checksum;
        }

        #endregion

        #region Private Methods

        /// <summary>
        /// Initialise the model (unless the model order is set to 1 in which case the model should be cleared so that
        /// the statistics are carried over, allowing "solid" mode compression).
        /// </summary>
        private static void StartModel(int modelOrder, ModelRestorationMethod modelRestorationMethod)
        {
            Array.Clear(characterMask, 0, characterMask.Length);
            escapeCount = 1;

            // Compress in "solid" mode if the model order value is set to 1 (this will examine the current PPM context
            // structures to determine the value of orderFall).

            if (modelOrder < 2)
            {
                orderFall = Model.modelOrder;
                for (PpmContext context = maximumContext; context.Suffix != PpmContext.Zero; context = context.Suffix)
                    orderFall--;
                return;
            }

            Model.modelOrder = modelOrder;
            orderFall = modelOrder;
            method = modelRestorationMethod;
            Allocator.Initialize();
            initialRunLength = -((modelOrder < 12) ? modelOrder : 12) - 1;
            runLength = initialRunLength;

            // Allocate the context structure.

            maximumContext = Allocator.AllocateContext();
            maximumContext.Suffix = PpmContext.Zero;
            maximumContext.NumberStatistics = 255;
            maximumContext.SummaryFrequency = (ushort) (maximumContext.NumberStatistics + 2);
            maximumContext.Statistics = Allocator.AllocateUnits(256 / 2);  // allocates enough space for 256 PPM states (each is 6 bytes)

            previousSuccess = 0;
            for (int index = 0; index < 256; index++)
            {
                PpmState state = maximumContext.Statistics[index];
                state.Symbol = (byte) index;
                state.Frequency = 1;
                state.Successor = PpmContext.Zero;
            }

            uint probability = 0;
            for (int index1 = 0; probability < 25; probability++)
            {
                while (probabilities[index1] == probability)
                    index1++;
                for (int index2 = 0; index2 < 8; index2++)
                    binarySummary[probability, index2] = (ushort) (BinaryScale - InitialBinaryEscapes[index2] / (index1 + 1));
                for (int index2 = 8; index2 < 64; index2 += 8)
                    for (int index3 = 0; index3 < 8; index3++)
                        binarySummary[probability, index2 + index3] = binarySummary[probability, index3];
            }

            probability = 0;
            for (uint index1 = 0; probability < 24; probability++)
            {
                while (probabilities[index1 + 3] == probability + 3)
                    index1++;
                for (int index2 = 0; index2 < 32; index2++)
                    see2Contexts[probability, index2].Initialize(2 * index1 + 5);
            }
        }

        private static void UpdateModel(PpmContext minimumContext)
        {
            PpmState state = PpmState.Zero;
            PpmContext Successor;
            PpmContext currentContext = maximumContext;
            uint numberStatistics;
            uint ns1;
            uint cf;
            uint sf;
            uint s0;
            uint foundStateFrequency = foundState.Frequency;
            byte foundStateSymbol = foundState.Symbol;
            byte symbol;
            byte flag;

            PpmContext foundStateSuccessor = foundState.Successor;
            PpmContext context = minimumContext.Suffix;

            if ((foundStateFrequency < MaximumFrequency / 4) && (context != PpmContext.Zero))
            {
                if (context.NumberStatistics != 0)
                {
                    state = context.Statistics;
                    if (state.Symbol != foundStateSymbol)
                    {
                        do
                        {
                            symbol = state[1].Symbol;
                            state++;
                        } while (symbol != foundStateSymbol);
                        if (state[0].Frequency >= state[-1].Frequency)
                        {
                            Swap(state[0], state[-1]);
                            state--;
                        }
                    }
                    cf = (uint) ((state.Frequency < MaximumFrequency - 9) ? 2 : 0);
                    state.Frequency += (byte) cf;
                    context.SummaryFrequency += (byte) cf;
                }
                else
                {
                    state = context.FirstState;
                    state.Frequency += (byte) ((state.Frequency < 32) ? 1 : 0);
                }
            }

            if (orderFall == 0 && foundStateSuccessor != PpmContext.Zero)
            {
                foundState.Successor = CreateSuccessors(true, state, minimumContext);
                if (foundState.Successor == PpmContext.Zero)
                    goto RestartModel;
                maximumContext = foundState.Successor;
                return;
            }

            Allocator.Text[0] = foundStateSymbol;
            Allocator.Text++;
            Successor = Allocator.Text;

            if (Allocator.Text >= Allocator.BaseUnit)
                goto RestartModel;

            if (foundStateSuccessor != PpmContext.Zero)
            {
                if (foundStateSuccessor < Allocator.BaseUnit)
                    foundStateSuccessor = CreateSuccessors(false, state, minimumContext);
            }
            else
            {
                foundStateSuccessor = ReduceOrder(state, minimumContext);
            }

            if (foundStateSuccessor == PpmContext.Zero)
                goto RestartModel;

            if (--orderFall == 0)
            {
                Successor = foundStateSuccessor;
                Allocator.Text -= (maximumContext != minimumContext) ? 1 : 0;
            }
            else if (method > ModelRestorationMethod.Freeze)
            {
                Successor = foundStateSuccessor;
                Allocator.Text = Allocator.Heap;
                orderFall = 0;
            }

            numberStatistics = minimumContext.NumberStatistics;
            s0 = minimumContext.SummaryFrequency - numberStatistics - foundStateFrequency;
            flag = (byte) ((foundStateSymbol >= 0x40) ? 0x08 : 0x00);
            for (; currentContext != minimumContext; currentContext = currentContext.Suffix)
            {
                ns1 = currentContext.NumberStatistics;
                if (ns1 != 0)
                {
                    if ((ns1 & 1) != 0)
                    {
                        state = Allocator.ExpandUnits(currentContext.Statistics, (ns1 + 1) >> 1);
                        if (state == PpmState.Zero)
                            goto RestartModel;
                        currentContext.Statistics = state;
                    }
                    currentContext.SummaryFrequency += (ushort) ((3 * ns1 + 1 < numberStatistics) ? 1 : 0);
                }
                else
                {
                    state = Allocator.AllocateUnits(1);
                    if (state == PpmState.Zero)
                        goto RestartModel;
                    Copy(state, currentContext.FirstState);
                    currentContext.Statistics = state;
                    if (state.Frequency < MaximumFrequency / 4 - 1)
                        state.Frequency += state.Frequency;
                    else
                        state.Frequency = (byte) (MaximumFrequency - 4);
                    currentContext.SummaryFrequency = (ushort) (state.Frequency + initialEscape + ((numberStatistics > 2) ? 1 : 0));
                }

                cf = (uint) (2 * foundStateFrequency * (currentContext.SummaryFrequency + 6));
                sf = s0 + currentContext.SummaryFrequency;

                if (cf < 6 * sf)
                {
                    cf = (uint) (1 + ((cf > sf) ? 1 : 0) + ((cf >= 4 * sf) ? 1 : 0));
                    currentContext.SummaryFrequency += 4;
                }
                else
                {
                    cf = (uint) (4 + ((cf > 9 * sf) ? 1 : 0) + ((cf > 12 * sf) ? 1 : 0) + ((cf > 15 * sf) ? 1 : 0));
                    currentContext.SummaryFrequency += (ushort) cf;
                }

                state = currentContext.Statistics + (++currentContext.NumberStatistics);
                state.Successor = Successor;
                state.Symbol = foundStateSymbol;
                state.Frequency = (byte) cf;
                currentContext.Flags |= flag;
            }

            maximumContext = foundStateSuccessor;
            return;

        RestartModel:
            RestoreModel(currentContext, minimumContext, foundStateSuccessor);
        }

        private static PpmContext CreateSuccessors(bool skip, PpmState state, PpmContext context)
        {
            PpmContext upBranch = foundState.Successor;
            PpmState[] states = new PpmState[MaximumOrder];
            uint stateIndex = 0;
            byte symbol = foundState.Symbol;

            if (!skip)
            {
                states[stateIndex++] = foundState;
                if (context.Suffix == PpmContext.Zero)
                    goto NoLoop;
            }

            bool gotoLoopEntry = false;
            if (state != PpmState.Zero)
            {
                context = context.Suffix;
                gotoLoopEntry = true;
            }
             
            do
            {
                if (gotoLoopEntry)
                {
                    gotoLoopEntry = false;
                    goto LoopEntry;
                }

                context = context.Suffix;
                if (context.NumberStatistics != 0)
                {
                    byte temporary;
                    state = context.Statistics;
                    if (state.Symbol != symbol)
                    {
                        do
                        {
                            temporary = state[1].Symbol;
                            state++;
                        } while (temporary != symbol);
                    }
                    temporary = (byte) ((state.Frequency < MaximumFrequency - 9) ? 1 : 0);
                    state.Frequency += temporary;
                    context.SummaryFrequency += temporary;
                }
                else
                {
                    state = context.FirstState;
                    state.Frequency += (byte) (((context.Suffix.NumberStatistics == 0) ? 1 : 0) & ((state.Frequency < 24) ? 1 : 0));
                }

        LoopEntry:
                if (state.Successor != upBranch)
                {
                    context = state.Successor;
                    break;
                }
                states[stateIndex++] = state;
            } while (context.Suffix != PpmContext.Zero);

        NoLoop:
            if (stateIndex == 0)
                return context;

            byte localNumberStatistics = 0;
            byte localFlags = (byte) ((symbol >= 0x40) ? 0x10 : 0x00);
            symbol = upBranch.NumberStatistics;
            byte localSymbol = symbol;
            byte localFrequency;
            PpmContext localSuccessor = ((Pointer) upBranch) + 1;
            localFlags |= (byte) ((symbol >= 0x40) ? 0x08 : 0x00);

            if (context.NumberStatistics != 0)
            {
                state = context.Statistics;
                if (state.Symbol != symbol)
                {
                    byte temporary;
                    do
                    {
                        temporary = state[1].Symbol;
                        state++;
                    } while (temporary != symbol);
                }
                uint cf = (uint) (state.Frequency - 1);
                uint s0 = (uint) (context.SummaryFrequency - context.NumberStatistics - cf);
                localFrequency = (byte) (1 + ((2 * cf <= s0) ? (uint) ((5 * cf > s0) ? 1 : 0) : ((cf + 2 * s0 - 3) / s0)));
            }
            else
            {
                localFrequency = context.FirstStateFrequency;
            }

            do
            {
                PpmContext currentContext = Allocator.AllocateContext();
                if (currentContext == PpmContext.Zero)
                    return PpmContext.Zero;
                currentContext.NumberStatistics = localNumberStatistics;
                currentContext.Flags = localFlags;
                currentContext.FirstStateSymbol = localSymbol;
                currentContext.FirstStateFrequency = localFrequency;
                currentContext.FirstStateSuccessor = localSuccessor;
                currentContext.Suffix = context;
                context = currentContext;
                states[--stateIndex].Successor = context;
            } while (stateIndex != 0);

            return context;
        }

        private static PpmContext ReduceOrder(PpmState state, PpmContext context)
        {
            PpmState currentState;
            PpmState[] states = new PpmState[MaximumOrder];
            uint stateIndex = 0;
            PpmContext currentContext = context;
            PpmContext UpBranch = Allocator.Text;
            byte temporary;
            byte symbol = foundState.Symbol;

            states[stateIndex++] = foundState;
            foundState.Successor = UpBranch;
            orderFall++;

            bool gotoLoopEntry = false;
            if (state != PpmState.Zero)
            {
                context = context.Suffix;
                gotoLoopEntry = true;
            }

            while (true)
            {
                if (gotoLoopEntry)
                {
                    gotoLoopEntry = false;
                    goto LoopEntry;
                }

                if (context.Suffix == PpmContext.Zero)
                {
                    if (method > ModelRestorationMethod.Freeze)
                    {
                        do
                        {
                            states[--stateIndex].Successor = context;
                        } while (stateIndex != 0);
                        Allocator.Text = Allocator.Heap + 1;
                        orderFall = 1;
                    }
                    return context;
                }

                context = context.Suffix;
                if (context.NumberStatistics != 0)
                {
                    state = context.Statistics;
                    if (state.Symbol != symbol)
                    {
                        do
                        {
                            temporary = state[1].Symbol;
                            state++;
                        } while (temporary != symbol);
                    }
                    temporary = (byte) ((state.Frequency < MaximumFrequency - 9) ? 2 : 0);
                    state.Frequency += temporary;
                    context.SummaryFrequency += temporary;
                }
                else
                {
                    state = context.FirstState;
                    state.Frequency += (byte) ((state.Frequency < 32) ? 1 : 0);
                }

            LoopEntry:
                if (state.Successor != PpmContext.Zero)
                    break;
                states[stateIndex++] = state;
                state.Successor = UpBranch;
                orderFall++;
            }

            if (method > ModelRestorationMethod.Freeze)
            {
                context = state.Successor;
                do
                {
                    states[--stateIndex].Successor = context;
                } while (stateIndex != 0);
                Allocator.Text = Allocator.Heap + 1;
                orderFall = 1;
                return context;
            }
            else if (state.Successor <= UpBranch)
            {
                currentState = foundState;
                foundState = state;
                state.Successor = CreateSuccessors(false, PpmState.Zero, context);
                foundState = currentState;
            }

            if (orderFall == 1 && currentContext == maximumContext)
            {
                foundState.Successor = state.Successor;
                Allocator.Text--;
            }

            return state.Successor;
        }

        private static void RestoreModel(PpmContext context, PpmContext minimumContext, PpmContext foundStateSuccessor)
        {
            PpmContext currentContext;

            Allocator.Text = Allocator.Heap;
            for (currentContext = maximumContext; currentContext != context; currentContext = currentContext.Suffix)
            {
                if (--(currentContext.NumberStatistics) == 0)
                {
                    currentContext.Flags = (byte) ((currentContext.Flags & 0x10) + ((currentContext.Statistics.Symbol >= 0x40) ? 0x08 : 0x00));
                    PpmState state = currentContext.Statistics;
                    Copy(currentContext.FirstState, state);
                    Allocator.SpecialFreeUnits(state);
                    currentContext.FirstStateFrequency = (byte) ((currentContext.FirstStateFrequency + 11) >> 3);
                }
                else
                {
                    currentContext.Refresh((uint) ((currentContext.NumberStatistics + 3) >> 1), false);
                }
            }

            for (; currentContext != minimumContext; currentContext = currentContext.Suffix)
            {
                if (currentContext.NumberStatistics == 0)
                    currentContext.FirstStateFrequency -= (byte) (currentContext.FirstStateFrequency >> 1);
                else if ((currentContext.SummaryFrequency += 4) > 128 + 4 * currentContext.NumberStatistics)
                    currentContext.Refresh((uint) ((currentContext.NumberStatistics + 2) >> 1), true);
            }

            if (method > ModelRestorationMethod.Freeze)
            {
                maximumContext = foundStateSuccessor;
                Allocator.GlueCount += (uint) (((Allocator.MemoryNodes[1].Stamp & 1) == 0) ? 1 : 0);
            }
            else if (method == ModelRestorationMethod.Freeze)
            {
                while (maximumContext.Suffix != PpmContext.Zero)
                    maximumContext = maximumContext.Suffix;

                maximumContext.RemoveBinaryContexts(0);
                method = (ModelRestorationMethod) (method + 1);
                Allocator.GlueCount = 0;
                orderFall = modelOrder;
            }
            else if (method == ModelRestorationMethod.Restart || Allocator.GetMemoryUsed() < (Allocator.AllocatorSize >> 1))
            {
                StartModel(modelOrder, method);
                escapeCount = 0;
            }
            else
            {
                while (maximumContext.Suffix != PpmContext.Zero)
                    maximumContext = maximumContext.Suffix;

                do
                {
                    maximumContext.CutOff(0);
                    Allocator.ExpandText();
                } while (Allocator.GetMemoryUsed() > 3 * (Allocator.AllocatorSize >> 2));

                Allocator.GlueCount = 0;
                orderFall = modelOrder;
            }
        }

        private static void Swap(PpmState state1, PpmState state2)
        {
            byte swapSymbol = state1.Symbol;
            byte swapFrequency = state1.Frequency;
            PpmContext swapSuccessor = state1.Successor;

            state1.Symbol = state2.Symbol;
            state1.Frequency = state2.Frequency;
            state1.Successor = state2.Successor;

            state2.Symbol = swapSymbol;
            state2.Frequency = swapFrequency;
            state2.Successor = swapSuccessor;
        }

        private static void Copy(PpmState state1, PpmState state2)
        {
            state1.Symbol = state2.Symbol;
            state1.Frequency = state2.Frequency;
            state1.Successor = state2.Successor;
        }

        private static int Mean(int sum, int shift, int round)
        {
            return (sum + (1 << (shift - round))) >> shift;
        }

        private static void ClearMask()
        {
            escapeCount = 1;
            Array.Clear(characterMask, 0, characterMask.Length);
        }

        #endregion
    }
}
