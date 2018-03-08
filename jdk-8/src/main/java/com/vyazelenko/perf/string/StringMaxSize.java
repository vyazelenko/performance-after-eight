package com.vyazelenko.perf.string;

public class StringMaxSize {
    public static void main(String[] args) {
        char[] data = new char[Integer.MAX_VALUE - 2];
        data[0] = '\uD83D';
        data[1] = '\uDC7B';
        String value = new String(data);
        System.out.println(value.hashCode());
    }
}
