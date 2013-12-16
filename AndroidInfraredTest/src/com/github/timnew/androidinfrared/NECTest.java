package com.github.timnew.androidinfrared;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class NECTest {

    public static final NecIrSequence ON_SEQENCE = new NecIrSequence(32, 0xFFE01F);
    public static final NecIrSequence OFF_SEQENCE = new NecIrSequence(32, 0xFF609F);

    @Test
    public void should_be_generate() {
        String onCode = ON_SEQENCE.getCode();
        String offCode = OFF_SEQENCE.getCode();

        String onCode1 = IrdaProtocols.NEC.buildNEC(32, 0xFFE01F);
        String offCode1 = IrdaProtocols.NEC.buildNEC(32, 0xFF609F);

//        assertThat(onCode1).isEqualTo(onCode);
        assertThat(offCode1).isEqualTo(offCode);
    }
}


