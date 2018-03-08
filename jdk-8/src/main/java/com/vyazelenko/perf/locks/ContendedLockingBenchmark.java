package com.vyazelenko.perf.locks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 3, jvmArgsAppend = {"-XX:-UseBiasedLocking", "-XX:+UseHeavyMonitors"})
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class ContendedLockingBenchmark {
    // Note: Use '-XX:-UseBiasedLocking -XX:+UseHeavyMonitors' to bypass both biased locking and stack based locking;
    // forces the use of ObjectMonitor objects.

    // FIXME: Probably wrong results

    @Param({"10", "100"})
    private int tokens;

    private volatile long counter;

    @Benchmark
    @Threads(4)
    public synchronized long increment() {
        long result = counter++;
        Blackhole.consumeCPU(tokens);
        return result;
    }
}