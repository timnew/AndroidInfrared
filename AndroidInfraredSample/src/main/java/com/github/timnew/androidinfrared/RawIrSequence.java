package com.github.timnew.androidinfrared;

import static java.lang.Integer.parseInt;

public class RawIrSequence extends IrSequence {
    private final int frequencyKHz;
    private final int[] codes;

    public RawIrSequence(int frequencyKHz, int... codes) {
        this.frequencyKHz = frequencyKHz;
        this.codes = codes;
    }

    @Override
    public String getName() {
        return "RawSequence";
    }

    @Override
    protected CharSequence generateCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(frequencyKHz);

        for (int code : codes) {
            sb.append(",");
            sb.append(code);
        }

        return sb;
    }

    public static RawIrSequence parseProntoCode(String protoCode) {
        String[] codeComponents = protoCode.split(" ");

        int frequency = (int) (1000000 / (parseInt(codeComponents[1], 16) * 0.241246));
        int[] codes = new int[codeComponents.length - 4];

        for (int i = 0; i < codes.length; i++) {
            codes[i] = parseInt(codeComponents[i + 4], 16);
        }

        return new RawIrSequence(frequency, codes);
    }
}
