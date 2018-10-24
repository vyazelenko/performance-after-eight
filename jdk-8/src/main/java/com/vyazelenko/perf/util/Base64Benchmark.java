package com.vyazelenko.perf.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Base64;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@State(Scope.Benchmark)
public class Base64Benchmark {
    @Param({"10", "100", "1000"})
    private int size;

    private byte[] value;
    private byte[] encoded;

    @Setup
    public void setup() {
        Random r = new Random(-749834793274L);
        value = new byte[size];
        r.nextBytes(value);
        encoded = Base64.getEncoder().encode(value);
    }

    @Benchmark
    public byte[] encode() {
        return Base64.getEncoder().encode(value);
    }

    @Benchmark
    public byte[] decode() {
        return Base64.getDecoder().decode(encoded);
    }
}
