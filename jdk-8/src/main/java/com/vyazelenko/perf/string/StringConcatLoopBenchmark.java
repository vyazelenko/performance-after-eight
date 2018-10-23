package com.vyazelenko.perf.string;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class StringConcatLoopBenchmark {
    @Param("50")
    private int iterations;

    private long currentTime;
    private int count;
    private String message;

    @Setup
    public void setup() {
        currentTime = System.currentTimeMillis();
        count = 36473;
        message = "Log payload";
    }

    private String log() {
        return "[" + currentTime + "]: " + message + " (" + count + ")";
    }

    @Benchmark
    public String naive() {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += (i + " " + log() + "\n");
        }
        return result;
    }

    @Benchmark
    public String proper() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(i).append(" ").append(log()).append("\n");
        }
        return sb.toString();
    }
}
