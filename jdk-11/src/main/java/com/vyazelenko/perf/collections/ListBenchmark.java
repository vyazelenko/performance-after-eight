package com.vyazelenko.perf.collections;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.infra.Blackhole;

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
    private int index;
    private Object contains;

    @Override
    protected void doSetup(Object[] values) {
        list = kind.create(values);
        index = values.length >> 1;
        contains = values[index];
    }

    @Benchmark
    public Object get() {
        return list.get(index);
    }

    @Benchmark
    public Object contains() {
        return list.contains(contains);
    }

    @Benchmark
    public void iterator(Blackhole bh) {
        List<Object> list = this.list;
        for (Object o : list) {
            bh.consume(o);
        }
    }
}
