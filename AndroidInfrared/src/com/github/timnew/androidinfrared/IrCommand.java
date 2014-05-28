package com.github.timnew.androidinfrared;

import static com.github.timnew.androidinfrared.IrCommandBuilder.irCommandBuilder;
import static com.github.timnew.androidinfrared.IrCommandBuilder.simpleSequence;

public class IrCommand {

    public final int frequency;
    public final int[] pattern;

    public IrCommand(int frequency, int[] pattern) {
        this.frequency = frequency;
        this.pattern = pattern;
    }

    public static class NEC {

        private static final int FREQUENCY = 38000;
        private static final int HDR_MARK = 9000;
        private static final int HDR_SPACE = 4500;
        private static final int BIT_MARK = 560;
        private static final int ONE_SPACE = 1600;
        private static final int ZERO_SPACE = 560;

        private static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = simpleSequence(BIT_MARK, ONE_SPACE, BIT_MARK, ZERO_SPACE);

        public static IrCommand buildNEC(int bitCount, int data) {
            return irCommandBuilder(FREQUENCY)
                    .pair(HDR_MARK, HDR_SPACE)
                    .sequence(SEQUENCE_DEFINITION, bitCount, data)
                    .mark(BIT_MARK)
                    .build();
        }
    }

    public static class Sony {

        private static final int FREQUENCY = 40000;
        private static final int HDR_MARK = 2400;
        private static final int HDR_SPACE = 600;
        private static final int ONE_MARK = 1200;
        private static final int ZERO_MARK = 600;

        private static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = simpleSequence(ONE_MARK, HDR_SPACE, ZERO_MARK, HDR_SPACE);

        public static IrCommand buildSony(int bitCount, long data) {
            return irCommandBuilder(FREQUENCY)
                    .pair(HDR_MARK, HDR_SPACE)
                    .sequence(SEQUENCE_DEFINITION, bitCount, data << (64 - bitCount))
                    .build();
        }
    }

    public static class RC5 {

        private static final int FREQUENCY = 36000;
        private static final int T1 = 889;

        private static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = new IrCommandBuilder.SequenceDefinition() {
            @Override
            public void one(IrCommandBuilder builder, int index) {
                builder.reversePair(T1, T1);
            }

            @Override
            public void zero(IrCommandBuilder builder, int index) {
                builder.pair(T1, T1);
            }
        };

        // Note: first bit must be a one (start bit)
        public static IrCommand buildRC5(int bitCount, long data) {
            return irCommandBuilder(FREQUENCY)
                    .mark(T1)
                    .space(T1)
                    .mark(T1)
                    .sequence(SEQUENCE_DEFINITION, bitCount, data << (64 - bitCount))
                    .build();
        }
    }

    public static class RC6 {

        private static final int FREQUENCY = 36000;
        private static final int HDR_MARK = 2666;
        private static final int HDR_SPACE = 889;
        private static final int T1 = 444;

        public static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = new IrCommandBuilder.SequenceDefinition() {
            private int getTime(int index) {
                return index == 3 ? T1 + T1 : T1;
            }

            @Override
            public void one(IrCommandBuilder builder, int index) {
                int t = getTime(index);
                builder.pair(t, t);
            }

            @Override
            public void zero(IrCommandBuilder builder, int index) {
                int t = getTime(index);
                builder.reversePair(t, t);
            }
        };

        // Caller needs to take care of flipping the toggle bit
        public static IrCommand buildRC6(int bitCount, long data) {
            return irCommandBuilder(FREQUENCY)
                    .pair(HDR_MARK, HDR_SPACE)
                    .pair(T1, T1)
                    .sequence(SEQUENCE_DEFINITION, bitCount, data << (64 - bitCount))
                    .build();
        }
    }

    public static class DISH {

        private static final int FREQUENCY = 56000;
        private static final int HDR_MARK = 400;
        private static final int HDR_SPACE = 6100;
        private static final int BIT_MARK = 400;
        private static final int ONE_SPACE = 1700;
        private static final int ZERO_SPACE = 2800;
        private static final int TOP_BIT = 0x8000;

        private static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = simpleSequence(BIT_MARK, ONE_SPACE, BIT_MARK, ZERO_SPACE);

        public static IrCommand buildDISH(int bitCount, int data) {
            return irCommandBuilder(FREQUENCY)
                    .pair(HDR_MARK, HDR_SPACE)
                    .sequence(SEQUENCE_DEFINITION, TOP_BIT, bitCount, data)
                    .build();
        }
    }

    public static class Sharp {

        private static final int FREQUENCY = 38000;
        private static final int BIT_MARK = 245;
        private static final int ONE_SPACE = 1805;
        private static final int ZERO_SPACE = 795;
        private static final int DELAY = 46;

        private static final int INVERSE_MASK = 0x3FF;
        private static final int TOP_BIT = 0x4000;

        private static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = simpleSequence(BIT_MARK, ONE_SPACE, BIT_MARK, ZERO_SPACE);

        public static IrCommand buildSharp(int bitCount, int data) {
            return irCommandBuilder(FREQUENCY)
                    .sequence(SEQUENCE_DEFINITION, TOP_BIT, bitCount, data)
                    .pair(BIT_MARK, ZERO_SPACE)
                    .delay(DELAY)

                    .sequence(SEQUENCE_DEFINITION, TOP_BIT, bitCount, data ^ INVERSE_MASK)
                    .pair(BIT_MARK, ZERO_SPACE)
                    .delay(DELAY)
                    .build();
        }
    }

    public static class Panasonic {
        private static final int FREQUENCY = 35000;
        private static final int HDR_MARK = 3502;
        private static final int HDR_SPACE = 1750;
        private static final int BIT_MARK = 502;
        private static final int ONE_SPACE = 1244;
        private static final int ZERO_SPACE = 400;

        private static final int ADDRESS_TOP_BIT = 0x8000;
        private static final int ADDRESS_LENGTH = 16;
        private static final int DATA_LENGTH = 32;

        private static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = simpleSequence(BIT_MARK, ONE_SPACE, BIT_MARK, ZERO_SPACE);

        public static IrCommand buildPanasonic(int address, int data) {
            return irCommandBuilder(FREQUENCY)
                    .pair(HDR_MARK, HDR_SPACE)
                    .sequence(SEQUENCE_DEFINITION, ADDRESS_TOP_BIT, ADDRESS_LENGTH, address)
                    .sequence(SEQUENCE_DEFINITION, DATA_LENGTH, data)
                    .mark(BIT_MARK)
                    .build();
        }
    }

    public static class JVC {

        private static final int FREQUENCY = 38000;
        private static final int HDR_MARK = 8000;
        private static final int HDR_SPACE = 4000;
        private static final int BIT_MARK = 600;
        private static final int ONE_SPACE = 1600;
        private static final int ZERO_SPACE = 550;

        private static final IrCommandBuilder.SequenceDefinition SEQUENCE_DEFINITION = simpleSequence(BIT_MARK, ONE_SPACE, BIT_MARK, ZERO_SPACE);

        public static IrCommand buildJVC(int bitCount, long data, boolean repeat) {
            IrCommandBuilder builder = irCommandBuilder(FREQUENCY);

            if (!repeat)
                builder.pair(HDR_MARK, HDR_SPACE);

            return builder.sequence(SEQUENCE_DEFINITION, bitCount, data << (64 - bitCount))
                    .mark(BIT_MARK)
                    .build();
        }
    }

    public static class Pronto {

        public static IrCommand buildPronto(String protoText) {
            String[] codeParts = protoText.split(" ");

            int[] protoSequence = new int[codeParts.length];

            for (int i = 0; i < codeParts.length; i++) {
                protoSequence[i] = Integer.parseInt(codeParts[i], 16);
            }

            return buildPronto(protoSequence);
        }

        public static IrCommand buildPronto(int[] protoSequence) {
            int frequency = (int) (1000000 / (protoSequence[1] * 0.241246));
            int T = (int) (protoSequence[1] * 0.241246);

            IrCommandBuilder builder = irCommandBuilder(frequency);

            for (int i = 4; i < protoSequence.length; i += 2) {
                builder.pair(protoSequence[i] * T, protoSequence[i + 1] * T);
            }

            return builder.build();
        }
    }
}
