package com.github.timnew.androidinfrared;

import org.junit.Before;
import org.junit.Test;

import static com.github.timnew.androidinfrared.CommandBuilder.commandBuilder;
import static org.fest.assertions.api.Assertions.assertThat;

public class CommandBuilderTest {

    public static final int FREQUENCY = 40000;
    public static final String FREQUENCY_DATA = "40000,";
    private CommandBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = commandBuilder(FREQUENCY);
    }

    @Test
    public void should_build_with_correct_frequency() {
        assertThat(builder.getFrequency()).isEqualTo(FREQUENCY);
        assertThat(builder.getBuffer()).containsExactly(FREQUENCY);
    }

    @Test
    public void should_build_pair_correctly() {
        builder.pair(30, 40).pair(50, 60);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 30, 40, 50, 60);
    }

    @Test
    public void should_build_mark() {
        builder.pair(30, 40).mark(10);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 30, 40, 10);

        builder.mark(20);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 30, 40, 30);
    }

    @Test
    public void should_build_space() {
        builder.pair(30, 40).space(10);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 30, 50);

        builder.mark(20).space(10);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 30, 50, 20, 10);
    }

    @Test
    public void should_merge_pair_and_reverse_pair() {
        builder.pair(10, 20).reversePair(20, 30);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 10, 40, 30);

        builder.pair(5, 10);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 10, 40, 35, 10);
    }

    @Test
    public void should_inject_delay() {
        builder.pair(10, 20).delay(1); // 1 ms == 40 ticks @ frequency 40kHz

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 10, 60);

        builder.mark(10).delay(1);

        assertThat(builder.getBuffer()).containsExactly(FREQUENCY, 10, 60, 10, 40);

    }

    @Test
    public void should_render_properly() {
        builder.pair(10, 20).pair(30, 40);

        assertThat(builder.build()).isEqualTo(FREQUENCY_DATA + "10,20,30,40,");
    }


}
