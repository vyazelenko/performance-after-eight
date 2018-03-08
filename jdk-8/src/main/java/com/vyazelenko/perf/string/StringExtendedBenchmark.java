package com.vyazelenko.perf.string;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class StringExtendedBenchmark {
    @Param({"Java is awesome!", "Java \uD83D\uDC7B \u16E0wesome!"})
    private String value;
    @Param({"Java is awesome!", "Java \uD83D\uDC7B \u16E0wesome!"})
    private String value2;
    private String indexOf;
    private String startsWith;

    @Setup
    public void setup() {
        String[] array = value2.split(" ");
        indexOf = array[array.length >> 1];
        startsWith = array[0];
    }

    @Benchmark
    public int indexOf() {
        return value.indexOf(indexOf);
    }

    @Benchmark
    public boolean startsWith() {
        return value.startsWith(startsWith);
    }

    @Benchmark
    public boolean equals() {
        return value.equals(value2);
    }

    @Benchmark
    public int compareTo() {
        return value.compareTo(value2);
    }
}
