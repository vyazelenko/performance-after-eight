package com.vyazelenko.perf.stack;

import org.openjdk.jmh.annotations.*;

import java.lang.StackWalker.Option;
import java.util.concurrent.TimeUnit;

import static com.vyazelenko.perf.stack.StackHelper.doCall;
import static java.util.stream.Collectors.toList;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(3)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class StackWalkerBenchmark {

    private static final StackWalker STACK_WALKER =
            StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
    @Param({"10", "100"})
    private int stackDepth;

    @Benchmark
    public Class<?> callerClass() throws Exception {
        return doCall(
                () -> STACK_WALKER.getCallerClass(),
                stackDepth);
    }

    @Benchmark
    public int stackDepth() throws Exception {
        return doCall(
                () -> STACK_WALKER.walk(stream -> stream.count()).intValue(),
                stackDepth);
    }

    @Benchmark
    public Object top10frames() throws Exception {
        return doCall(
                () -> STACK_WALKER.walk(s -> s.limit(10).collect(toList())),
                stackDepth);
    }
}