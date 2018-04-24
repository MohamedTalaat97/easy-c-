#region Using

using System;

#endregion

namespace PpmdSharp
{
    /// <summary>
    /// The PPM context structure.  This is tightly coupled with <see cref="Model"/>.
    /// </summary>
    /// <remarks>
    /// <para>
    /// This must be a structure rather than a class because several places in the associated code assume that
    /// <see cref="PpmContext"/> is a value type (meaning that assignment creates a completely new copy of
    /// the instance rather than just copying a reference to the same instance).
    /// </para>
    /// <para>
    /// Note that <see cref="Address"/> is a field rather than a property for performance reasons.
    /// </para>
    /// </remarks>
    internal static partial class Model
    {
        /// <summary>
        /// The structure which represents the current PPM context.  This is 12 bytes in size.
        /// </summary>
        internal struct PpmContext
        {
            public uint Address;
            public static byte[] Memory;
            public static readonly PpmContext Zero = new PpmContext(0);
            public const int Size = 12;
            private static PpmState[] decodeStates = new PpmState[256];

            /// <summary>
            /// Initializes a new instance of the <see cref="PpmContext"/> structure.
            /// </summary>
            /// <param name="address"></param>
            public PpmContext(uint address)
            {
                Address = address;
            }

            /// <summary>
            /// Gets or sets the number statistics.
            /// </summary>
            public byte NumberStatistics
            {
                get { return Memory[Address]; }
                set { Memory[Address] = value; }
            }

            /// <summary>
            /// Gets or sets the flags.
            /// </summary>
            public byte Flags
            {
                get { return Memory[Address + 1]; }
                set { Memory[Address + 1] = value; }
            }

            /// <summary>
            /// Gets or sets the summary frequency.
            /// </summary>
            public ushort SummaryFrequency
            {
                get { return (ushort) (((ushort) Memory[Address + 2]) | ((ushort) Memory[Address + 3]) << 8); }
                set
                {
                    Memory[Address + 2] = (byte) value;
                    Memory[Address + 3] = (byte) (value >> 8);
                }
            }

            /// <summary>
            /// Gets or sets the statistics.
            /// </summary>
            public PpmState Statistics
            {
                get { return new PpmState(((uint) Memory[Address + 4]) | ((uint) Memory[Address + 5]) << 8 | ((uint) Memory[Address + 6]) << 16 | ((uint) Memory[Address + 7]) << 24); }
                set
                {
                    Memory[Address + 4] = (byte) value.Address;
                    Memory[Address + 5] = (byte) (value.Address >> 8);
                    Memory[Address + 6] = (byte) (value.Address >> 16);
                    Memory[Address + 7] = (byte) (value.Address >> 24);
                }
            }

            /// <summary>
            /// Gets or sets the suffix.
            /// </summary>
            public PpmContext Suffix
            {
                get { return new PpmContext(((uint) Memory[Address + 8]) | ((uint) Memory[Address + 9]) << 8 | ((uint) Memory[Address + 10]) << 16 | ((uint) Memory[Address + 11]) << 24); }
                set
                {
                    Memory[Address + 8] = (byte) value.Address;
                    Memory[Address + 9] = (byte) (value.Address >> 8);
                    Memory[Address + 10] = (byte) (value.Address >> 16);
                    Memory[Address + 11] = (byte) (value.Address >> 24);
                }
            }

            /// <summary>
            /// The first PPM state associated with the PPM context.
            /// </summary>
            /// <remarks>
            /// <para>
            /// The first PPM state overlaps this PPM context instance (the SummaryFrequency and Statistics members
            /// of PpmContext use 6 bytes and so can therefore fit into the space used by the Symbol, Frequency and
            /// Successor members of PpmState, since they also add up to 6 bytes).
            /// </para>
            /// <para>
            /// PpmContext (SummaryFrequency and Statistics use 6 bytes)
            ///     1 NumberStatistics
            ///     1 Flags
            ///     2 SummaryFrequency
            ///     4 Statistics (pointer to PpmState)
            ///     4 Suffix (pointer to PpmContext)
            /// </para>
            /// <para>
            /// PpmState (total of 6 bytes)
            ///     1 Symbol
            ///     1 Frequency
            ///     4 Successor (pointer to PpmContext)
            /// </para>
            /// </remarks>
            /// <returns></returns>
            public PpmState FirstState
            {
                get { return new PpmState(Address + 2); }
            }

            /// <summary>
            /// Gets or sets the symbol of the first PPM state.  This is provided for convenience.  The same
            /// information can be obtained using the Symbol property on the PPM state provided by the
            /// <see cref="FirstState"/> property.
            /// </summary>
            [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Performance", "CA1811:AvoidUncalledPrivateCode", Justification = "The property getter is provided for completeness.")]
            public byte FirstStateSymbol
            {
                get { return Memory[Address + 2]; }
                set { Memory[Address + 2] = value; }
            }

            /// <summary>
            /// Gets or sets the frequency of the first PPM state.  This is provided for convenience.  The same
            /// information can be obtained using the Frequency property on the PPM state provided by the
            /// <see cref="FirstState"/> property.
            /// </summary>
            public byte FirstStateFrequency
            {
                get { return Memory[Address + 3]; }
                set { Memory[Address + 3] = value; }
            }

            /// <summary>
            /// Gets or sets the successor of the first PPM state.  This is provided for convenience.  The same
            /// information can be obtained using the Successor property on the PPM state provided by the
            /// <see cref="FirstState"/> property.
            /// </summary>
            [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Performance", "CA1811:AvoidUncalledPrivateCode", Justification = "The property getter is provided for completeness.")]
            public PpmContext FirstStateSuccessor
            {
                get { return new PpmContext(((uint) Memory[Address + 4]) | ((uint) Memory[Address + 5]) << 8 | ((uint) Memory[Address + 6]) << 16 | ((uint) Memory[Address + 7]) << 24); }
                set
                {
                    Memory[Address + 4] = (byte) value.Address;
                    Memory[Address + 5] = (byte) (value.Address >> 8);
                    Memory[Address + 6] = (byte) (value.Address >> 16);
                    Memory[Address + 7] = (byte) (value.Address >> 24);
                }
            }

            /// <summary>
            /// Allow a pointer to be implicitly converted to a PPM context.
            /// </summary>
            /// <param name="pointer"></param>
            /// <returns></returns>
            public static implicit operator PpmContext(Pointer pointer)
            {
                return new PpmContext(pointer.Address);
            }

            /// <summary>
            /// Allow pointer-like addition on a PPM context.
            /// </summary>
            /// <param name="context"></param>
            /// <param name="offset"></param>
            /// <returns></returns>
            public static PpmContext operator +(PpmContext context, int offset)
            {
                context.Address = (uint) (context.Address + offset * Size);
                return context;
            }

            /// <summary>
            /// Allow pointer-like subtraction on a PPM context.
            /// </summary>
            /// <param name="context"></param>
            /// <param name="offset"></param>
            /// <returns></returns>
            public static PpmContext operator -(PpmContext context, int offset)
            {
                context.Address = (uint) (context.Address - offset * Size);
                return context;
            }

            /// <summary>
            /// Compare two PPM contexts.
            /// </summary>
            /// <param name="context1"></param>
            /// <param name="context2"></param>
            /// <returns></returns>
            public static bool operator <=(PpmContext context1, PpmContext context2)
            {
                return context1.Address <= context2.Address;
            }

            /// <summary>
            /// Compare two PPM contexts.
            /// </summary>
            /// <param name="context1"></param>
            /// <param name="context2"></param>
            /// <returns></returns>
            public static bool operator >=(PpmContext context1, PpmContext context2)
            {
                return context1.Address >= context2.Address;
            }

            /// <summary>
            /// Compare two PPM contexts.
            /// </summary>
            /// <param name="context1"></param>
            /// <param name="context2"></param>
            /// <returns></returns>
            public static bool operator ==(PpmContext context1, PpmContext context2)
            {
                return context1.Address == context2.Address;
            }

            /// <summary>
            /// Compare two PPM contexts.
            /// </summary>
            /// <param name="context1"></param>
            /// <param name="context2"></param>
            /// <returns></returns>
            public static bool operator !=(PpmContext context1, PpmContext context2)
            {
                return context1.Address != context2.Address;
            }

            /// <summary>
            /// Indicates whether this instance and a specified object are equal.
            /// </summary>
            /// <returns>true if obj and this instance are the same type and represent the same value; otherwise, false.</returns>
            /// <param name="obj">Another object to compare to.</param>
            public override bool Equals(object obj)
            {
                if (obj is PpmContext)
                {
                    PpmContext context = (PpmContext) obj;
                    return context.Address == Address;
                }
                return base.Equals(obj);
            }

            /// <summary>
            /// Returns the hash code for this instance.
            /// </summary>
            /// <returns>A 32-bit signed integer that is the hash code for this instance.</returns>
            public override int GetHashCode()
            {
                return Address.GetHashCode();
            }

            public void EncodeBinarySymbol(int symbol)
            {
                PpmState state = FirstState;
                int index1 = probabilities[state.Frequency - 1];
                int index2 = numberStatisticsToBinarySummaryIndex[Suffix.NumberStatistics] + previousSuccess + Flags + ((runLength >> 26) & 0x20);

                if (state.Symbol == symbol)
                {
                    foundState = state;
                    state.Frequency += (byte) ((state.Frequency < 196) ? 1 : 0);
                    Coder.LowCount = 0;
                    Coder.HighCount = binarySummary[index1, index2];
                    binarySummary[index1, index2] += (ushort) (Interval - Mean(binarySummary[index1, index2], PeriodBitCount, 2));
                    previousSuccess = 1;
                    runLength++;
                }
                else
                {
                    Coder.LowCount = binarySummary[index1, index2];
                    binarySummary[index1, index2] -= (ushort) Mean(binarySummary[index1, index2], PeriodBitCount, 2);
                    Coder.HighCount = BinaryScale;
                    initialEscape = ExponentialEscapes[binarySummary[index1, index2] >> 10];
                    characterMask[state.Symbol] = escapeCount;
                    previousSuccess = 0;
                    numberMasked = 0;
                    foundState = PpmState.Zero;
                }
            }

            public void EncodeSymbol1(int symbol)
            {
                uint lowCount;
                uint index = Statistics.Symbol;
                PpmState state = Statistics;
                Coder.Scale = SummaryFrequency;
                if (index == symbol)
                {
                    Coder.HighCount = state.Frequency;
                    previousSuccess = (byte) ((2 * Coder.HighCount >= Coder.Scale) ? 1 : 0);
                    foundState = state;
                    foundState.Frequency += 4;
                    SummaryFrequency += 4;
                    runLength += previousSuccess;
                    if (state.Frequency > MaximumFrequency)
                        Rescale();
                    Coder.LowCount = 0;
                    return;
                }

                lowCount = state.Frequency;
                index = NumberStatistics;
                previousSuccess = 0;
                while ((++state).Symbol != symbol)
                {
                    lowCount += state.Frequency;
                    if (--index == 0)
                    {
                        Coder.LowCount = lowCount;
                        characterMask[state.Symbol] = escapeCount;
                        numberMasked = NumberStatistics;
                        index = NumberStatistics;
                        foundState = PpmState.Zero;
                        do
                        {
                            characterMask[(--state).Symbol] = escapeCount;
                        } while (--index != 0);
                        Coder.HighCount = Coder.Scale;
                        return;
                    }
                }
                Coder.HighCount = (Coder.LowCount = lowCount) + state.Frequency;
                Update1(state);
            }

            public void EncodeSymbol2(int symbol)
            {
                See2Context see2Context = MakeEscapeFrequency();
                uint currentSymbol;
                uint lowCount = 0;
                uint index = (uint) (NumberStatistics - numberMasked);
                PpmState state = Statistics - 1;

                do
                {
                    do
                    {
                        currentSymbol = state[1].Symbol;
                        state++;
                    } while (characterMask[currentSymbol] == escapeCount);
                    characterMask[currentSymbol] = escapeCount;
                    if (currentSymbol == symbol)
                        goto SymbolFound;
                    lowCount += state.Frequency;
                } while (--index != 0);

                Coder.LowCount = lowCount;
                Coder.Scale += Coder.LowCount;
                Coder.HighCount = Coder.Scale;
                see2Context.Summary += (ushort) Coder.Scale;
                numberMasked = NumberStatistics;
                return;

            SymbolFound:
                Coder.LowCount = lowCount;
                lowCount += state.Frequency;
                Coder.HighCount = lowCount;
                for (PpmState p1 = state; --index != 0; )
                {
                    do
                    {
                        currentSymbol = p1[1].Symbol;
                        p1++;
                    } while (characterMask[currentSymbol] == escapeCount);
                    lowCount += p1.Frequency;
                }
                Coder.Scale += lowCount;
                see2Context.Update();
                Update2(state);
            }

            public void DecodeBinarySymbol()
            {
                PpmState state = FirstState;
                int index1 = probabilities[state.Frequency - 1];
                int index2 = numberStatisticsToBinarySummaryIndex[Suffix.NumberStatistics] + previousSuccess + Flags + ((runLength >> 26) & 0x20);

                if (Coder.RangeGetCurrentShiftCount(TotalBitCount) < binarySummary[index1, index2])
                {
                    foundState = state;
                    state.Frequency += (byte) ((state.Frequency < 196) ? 1 : 0);
                    Coder.LowCount = 0;
                    Coder.HighCount = binarySummary[index1, index2];
                    binarySummary[index1, index2] += (ushort) (Interval - Mean(binarySummary[index1, index2], PeriodBitCount, 2));
                    previousSuccess = 1;
                    runLength++;
                }
                else
                {
                    Coder.LowCount = binarySummary[index1, index2];
                    binarySummary[index1, index2] -= (ushort) Mean(binarySummary[index1, index2], PeriodBitCount, 2);
                    Coder.HighCount = BinaryScale;
                    initialEscape = ExponentialEscapes[binarySummary[index1, index2] >> 10];
                    characterMask[state.Symbol] = escapeCount;
                    previousSuccess = 0;
                    numberMasked = 0;
                    foundState = PpmState.Zero;
                }
            }

            public void DecodeSymbol1()
            {
                uint index;
                uint count;
                uint highCount = Statistics.Frequency;
                PpmState state = Statistics;
                Coder.Scale = SummaryFrequency;

                count = Coder.RangeGetCurrentCount();
                if (count < highCount)
                {
                    Coder.HighCount = highCount;
                    previousSuccess = (byte) ((2 * Coder.HighCount >= Coder.Scale) ? 1 : 0);
                    foundState = state;
                    highCount += 4;
                    foundState.Frequency = (byte) highCount;
                    SummaryFrequency += 4;
                    runLength += previousSuccess;
                    if (highCount > MaximumFrequency)
                        Rescale();
                    Coder.LowCount = 0;
                    return;
                }

                index = NumberStatistics;
                previousSuccess = 0;
                while ((highCount += (++state).Frequency) <= count)
                {
                    if (--index == 0)
                    {
                        Coder.LowCount = highCount;
                        characterMask[state.Symbol] = escapeCount;
                        numberMasked = NumberStatistics;
                        index = NumberStatistics;
                        foundState = PpmState.Zero;
                        do
                        {
                            characterMask[(--state).Symbol] = escapeCount;
                        } while (--index != 0);
                        Coder.HighCount = Coder.Scale;
                        return;
                    }
                }
                Coder.HighCount = highCount;
                Coder.LowCount = Coder.HighCount - state.Frequency;
                Update1(state);
            }

            public void DecodeSymbol2()
            {
                See2Context see2Context = MakeEscapeFrequency();
                uint currentSymbol;
                uint count;
                uint highCount = 0;
                uint index = (uint) (NumberStatistics - numberMasked);
                uint stateIndex = 0;
                PpmState state = Statistics - 1;

                do
                {
                    do
                    {
                        currentSymbol = state[1].Symbol;
                        state++;
                    } while (characterMask[currentSymbol] == escapeCount);
                    highCount += state.Frequency;
                    decodeStates[stateIndex++] = state;  // note that decodeStates is a static array that is re-used on each call to this method (for performance reasons)
                } while (--index != 0);

                Coder.Scale += highCount;
                count = Coder.RangeGetCurrentCount();
                stateIndex = 0;
                state = decodeStates[stateIndex];
                if (count < highCount)
                {
                    highCount = 0;
                    while ((highCount += state.Frequency) <= count)
                        state = decodeStates[++stateIndex];
                    Coder.HighCount = highCount;
                    Coder.LowCount = Coder.HighCount - state.Frequency;
                    see2Context.Update();
                    Update2(state);
                }
                else
                {
                    Coder.LowCount = highCount;
                    Coder.HighCount = Coder.Scale;
                    index = (uint) (NumberStatistics - numberMasked);
                    numberMasked = NumberStatistics;
                    do
                    {
                        characterMask[decodeStates[stateIndex].Symbol] = escapeCount;
                        stateIndex++;
                    } while (--index != 0);
                    see2Context.Summary += (ushort) Coder.Scale;
                }
            }

            public void Update1(PpmState state)
            {
                foundState = state;
                foundState.Frequency += 4;
                SummaryFrequency += 4;
                if (state[0].Frequency > state[-1].Frequency)
                {
                    Swap(state[0], state[-1]);
                    foundState = --state;
                    if (state.Frequency > MaximumFrequency)
                        Rescale();
                }
            }

            public void Update2(PpmState state)
            {
                foundState = state;
                foundState.Frequency += 4;
                SummaryFrequency += 4;
                if (state.Frequency > MaximumFrequency)
                    Rescale();
                escapeCount++;
                runLength = initialRunLength;
            }

            public See2Context MakeEscapeFrequency()
            {
                uint numberStatistics = (uint) 2 * NumberStatistics;
                See2Context see2Context;

                if (NumberStatistics != 0xff)
                {
                    // Note that Flags is always in the range 0 .. 28 (this ensures that the index used for the second
                    // dimension of the see2Contexts array is always in the range 0 .. 31).

                    numberStatistics = Suffix.NumberStatistics;
                    int index1 = probabilities[NumberStatistics + 2] - 3;
                    int index2 = ((SummaryFrequency > 11 * (NumberStatistics + 1)) ? 1 : 0) + ((2 * NumberStatistics < numberStatistics + numberMasked) ? 2 : 0) + Flags;
                    see2Context = see2Contexts[index1, index2];
                    Coder.Scale = see2Context.Mean();
                }
                else
                {
                    see2Context = emptySee2Context;
                    Coder.Scale = 1;
                }

                return see2Context;
            }

            public void Rescale()
            {
                uint oldUnitCount;
                int adder;
                uint escapeFrequency;
                uint index = NumberStatistics;

                byte localSymbol;
                byte localFrequency;
                PpmContext localSuccessor;
                PpmState p1;
                PpmState state;

                for (state = foundState; state != Statistics; state--)
                    Swap(state[0], state[-1]);

                state.Frequency += 4;
                SummaryFrequency += 4;
                escapeFrequency = (uint) (SummaryFrequency - state.Frequency);
                adder = (orderFall != 0 || method > ModelRestorationMethod.Freeze) ? 1 : 0;
                state.Frequency = (byte) ((state.Frequency + adder) >> 1);
                SummaryFrequency = state.Frequency;

                do
                {
                    escapeFrequency -= (++state).Frequency;
                    state.Frequency = (byte) ((state.Frequency + adder) >> 1);
                    SummaryFrequency += state.Frequency;
                    if (state[0].Frequency > state[-1].Frequency)
                    {
                        p1 = state;
                        localSymbol = p1.Symbol;
                        localFrequency = p1.Frequency;
                        localSuccessor = p1.Successor;
                        do
                        {
                            Copy(p1[0], p1[-1]);
                        } while (localFrequency > (--p1)[-1].Frequency);
                        p1.Symbol = localSymbol;
                        p1.Frequency = localFrequency;
                        p1.Successor = localSuccessor;
                    }
                } while (--index != 0);

                if (state.Frequency == 0)
                {
                    do
                    {
                        index++;
                    } while ((--state).Frequency == 0);

                    escapeFrequency += index;
                    oldUnitCount = (uint) ((NumberStatistics + 2) >> 1);
                    NumberStatistics -= (byte) index;
                    if (NumberStatistics == 0)
                    {
                        localSymbol = Statistics.Symbol;
                        localFrequency = Statistics.Frequency;
                        localSuccessor = Statistics.Successor;
                        localFrequency = (byte) ((2 * localFrequency + escapeFrequency - 1) / escapeFrequency);
                        if (localFrequency > MaximumFrequency / 3)
                            localFrequency = (byte) (MaximumFrequency / 3);
                        Allocator.FreeUnits(Statistics, oldUnitCount);
                        FirstStateSymbol = localSymbol;
                        FirstStateFrequency = localFrequency;
                        FirstStateSuccessor = localSuccessor;
                        Flags = (byte) ((Flags & 0x10) + ((localSymbol >= 0x40) ? 0x08 : 0x00));
                        foundState = FirstState;
                        return;
                    }

                    Statistics = Allocator.ShrinkUnits(Statistics, oldUnitCount, (uint) ((NumberStatistics + 2) >> 1));
                    Flags &= 0xf7;
                    index = NumberStatistics;
                    state = Statistics;
                    Flags |= (byte) ((state.Symbol >= 0x40) ? 0x08 : 0x00);
                    do
                    {
                        Flags |= (byte) (((++state).Symbol >= 0x40) ? 0x08 : 0x00);
                    } while (--index != 0);
                }

                escapeFrequency -= (escapeFrequency >> 1);
                SummaryFrequency += (ushort) escapeFrequency;
                Flags |= 0x04;
                foundState = Statistics;
            }

            public void Refresh(uint oldUnitCount, bool scale)
            {
                int index = NumberStatistics;
                int escapeFrequency;
                int scaleValue = (scale ? 1 : 0);

                Statistics = Allocator.ShrinkUnits(Statistics, oldUnitCount, (uint) ((index + 2) >> 1));
                PpmState statistics = Statistics;
                Flags = (byte) ((Flags & (0x10 + (scale ? 0x04 : 0x00))) + ((statistics.Symbol >= 0x40) ? 0x08 : 0x00));
                escapeFrequency = SummaryFrequency - statistics.Frequency;
                statistics.Frequency = (byte) ((statistics.Frequency + scaleValue) >> scaleValue);
                SummaryFrequency = statistics.Frequency;

                do
                {
                    escapeFrequency -= (++statistics).Frequency;
                    statistics.Frequency = (byte) ((statistics.Frequency + scaleValue) >> scaleValue);
                    SummaryFrequency += statistics.Frequency;
                    Flags |= (byte) ((statistics.Symbol >= 0x40) ? 0x08 : 0x00);
                } while (--index != 0);

                escapeFrequency = (escapeFrequency + scaleValue) >> scaleValue;
                SummaryFrequency += (ushort) escapeFrequency;
            }

            public PpmContext CutOff(int order)
            {
                int index;
                PpmState state;

                if (NumberStatistics == 0)
                {
                    state = FirstState;
                    if ((Pointer) state.Successor >= Allocator.BaseUnit)
                    {
                        if (order < modelOrder)
                            state.Successor = state.Successor.CutOff(order + 1);
                        else
                            state.Successor = PpmContext.Zero;

                        if (state.Successor == PpmContext.Zero && order > OrderBound)
                        {
                            Allocator.SpecialFreeUnits(this);
                            return PpmContext.Zero;
                        }

                        return this;
                    }
                    else
                    {
                        Allocator.SpecialFreeUnits(this);
                        return PpmContext.Zero;
                    }
                }

                uint unitCount = (uint) ((NumberStatistics + 2) >> 1);
                Statistics = Allocator.MoveUnitsUp(Statistics, unitCount);
                index = NumberStatistics;
                for (state = Statistics + index; state >= Statistics; state--)
                {
                    if (state.Successor < Allocator.BaseUnit)
                    {
                        state.Successor = PpmContext.Zero;
                        Swap(state, Statistics[index--]);
                    }
                    else if (order < modelOrder)
                        state.Successor = state.Successor.CutOff(order + 1);
                    else
                        state.Successor = PpmContext.Zero;
                }

                if (index != NumberStatistics && order != 0)
                {
                    NumberStatistics = (byte) index;
                    state = Statistics;
                    if (index < 0)
                    {
                        Allocator.FreeUnits(state, unitCount);
                        Allocator.SpecialFreeUnits(this);
                        return PpmContext.Zero;
                    }
                    else if (index == 0)
                    {
                        Flags = (byte) ((Flags & 0x10) + ((state.Symbol >= 0x40) ? 0x08 : 0x00));
                        Copy(FirstState, state);
                        Allocator.FreeUnits(state, unitCount);
                        FirstStateFrequency = (byte) ((FirstStateFrequency + 11) >> 3);
                    }
                    else
                    {
                        Refresh(unitCount, SummaryFrequency > 16 * index);
                    }
                }

                return this;
            }

            public PpmContext RemoveBinaryContexts(int order)
            {
                if (NumberStatistics == 0)
                {
                    PpmState state = FirstState;
                    if ((Pointer) state.Successor >= Allocator.BaseUnit && order < modelOrder)
                        state.Successor = state.Successor.RemoveBinaryContexts(order + 1);
                    else
                        state.Successor = PpmContext.Zero;
                    if ((state.Successor == PpmContext.Zero) && (Suffix.NumberStatistics == 0 || Suffix.Flags == 0xff))
                    {
                        Allocator.FreeUnits(this, 1);
                        return PpmContext.Zero;
                    }
                    else
                    {
                        return this;
                    }
                }

                for (PpmState state = Statistics + NumberStatistics; state >= Statistics; state--)
                {
                    if ((Pointer) state.Successor >= Allocator.BaseUnit && order < modelOrder)
                        state.Successor = state.Successor.RemoveBinaryContexts(order + 1);
                    else
                        state.Successor = PpmContext.Zero;
                }

                return this;
            }
        }
    }
}
