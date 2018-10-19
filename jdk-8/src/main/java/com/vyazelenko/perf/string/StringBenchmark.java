package com.vyazelenko.perf.string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class StringBenchmark {
    private String latin1;
    private String latin1Copy;
    private String utf16;

    @Setup
    public void setup() {
        latin1 = "Java is awesome!";
        latin1Copy = new String(latin1.toCharArray());
        utf16 = "Java \uD83D\uDC7B \u16E0wesome!";
    }

    @Benchmark
    public void charAt_Latin1(Blackhole bh) {
        String value = latin1;
        for (int i = 0, len = value.length(); i < len; i++) {
            bh.consume(value.charAt(i));
        }
    }

    @Benchmark
    public void charAt_UTF16(Blackhole bh) {
        String value = utf16;
        for (int i = 0, len = value.length(); i < len; i++) {
            bh.consume(value.charAt(i));
        }
    }

    @Benchmark
    public String toLowerCase_Latin1() {
        return latin1.toLowerCase(Locale.US);
    }

    @Benchmark
    public String toLowerCase_UTF16() {
        return utf16.toLowerCase(Locale.US);
    }

    @Benchmark
    public boolean equals_Latin1_Latin1() {
        return latin1.equals(latin1Copy);
    }

    @Benchmark
    public boolean equals_Latin1_UTF16() {
        return latin1.equals(utf16);
    }
}
