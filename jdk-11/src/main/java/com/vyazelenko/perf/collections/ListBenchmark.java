package com.vyazelenko.perf.collections;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListBenchmark extends AbstractCollectionBenchmark {
    public enum Kind {
        LIST_OF {
            @Override
            public <E> List<E> create(E... values) {
                return List.of(values);
            }
        },
        ARRAY_LIST {
            @Override
            public <E> List<E> create(E... values) {
                return new ArrayList<>(List.of(values));
            }
        },
        LINKED_LIST {
            @Override
            public <E> List<E> create(E... values) {
                return new LinkedList<>(List.of(values));
            }
        };

        public abstract <E> List<E> create(E... values);
    }

    @Param
    private Kind kind;

    private List<Object> list;
    private Object contains;

    @Override
    protected void doSetup(Object[] values) {
        list = kind.create(values);
        contains = values[values.length >> 1];
    }

    @Benchmark
    public Object contains() {
        return list.contains(contains);
    }
}
