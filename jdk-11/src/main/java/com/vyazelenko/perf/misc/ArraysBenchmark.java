package com.vyazelenko.perf.misc;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class ArraysBenchmark {
    @Param({"10", "100", "1000", "10000"})
    private int prefixSize;

    private byte[] a;
    private byte[] b;

    @Setup
    public void setup() {
        Random r = new Random(47623473);
        a = new byte[prefixSize];
        b = new byte[prefixSize * 10];
        r.nextBytes(b);
        a = Arrays.copyOfRange(b, 0, prefixSize);
        a[a.length - 1] *= -1;
    }

    @Benchmark
    public int builtin() {
        return Arrays.mismatch(a, b);
    }

    @Benchmark
    public int handRolled() {
        byte[] a = this.a;
        byte[] b = this.b;
        int length = Math.min(a.length, b.length);
        for (int i = 0; i < length; i++) {
            if (a[i] != b[i]) {
                return i;
            }
        }
        return a.length == b.length ? -1 : length;
    }
}
