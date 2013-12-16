package com.github.timnew.androidinfrared;

import static com.github.timnew.androidinfrared.SequenceData.sequenceData;

public class NecIrSequence extends IrSequence {

    public static final int NEC_FREQUENCY = 38028;  // T = 26.296 ms
    public static final int NEC_HDR_MARK = 342;
    public static final int NEC_HDR_SPACE = 171;
    public static final int NEC_BIT_MARK = 21;
    public static final int NEC_ONE_SPACE = 60;
    public static final int NEC_ZERO_SPACE = 21;

    private final int bitCount;
    private final int data;

    public NecIrSequence(int bitCount, int data) {
        this.bitCount = bitCount;
        this.data = data;
    }

    @Override
    public String getName() {
        return "NEC Sequence";
    }

    @Override
    protected CharSequence generateCode() {
        return sequenceData(NEC_FREQUENCY)
                .writePair(NEC_HDR_MARK, NEC_HDR_SPACE)
                .setPositivePair(NEC_BIT_MARK, NEC_ONE_SPACE)
                .setNegtivePair(NEC_BIT_MARK, NEC_ZERO_SPACE)
                .writeData(bitCount, data)
                .writePair(NEC_BIT_MARK, 0);
    }

}

