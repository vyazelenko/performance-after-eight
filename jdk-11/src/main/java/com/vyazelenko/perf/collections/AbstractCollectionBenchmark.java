package com.vyazelenko.perf.collections;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public abstract class AbstractCollectionBenchmark {
    @Param({"1", "2", "10", "100"})
    private int size;

    @Setup
    public void setup() {
        Random random = new Random(36287463248382L);
        Object[] values = random.ints(size, 1_000, 1_000_000).mapToObj(Integer::valueOf).toArray();
        doSetup(values);
    }

    protected abstract void doSetup(Object[] values);
}
