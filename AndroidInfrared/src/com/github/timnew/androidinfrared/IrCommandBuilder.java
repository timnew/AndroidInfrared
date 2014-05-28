package com.github.timnew.androidinfrared;

import java.util.ArrayList;
import java.util.List;

public class IrCommandBuilder {

    public static final long TOP_BIT_32 = 0x1L << 31;
    public static final long TOP_BIT_64 = 0x1L << 63;

    private final int frequency;
    private final List<Integer> buffer;
    private Boolean lastMark;

    public static IrCommandBuilder irCommandBuilder(int frequency) {
        return new IrCommandBuilder(frequency);
    }

    private IrCommandBuilder(int frequencyKHz) {
        this.frequency = frequencyKHz;

        buffer = new ArrayList<Integer>();
        buffer.add(frequencyKHz);

        lastMark = null;
    }

    private IrCommandBuilder appendSymbol(boolean mark, int interval) {
        if (lastMark == null || lastMark != mark) {
            buffer.add(interval);
            lastMark = mark;
        } else {
            int lastIndex = buffer.size() - 1;
            buffer.set(lastIndex, buffer.get(lastIndex) + interval);
        }

        return this;
    }

    public IrCommandBuilder mark(int interval) {
        return appendSymbol(true, interval);
    }

    public IrCommandBuilder space(int interval) {
        return appendSymbol(false, interval);
    }

    public IrCommandBuilder pair(int on, int off) {
        return mark(on).space(off);
    }

    public IrCommandBuilder reversePair(int off, int on) {
        return space(off).mark(on);
    }

    public IrCommandBuilder delay(int ms) {
        return space(ms);
    }

    public IrCommandBuilder sequence(SequenceDefinition definition, int length, int data) {
        return sequence(definition, TOP_BIT_32, length, data);
    }

    public IrCommandBuilder sequence(SequenceDefinition definition, int length, long data) {
        return sequence(definition, TOP_BIT_64, length, data);
    }

    public IrCommandBuilder sequence(SequenceDefinition definition, long topBit, int length, long data) {
        for (int index = 0; index < length; index++) {
            if ((data & topBit) != 0) {
                definition.one(this, index);
            } else {
                definition.zero(this, index);
            }

            data <<= 1;
        }

        return this;
    }

    public IrCommand build() {
        return new IrCommand(getFrequency(), buildSequence());
    }

    public int[] buildSequence() {
        return buildRaw(buffer);
    }

    public int getFrequency() {
        return frequency;
    }

    public List<Integer> getBuffer() {
        return buffer;
    }

    public static SequenceDefinition simpleSequence(final int oneMark, final int oneSpace, final int zeroMark, final int zeroSpace) {
        return new SequenceDefinition() {
            @Override
            public void one(IrCommandBuilder builder, int index) {
                builder.pair(oneMark, oneSpace);
            }

            @Override
            public void zero(IrCommandBuilder builder, int index) {
                builder.pair(zeroMark, zeroSpace);
            }
        };
    }

    public static int[] buildRaw(int... rawData) {
        return rawData;
    }

    public static int[] buildRaw(List<Integer> buffer) {
        int[] result = new int[buffer.size()];

        for (int i = 0; i < buffer.size(); i++) {
            result[i] = buffer.get(i);
        }

        return result;
    }

    public static int[] buildRaw(Iterable<Integer> dataStream) {
        if (dataStream instanceof List) {
            return buildRaw((List<Integer>) dataStream);
        }

        ArrayList<Integer> buffer = new ArrayList<Integer>();
        for (int data : dataStream) {
            buffer.add(data);
        }

        return buildRaw(buffer);
    }


    public static abstract interface SequenceDefinition {

        public abstract void one(IrCommandBuilder builder, int index);

        public abstract void zero(IrCommandBuilder builder, int index);

    }
}

