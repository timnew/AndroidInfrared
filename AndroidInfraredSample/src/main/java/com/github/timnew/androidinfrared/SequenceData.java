package com.github.timnew.androidinfrared;

import static java.lang.String.format;

public class SequenceData implements CharSequence {
    public static final int TOP_BIT = 0x80000000;
    private StringBuilder buffer;
    private CharSequence positivePair;
    private CharSequence negtivePair;

    public static SequenceData sequenceData(int frequency) {
        return new SequenceData(frequency);
    }

    SequenceData(int frequencyKHz) {
        buffer = new StringBuilder();
        buffer.append(frequencyKHz);
    }

    public SequenceData writePair(int on, int off) {
        buffer.append(',');
        buffer.append(on);
        buffer.append(',');
        buffer.append(off);

        return this;
    }

    public SequenceData writePair(CharSequence pair) {
        buffer.append(',');
        buffer.append(pair);

        return this;
    }

    @Override
    public int length() {
        return buffer.length();
    }

    @Override
    public char charAt(int index) {
        return buffer.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return buffer.subSequence(start, end);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    public static String makePair(int on, int off) {
        return format("%d,%d", on, off);
    }

    public SequenceData setPositivePair(int on, int off) {
        positivePair = makePair(on, off);

        return this;
    }

    public SequenceData setNegtivePair(int on, int off) {
        negtivePair = makePair(on, off);

        return this;
    }

    public SequenceData writeData(int bitCount, int data) {
        for (int index = 0; index < bitCount; index++) {
            if ((data & TOP_BIT) == TOP_BIT)
                writePair(positivePair);
            else
                writePair(negtivePair);

            data <<= 1;
        }

        return this;
    }
}
