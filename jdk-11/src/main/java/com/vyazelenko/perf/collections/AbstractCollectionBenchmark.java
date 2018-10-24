package com.vyazelenko.perf.collections;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public abstract class AbstractCollectionBenchmark {
    @Param({"1", "2", "10", "100"})
    private int size;

    @Setup
    public void setup() {
        Random random = new Random(36287463248382L);
        Object[] values = random.ints(size, 1_000, 1_000_000).mapToObj(Integer::valueOf).toArray();
        Object[] array = random.ints(size, 0, values.length).mapToObj(i -> values[i]).toArray();
        doSetup(array);
    }

    protected abstract void doSetup(Object[] values);
}
