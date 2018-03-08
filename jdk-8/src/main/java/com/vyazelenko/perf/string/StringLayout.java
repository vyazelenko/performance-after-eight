package com.vyazelenko.perf.string;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;

public class StringLayout {
    public static void main(String[] args) {
        String value =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et " +
                        "dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
                        "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse " +
                        "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
                        "culpa qui officia deserunt mollit anim id est laborum.";
        out.println(VM.current().details());
        out.println(ClassLayout.parseClass(String.class).toPrintable());
        out.println(GraphLayout.parseInstance(value).toFootprint());
    }
}