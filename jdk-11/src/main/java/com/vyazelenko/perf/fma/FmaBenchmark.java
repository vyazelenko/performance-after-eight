package com.vyazelenko.perf.fma;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(3)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class FmaBenchmark {
    @Param({"10", "100"})
    private int size;

    private double[][] a;
    private double[][] b;

    @Setup
    public void setup() {
        Random r = new Random(-2183187493L);
        a = new double[size][];
        b = new double[size][];
        for (int i = 0; i < size; i++) {
            a[i] = r.doubles(size, -1_000_000, 1_000_000).toArray();
            b[i] = r.doubles(size, -1000, 1000).toArray();
        }
    }

    @Benchmark
    public double[][] matrix_multiply_expression() {
        double[][] c = createResultArray();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < b.length; k++) {
                    sum += a[i][k] * b[k][j];
                }
                c[i][j] = sum;
            }
        }
        return c;
    }

    private double[][] createResultArray() {
        double[][] c = new double[size][];
        for (int i = 0; i < c.length; i++) {
            c[i] = new double[c.length];
        }
        return c;
    }

    @Benchmark
    public double[][] matrix_multiply_fma() {
        double[][] c = createResultArray();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < b.length; k++) {
                    sum = Math.fma(a[i][k], b[k][j], sum);
                }
                c[i][j] = sum;
            }
        }
        return c;
    }
}
