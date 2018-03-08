package com.vyazelenko.perf.string;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class StringBasicBenchmark {
    @Param({"Java is awesome!", "Java \uD83D\uDC7B \u16E0wesome!"})
    private String value;
    private int substringIndex;

    @Setup
    public void setup() {
        substringIndex = value.indexOf(' ');
    }

    @Benchmark
    public void charAt(Blackhole bh) {
        String v = value;
        for (int i = 0, len = v.length(); i < len; i++) {
            bh.consume(v.charAt(i));
        }
    }

    @Benchmark
    public String toLowerCase() {
        return value.toLowerCase(Locale.US);
    }

    @Benchmark
    public String substringHead() {
        return value.substring(0, substringIndex);
    }

    @Benchmark
    public String substringTail() {
        return value.substring(substringIndex);
    }
}
