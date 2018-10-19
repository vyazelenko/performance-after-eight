package com.vyazelenko.perf.stack;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.vyazelenko.perf.stack.StackHelper.doCall;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class ExceptionBenchmark {
    @Param({"10", "100"})
    private int stackDepth;

    @Benchmark
    public Class<?> caller_class() throws Exception {
        return doCall(() -> {
            StackTraceElement[] stackTrace = new Exception().getStackTrace();
            return Class.forName(stackTrace[2].getClassName());
        }, stackDepth);
    }

    @Benchmark
    public int stack_depth() throws Exception {
        return doCall(
                () -> new Exception().getStackTrace().length,
                stackDepth);
    }

    @Benchmark
    public Object top_10_frames() throws Exception {
        return doCall(() -> {
            StackTraceElement[] stackTrace = new Exception().getStackTrace();
            return Arrays.copyOfRange(stackTrace, 0, 10);
        }, stackDepth);
    }
}
