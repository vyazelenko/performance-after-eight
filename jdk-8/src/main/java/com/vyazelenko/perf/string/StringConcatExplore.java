package com.vyazelenko.perf.string;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class StringConcatExplore {
    public static void main(String[] args) {
        log("msg", 42);
    }

    private static void log(String msg, int count) {
        out.println("[" + currentTimeMillis() + "]: "
                + msg + " (" + count + ")");
    }
}
