package com.vyazelenko.perf.collections;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetBenchmark extends AbstractCollectionBenchmark {

    public enum Kind {
        SET_OF {
            @Override
            public <E> Set<E> create(E... values) {
                return Set.of(values);
            }
        },
        HASH_SET {
            @Override
            public <E> Set<E> create(E... values) {
                return new HashSet<>(Set.of(values));
            }
        },
        LINKED_HASH_SET {
            @Override
            public <E> Set<E> create(E... values) {
                return new LinkedHashSet<>(Set.of(values));
            }
        },
        TREE_SET {
            @Override
            public <E> Set<E> create(E... values) {
                return new TreeSet<>(Set.of(values));
            }
        };

        public abstract <E> Set<E> create(E... values);
    }

    @Param
    private Kind kind;

    private Set<Object> set;
    private Object contains;

    @Override
    protected void doSetup(Object[] values) {
        set = kind.create(values);
        contains = values[values.length >> 1];
    }

    @Benchmark
    public Object contains() {
        return set.contains(contains);
    }
}
