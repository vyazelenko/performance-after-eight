package com.vyazelenko.perf.stack;

import java.util.concurrent.Callable;

public final class StackHelper {
    private StackHelper() {
    }

    public static <V> V doCall(Callable<V> task, int stackDepth) throws Exception {
        if (stackDepth > 0) {
            return doCall(task, stackDepth - 1);
        } else {
            return task.call();
        }
    }
}
