package com.github.timnew.androidinfrared;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    public static final int TOP_BIT_32 = 0x1 << 31;
    public static final long TOP_BIT_64 = 0x1L << 63;
    private final int frequency;
    private final List<Integer> buffer;
    private Boolean lastMark;

    public static CommandBuilder commandBuilder(int frequency) {
        return new CommandBuilder(frequency);
    }

    private CommandBuilder(int frequencyKHz) {
        this.frequency = frequencyKHz;

        buffer = new ArrayList<Integer>();
        buffer.add(frequencyKHz);

        lastMark = null;
    }

    private CommandBuilder appendMark(boolean mark, int interval) {
        if (lastMark == null || lastMark != mark) {
            buffer.add(interval);
            lastMark = mark;
        } else {
            int lastIndex = buffer.size() - 1;
            buffer.set(lastIndex, buffer.get(lastIndex) + interval);
        }

        return this;
    }

    public CommandBuilder mark(int interval) {
        return appendMark(true, interval);
    }

    public CommandBuilder space(int interval) {
        return appendMark(false, interval);
    }

    public CommandBuilder pair(int on, int off) {
        return mark(on).space(off);
    }

    public CommandBuilder reversePair(int off, int on) {
        return space(off).mark(on);
    }

    public CommandBuilder delay(int ms) {
        return space(ms * frequency / 1000);
    }

    public CommandBuilder sequence(SequenceDefinition definition, int length, int data) {
        return sequence(definition, TOP_BIT_32, length, data);
    }

    public CommandBuilder sequence(SequenceDefinition definition, int length, long data) {
        return sequence(definition, TOP_BIT_64, length, data);
    }

    public CommandBuilder sequence(SequenceDefinition definition, long topBit, int length, long data) {
        for (int index = 0; index < length; index++) {
            if ((data & topBit) == topBit) {
                definition.one(this, index);
            } else {
                definition.zero(this, index);
            }

            data <<= 1;
        }
        return this;
    }

    public String build() {
        StringBuilder result = new StringBuilder();

        for (Integer num : buffer) {
            result.append(num);
            result.append(',');
        }

        return result.toString();
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
            public void one(CommandBuilder builder, int index) {
                builder.pair(oneMark, oneSpace);
            }

            @Override
            public void zero(CommandBuilder builder, int index) {
                builder.pair(zeroMark, zeroSpace);
            }
        };
    }

    public static abstract interface SequenceDefinition {

        public abstract void one(CommandBuilder builder, int index);

        public abstract void zero(CommandBuilder builder, int index);

    }
}
