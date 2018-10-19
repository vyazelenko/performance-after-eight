package com.vyazelenko.perf.string;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class StringConcatBenchmark {
    private long currentTime;
    private int count;
    private String message;

    @Setup
    public void setup() {
        currentTime = System.currentTimeMillis();
        count = 42;
        message = "Log payload";
    }

    @Benchmark
    public String log() {
        return "[" + currentTime + "]: " + message + " (" + count + ")";
    }

}
